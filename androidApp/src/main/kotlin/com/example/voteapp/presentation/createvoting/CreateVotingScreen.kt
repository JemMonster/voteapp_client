package com.example.voteapp.presentation.createvoting

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.voteapp.domain.model.VotingType
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateVotingScreen(
    navController: NavController,
    viewModel: CreateVotingViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var type by remember { mutableStateOf(VotingType.SINGLE) }

    // MVP: строковые поля (сервер ожидает ISO/Instant как String)
    var startTime by remember { mutableStateOf("") }
    var endTime by remember { mutableStateOf("") }

    var options by remember { mutableStateOf(listOf("", "")) }

    LaunchedEffect(state) {
        if (state is CreateVotingState.Success) {
            navController.navigate("feed") {
                popUpTo(navController.graph.startDestinationId) { inclusive = false }
                launchSingleTop = true
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Создать голосование") },
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth(),
                enabled = state !is CreateVotingState.Loading,
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth(),
                enabled = state !is CreateVotingState.Loading,
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                AssistChip(
                    onClick = { type = VotingType.SINGLE },
                    label = { Text("SINGLE") },
                    colors = if (type == VotingType.SINGLE) AssistChipDefaults.assistChipColors() else AssistChipDefaults.assistChipColors(),
                )
                AssistChip(
                    onClick = { type = VotingType.MULTIPLE },
                    label = { Text("MULTIPLE") },
                )
            }

            OutlinedTextField(
                value = startTime,
                onValueChange = { startTime = it },
                label = { Text("StartTime (ISO/Instant)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = androidx.compose.ui.text.input.KeyboardOptions(keyboardType = KeyboardType.Text),
                enabled = state !is CreateVotingState.Loading,
            )

            OutlinedTextField(
                value = endTime,
                onValueChange = { endTime = it },
                label = { Text("EndTime (ISO/Instant)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = androidx.compose.ui.text.input.KeyboardOptions(keyboardType = KeyboardType.Text),
                enabled = state !is CreateVotingState.Loading,
            )

            Text("Options", style = MaterialTheme.typography.titleMedium)

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 220.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(options) { index, opt ->
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = opt,
                            onValueChange = {
                                options = options.toMutableList().also { it[index] = it[index].let { _ -> it[index] } }
                            },
                            label = { Text("Option ${index + 1}") },
                            modifier = Modifier.weight(1f),
                            enabled = state !is CreateVotingState.Loading,
                        )

                        IconButton(
                            onClick = {
                                if (options.size > 2) {
                                    options = options.filterIndexed { i, _ -> i != index }
                                }
                            },
                            enabled = state !is CreateVotingState.Loading && options.size > 2
                        ) {
                            Icon(Icons.Default.Delete, contentDescription = null)
                        }
                    }
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = {
                        options = options + ""
                    },
                    enabled = state !is CreateVotingState.Loading
                ) {
                    Text("Add option")
                }

                Button(
                    onClick = {
                        viewModel.validateAndCreate(
                            title = title,
                            description = description.takeIf { it.isNotBlank() },
                            type = type,
                            startTime = startTime,
                            endTime = endTime,
                            options = options,
                        )
                    },
                    enabled = state !is CreateVotingState.Loading,
                    modifier = Modifier.weight(1f)
                ) {
                    if (state is CreateVotingState.Loading) {
                        CircularProgressIndicator(modifier = Modifier.size(18.dp))
                    } else {
                        Text("Создать")
                    }
                }
            }

            if (state is CreateVotingState.Error) {
                Text((state as CreateVotingState.Error).message, color = MaterialTheme.colorScheme.error)
            }
        }
    }
}

