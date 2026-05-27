package com.example.voteapp.presentation.votingdetail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.voteapp.domain.model.Voting
import com.example.voteapp.domain.model.VotingType
import com.example.voteapp.domain.model.VotingOption
import com.example.voteapp.presentation.components.VotingCard
import java.text.SimpleDateFormat
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VotingDetailScreen(
    navController: NavController,
    votingId: String,
    viewModel: VotingDetailViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(votingId) {
        viewModel.load(votingId)
    }

    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Детали голосования") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = androidx.compose.material.icons.Icons.Default.ArrowBack,
                            contentDescription = null,
                        )
                    }
                },
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize()) {
            when (val s = state) {
                VotingDetailState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                is VotingDetailState.Error -> {
                    Text(
                        text = s.message,
                        modifier = Modifier.align(Alignment.Center),
                    )
                }

                is VotingDetailState.AlreadyVoted -> {
                    VotingDetailBody(
                        voting = s.voting,
                        alreadyVoted = true,
                        onSubmit = { /* кнопка будет отключена */ },
                    )
                }

                is VotingDetailState.Success -> {
                    VotingDetailBody(
                        voting = s.voting,
                        alreadyVoted = s.voting.hasVoted,
                        onSubmit = { optionId, optionIds ->
                            viewModel.submitVote(
                                votingId = votingId,
                                optionId = optionId,
                                optionIds = optionIds,
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun VotingDetailBody(
    voting: com.example.voteapp.domain.model.Voting,
    alreadyVoted: Boolean,
    onSubmit: (optionId: Long?, optionIds: List<Long>?) -> Unit,
) {
    var selectedSingle by remember(voting.id) { mutableStateOf<Long?>(null) }
    var selectedMultiple by remember(voting.id) { mutableStateOf<Set<Long>>(emptySet()) }

    val endsAt = remember(voting.id) {
        // MVP: используем timestamp endsAt в мс, чтобы показать время грубо.
        val date = Date(voting.endsAt)
        SimpleDateFormat("dd.MM.yyyy HH:mm").format(date)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(text = voting.title, style = MaterialTheme.typography.headlineSmall)
        if (!voting.description.isNullOrBlank()) {
            Text(text = voting.description, style = MaterialTheme.typography.bodyMedium)
        }

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            AssistChip(
                onClick = {},
                label = { Text(voting.type.name) },
            )
            AssistChip(
                onClick = {},
                label = { Text(if (voting.status.name == "ACTIVE") "Активно" else "Завершено") },
            )
        }

        Text(text = "Окончание: $endsAt", style = MaterialTheme.typography.bodySmall)

        if (voting.options.isNotEmpty()) {
            when (voting.type) {
                VotingType.SINGLE -> {
                    voting.options.forEach { opt ->
                        val checked = selectedSingle == opt.id
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = checked,
                                onClick = { selectedSingle = opt.id }
                            )
                            Text(text = opt.text)
                        }
                    }
                }

                VotingType.MULTIPLE -> {
                    voting.options.forEach { opt ->
                        val checked = selectedMultiple.contains(opt.id)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = checked,
                                onCheckedChange = {
                                    selectedMultiple = if (it) selectedMultiple + opt.id else selectedMultiple - opt.id
                                }
                            )
                            Text(text = opt.text)
                        }
                    }
                }

                else -> {
                    Text("Тип голосования пока не поддержан в UI")
                }
            }
        }

        Button(
            onClick = {
                when (voting.type) {
                    VotingType.SINGLE -> onSubmit(selectedSingle, null)
                    VotingType.MULTIPLE -> onSubmit(null, selectedMultiple.toList())
                    else -> onSubmit(null, null)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !alreadyVoted && voting.status.name == "ACTIVE"
        ) {
            Text(if (alreadyVoted) "Уже проголосовано" else "Проголосовать")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // MVP: после успешного голосования сервер вернёт VotingDetailState.AlreadyVoted/Success.
        // Отдельный переход на results можно добавить позже.
        if (alreadyVoted) {
            Text(
                text = "Вы уже участвовали в этом голосовании.",
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

