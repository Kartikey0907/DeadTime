package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.SessionStatus
import com.example.ui.components.CategoryBadge
import com.example.ui.components.CategoryColor
import com.example.ui.theme.*
import com.example.ui.viewmodel.DeadTimeViewModel
import com.example.ui.viewmodel.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActiveTimerScreen(viewModel: DeadTimeViewModel) {
    val activity by viewModel.activeActivity.collectAsStateWithLifecycle()
    val secondsRemaining by viewModel.timerSecondsRemaining.collectAsStateWithLifecycle()
    val totalSeconds by viewModel.timerTotalSeconds.collectAsStateWithLifecycle()
    val isRunning by viewModel.isTimerRunning.collectAsStateWithLifecycle()

    var showStopDialog by remember { mutableStateOf(false) }

    val act = activity ?: run {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    val minutes = secondsRemaining / 60
    val seconds = secondsRemaining % 60
    val timeFormatted = String.format("%02d:%02d", minutes, seconds)

    val progress = if (totalSeconds > 0) {
        (totalSeconds - secondsRemaining).toFloat() / totalSeconds.toFloat()
    } else 0f

    val catColor = CategoryColor(act.category)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (act.isRest) "Intentional Rest" else "Active Session") },
                navigationIcon = {
                    IconButton(onClick = { showStopDialog = true }) {
                        Icon(Icons.Default.Close, contentDescription = "Exit Session")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Activity Title & Category Info
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                CategoryBadge(category = act.category)
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = act.title,
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = act.description,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Big Timer Display with Progress Ring
            Box(
                modifier = Modifier.size(260.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    progress = { 1f },
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    strokeWidth = 14.dp,
                    trackColor = Color.Transparent
                )

                CircularProgressIndicator(
                    progress = { progress },
                    modifier = Modifier.fillMaxSize(),
                    color = if (act.isRest) CategoryRelax else AmberPrimary,
                    strokeWidth = 14.dp,
                    trackColor = Color.Transparent
                )

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = timeFormatted,
                        style = MaterialTheme.typography.displayLarge.copy(
                            fontWeight = FontWeight.Black,
                            fontSize = 58.sp,
                            letterSpacing = (-1).sp
                        ),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = if (isRunning) "Remaining" else "Paused",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = if (isRunning) MaterialTheme.colorScheme.onSurfaceVariant else AmberPrimary
                    )
                }
            }

            // Timer Controls: Pause/Play, Finish Early, Stop
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Stop / Abort Button
                    OutlinedIconButton(
                        onClick = { showStopDialog = true },
                        modifier = Modifier
                            .size(54.dp)
                            .testTag("timer_stop_button")
                    ) {
                        Icon(Icons.Default.Stop, contentDescription = "Stop", tint = MaterialTheme.colorScheme.error)
                    }

                    Spacer(modifier = Modifier.width(20.dp))

                    // Pause / Resume Main FAB
                    FloatingActionButton(
                        onClick = { viewModel.toggleTimerPause() },
                        modifier = Modifier
                            .size(72.dp)
                            .testTag("timer_toggle_pause_button"),
                        shape = CircleShape,
                        containerColor = if (act.isRest) CategoryRelax else AmberPrimary,
                        contentColor = if (act.isRest) Color.White else Color(0xFF1E1500)
                    ) {
                        Icon(
                            imageVector = if (isRunning) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = if (isRunning) "Pause" else "Resume",
                            modifier = Modifier.size(36.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(20.dp))

                    // Finish Early Button
                    OutlinedIconButton(
                        onClick = { viewModel.completeCurrentActivity(isFullDuration = false) },
                        modifier = Modifier
                            .size(54.dp)
                            .testTag("timer_finish_early_button")
                    ) {
                        Icon(Icons.Default.Check, contentDescription = "Finish Early", tint = TealSecondary)
                    }
                }

                Text(
                    text = "Finish early anytime. Every minute counts.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }

    // Partial Completion / Stopping Early Dialog (No Guilt UX)
    if (showStopDialog) {
        AlertDialog(
            onDismissRequest = { showStopDialog = false },
            title = { Text("Did you finish?") },
            text = {
                Text(
                    text = "No problem if you need to stop early! Select how much you completed so we can log your reclaimed time accurately.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            confirmButton = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = {
                            showStopDialog = false
                            viewModel.stopSessionEarly(SessionStatus.COMPLETED)
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Finished!")
                    }

                    OutlinedButton(
                        onClick = {
                            showStopDialog = false
                            viewModel.stopSessionEarly(SessionStatus.PARTIAL)
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Partially completed (Record time)")
                    }

                    TextButton(
                        onClick = {
                            showStopDialog = false
                            viewModel.stopSessionEarly(SessionStatus.SKIPPED)
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Cancel session (Don't record)")
                    }
                }
            },
            dismissButton = null
        )
    }
}

@Composable
fun CompletionScreen(viewModel: DeadTimeViewModel) {
    val lastTitle by viewModel.lastCompletedTitle.collectAsStateWithLifecycle()
    val reclaimedMinutes by viewModel.lastReclaimedMinutes.collectAsStateWithLifecycle()
    val todaySessions by viewModel.todaySessions.collectAsStateWithLifecycle()

    val todayTotal = remember(todaySessions) {
        todaySessions.sumOf { it.durationActual }
    }

    var showShareDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Celebration Section
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Surface(
                    modifier = Modifier.size(88.dp),
                    shape = CircleShape,
                    color = AmberPrimary.copy(alpha = 0.15f),
                    border = androidx.compose.foundation.BorderStroke(2.dp, AmberPrimary)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(text = "🎉", fontSize = 42.sp)
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Nice.",
                    style = MaterialTheme.typography.displaySmall.copy(
                        fontWeight = FontWeight.Black,
                        color = AmberPrimary
                    )
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "You reclaimed $reclaimedMinutes minutes.",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Session Summary Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Activity", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text("Time Reclaimed", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = lastTitle.ifBlank { "DeadTime Session" },
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = "$reclaimedMinutes min",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Black,
                                    color = AmberPrimary
                                )
                            )
                        }

                        Divider(
                            modifier = Modifier.padding(vertical = 14.dp),
                            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Today's Total Reclaimed", style = MaterialTheme.typography.bodyMedium)
                            Text(
                                text = "$todayTotal min",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = TealSecondary
                                )
                            )
                        }
                    }
                }
            }

            // Action Buttons
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = { viewModel.navigateTo(Screen.Home) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("completion_home_button"),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AmberPrimary,
                        contentColor = Color(0xFF1E1500)
                    )
                ) {
                    Text(
                        text = "Done",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                }

                OutlinedButton(
                    onClick = {
                        viewModel.refreshRecommendations()
                        viewModel.navigateTo(Screen.Recommendations)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("completion_do_another_button"),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = "Do Another Session",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                    )
                }

                TextButton(
                    onClick = { showShareDialog = true },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Share Reclaimed Time Card")
                }
            }
        }
    }

    // Share Card Preview Modal
    if (showShareDialog) {
        AlertDialog(
            onDismissRequest = { showShareDialog = false },
            title = { Text("Share Your Time Win") },
            text = {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "⏳ DeadTime",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = AmberPrimary
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "I just reclaimed $reclaimedMinutes minutes of free time instead of mindless scrolling.",
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Today's total: $todayTotal minutes intentional.",
                            style = MaterialTheme.typography.labelMedium,
                            color = TealSecondary
                        )
                    }
                }
            },
            confirmButton = {
                Button(onClick = { showShareDialog = false }) {
                    Text("Close")
                }
            }
        )
    }
}
