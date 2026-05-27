package com.example.voteapp.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.voteapp.domain.model.Voting

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VotingCard(
    voting: Voting,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
) {
    val clickableModifier = if (onClick != null) modifier.then(androidx.compose.foundation.clickable { onClick() }) else modifier
    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Image placeholder
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            )
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = voting.title,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = voting.description.take(100) + if (voting.description.length > 100) "..." else "",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 4.dp)
                )
                
                Row(
                    modifier = Modifier.padding(top = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Badge(
                        containerColor = if (voting.status == VotingStatus.ACTIVE) 
                            MaterialTheme.colorScheme.primaryContainer else 
                            MaterialTheme.colorScheme.errorContainer
                    ) {
                        Text(
                            text = if (voting.status == VotingStatus.ACTIVE) "Активно" else "Завершено"
                        )
                    }
                    Text(
                        text = "${voting.totalVotes} голосов",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

