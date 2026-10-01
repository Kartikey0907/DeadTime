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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.Mood
import com.example.ui.components.MoodPill
import com.example.ui.components.PocketOfTimePicker
import com.example.ui.components.TimePill
import com.example.ui.theme.*
import com.example.ui.viewmodel.DeadTimeViewModel
import com.example.ui.viewmodel.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: DeadTimeViewModel) {
    val todaySessions by viewModel.todaySessions.collectAsStateWithLifecycle()
    val totalReclaimedMinutes by viewModel.totalReclaimedMinutes.collectAsStateWithLifecycle()
    val topRoutines by viewModel.topRoutines.collectAsStateWithLifecycle()
    val calendarGaps by viewModel.calendarGaps.collectAsStateWithLifecycle()
    val userProfile by viewModel.userProfile.collectAsStateWithLifecycle()

    var showCustomTimeDialog by remember { mutableStateOf(false) }
    var customMinutesInput by remember { mutableStateOf("12") }

    var showMoodSheet by remember { mutableStateOf(false) }
    var pendingMinutes by remember { mutableIntStateOf(10) }

    val todayReclaimed = remember(todaySessions) {
        todaySessions.sumOf { it.durationActual }
    }

    val hours = todayReclaimed / 60
    val mins = todayReclaimed % 60
    val todayFormatted = if (hours > 0) "${hours}h ${mins}m" else "${mins}m"

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            modifier = Modifier.size(36.dp),
                            shape = RoundedCornerShape(10.dp),
                            color = AmberPrimary.copy(alpha = 0.15f),
                            border = androidx.compose.foundation.BorderStroke(1.dp, AmberPrimary)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.HourglassEmpty,
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp),
                                    tint = AmberPrimary
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "DeadTime",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Black,
                                letterSpacing = (-0.5).sp
                            )
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { viewModel.navigateTo(Screen.CalendarSync) },
                        modifier = Modifier.testTag("home_calendar_action")
                    ) {
                        Icon(
                            imageVector = Icons.Default.CalendarToday,
                            contentDescription = "Calendar Sync",
                            tint = if (userProfile?.calendarConnected == true) TealSecondary else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    IconButton(
                        onClick = { viewModel.navigateTo(Screen.Settings) },
                        modifier = Modifier.testTag("home_settings_action")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Daily Reclaimed Time Summary Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(22.dp))
                    .clickable { viewModel.navigateTo(Screen.Statistics) }
                    .testTag("today_stat_card"),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Today's Reclaimed Time",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(
                                text = todayFormatted,
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    fontWeight = FontWeight.Black,
                                    color = if (todayReclaimed > 0) AmberPrimary else MaterialTheme.colorScheme.onSurface
                                )
                            )
                            if (todaySessions.isNotEmpty()) {
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "(${todaySessions.size} session${if (todaySessions.size > 1) "s" else ""})",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.padding(bottom = 4.dp)
                                )
                            }
                        }
                    }

                    Surface(
                        shape = CircleShape,
                        color = AmberPrimary.copy(alpha = 0.15f),
                        modifier = Modifier.size(48.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Timeline,
                                contentDescription = null,
                                tint = AmberPrimary
                            )
                        }
                    }
                }
            }

            // Calendar Gap Card (if found)
            if (calendarGaps.isNotEmpty()) {
                val gap = calendarGaps.first()
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(18.dp))
                        .clickable {
                            pendingMinutes = gap.durationMinutes
                            showMoodSheet = true
                        },
                    colors = CardDefaults.cardColors(
                        containerColor = TealSecondary.copy(alpha = 0.12f)
                    ),
                    border = androidx.compose.foundation.BorderStroke(1.dp, TealSecondary.copy(alpha = 0.5f))
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.EventAvailable,
                            contentDescription = null,
                            tint = TealSecondary,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "You have ${gap.durationMinutes} minutes free",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "Between '${gap.titleBefore}' and '${gap.titleAfter}'",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Button(
                            onClick = {
                                pendingMinutes = gap.durationMinutes
                                showMoodSheet = true
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = TealSecondary),
                            shape = RoundedCornerShape(10.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                        ) {
                            Text("Use it", style = MaterialTheme.typography.labelSmall)
                        }
                    }
                }
            }

            // Routine Prompt (if learned patterns exist)
            if (topRoutines.isNotEmpty() && userProfile?.routineLearningEnabled != false) {
                val routine = topRoutines.first()
                val hourStr = if (routine.hourOfDay > 12) "${routine.hourOfDay - 12}:${routine.minuteOfDay.toString().padStart(2, '0')} PM" else "${routine.hourOfDay}:${routine.minuteOfDay.toString().padStart(2, '0')} AM"
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.25f))
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = IndigoTertiary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "You often have a few minutes around $hourStr. Want a quick activity?",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            // PRIMARY HERO QUESTION: "How much time do you have?"
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "How much time do you have?",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Black,
                        letterSpacing = (-0.5).sp
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Adjust your available pocket of time. DeadTime recommends activities that fit realistically.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            var pocketDuration by remember { mutableIntStateOf(15) }

            // Interactive Pocket of Time Slider & Stepper Picker
            PocketOfTimePicker(
                durationMinutes = pocketDuration,
                onDurationChanged = { newDuration ->
                    pocketDuration = newDuration
                },
                onDirectInputClick = {
                    customMinutesInput = pocketDuration.toString()
                    showCustomTimeDialog = true
                }
            )

            // Primary Find Activities CTA for the chosen pocket
            Button(
                onClick = {
                    pendingMinutes = pocketDuration
                    showMoodSheet = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .testTag("find_activities_for_pocket_button"),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AmberPrimary,
                    contentColor = Color(0xFF1E1500)
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Find Activities for $pocketDuration min",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            }

            // Quick Presets Row Divider
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(modifier = Modifier.weight(1f), color = MaterialTheme.colorScheme.outline.copy(alpha = 0.25f))
                Text(
                    text = "OR ONE-TAP LAUNCH",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
                HorizontalDivider(modifier = Modifier.weight(1f), color = MaterialTheme.colorScheme.outline.copy(alpha = 0.25f))
            }

            // Large Time Grid (5, 10, 15, 20, 30, 45)
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    TimePill(
                        minutes = 5,
                        isSelected = false,
                        onClick = {
                            pendingMinutes = 5
                            showMoodSheet = true
                        },
                        modifier = Modifier.weight(1f)
                    )
                    TimePill(
                        minutes = 10,
                        isSelected = false,
                        onClick = {
                            pendingMinutes = 10
                            showMoodSheet = true
                        },
                        modifier = Modifier.weight(1f)
                    )
                    TimePill(
                        minutes = 15,
                        isSelected = false,
                        onClick = {
                            pendingMinutes = 15
                            showMoodSheet = true
                        },
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    TimePill(
                        minutes = 20,
                        isSelected = false,
                        onClick = {
                            pendingMinutes = 20
                            showMoodSheet = true
                        },
                        modifier = Modifier.weight(1f)
                    )
                    TimePill(
                        minutes = 30,
                        isSelected = false,
                        onClick = {
                            pendingMinutes = 30
                            showMoodSheet = true
                        },
                        modifier = Modifier.weight(1f)
                    )
                    TimePill(
                        minutes = 45,
                        isSelected = false,
                        onClick = {
                            pendingMinutes = 45
                            showMoodSheet = true
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // Quick Start Custom Duration: "I have 12 minutes"
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .clickable { showCustomTimeDialog = true }
                    .testTag("custom_time_button"),
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surfaceVariant,
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.EditCalendar,
                            contentDescription = null,
                            tint = AmberPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Have an exact duration? (e.g. 12 min)",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Intentional Rest (Major Feature)
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(18.dp))
                    .clickable {
                        viewModel.startIntentionalRest(durationMinutes = 10)
                    }
                    .testTag("intentional_rest_button"),
                shape = RoundedCornerShape(18.dp),
                color = CategoryRelax.copy(alpha = 0.12f),
                border = androidx.compose.foundation.BorderStroke(1.2.dp, CategoryRelax.copy(alpha = 0.45f))
            ) {
                Row(
                    modifier = Modifier.padding(18.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = CircleShape,
                        color = CategoryRelax.copy(alpha = 0.2f),
                        modifier = Modifier.size(42.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Spa,
                                contentDescription = null,
                                tint = CategoryRelax,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "I want to rest.",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Rest is valuable. Breathing, quiet time, or doing nothing.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = null,
                        tint = CategoryRelax
                    )
                }
            }

            // Modes & Quick Actions Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Student Mode button
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(14.dp))
                        .clickable { viewModel.navigateTo(Screen.StudentMode) }
                        .testTag("home_student_mode_button"),
                    shape = RoundedCornerShape(14.dp),
                    color = CategoryStudy.copy(alpha = 0.1f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, CategoryStudy.copy(alpha = 0.3f))
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.School, contentDescription = null, tint = CategoryStudy, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Student Mode", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold))
                    }
                }

                // Career Mode button
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(14.dp))
                        .clickable { viewModel.navigateTo(Screen.CareerMode) }
                        .testTag("home_career_mode_button"),
                    shape = RoundedCornerShape(14.dp),
                    color = CategoryCareer.copy(alpha = 0.1f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, CategoryCareer.copy(alpha = 0.3f))
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Work, contentDescription = null, tint = CategoryCareer, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Career Mode", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold))
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }

    // Custom Duration Input Dialog
    if (showCustomTimeDialog) {
        AlertDialog(
            onDismissRequest = { showCustomTimeDialog = false },
            title = { Text("How much time do you have?") },
            text = {
                Column {
                    Text(
                        text = "Enter any number of minutes:",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = customMinutesInput,
                        onValueChange = { customMinutesInput = it.filter { ch -> ch.isDigit() }.take(3) },
                        label = { Text("Minutes") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("custom_minutes_input"),
                        shape = RoundedCornerShape(12.dp)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val minsInt = customMinutesInput.toIntOrNull()?.coerceIn(1, 120) ?: 12
                        showCustomTimeDialog = false
                        pendingMinutes = minsInt
                        showMoodSheet = true
                    }
                ) {
                    Text("Continue")
                }
            },
            dismissButton = {
                TextButton(onClick = { showCustomTimeDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    // Mood / Energy Selection Bottom Sheet (Skippable)
    if (showMoodSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                // If dismissed or skipped, proceed with neutral mood
                showMoodSheet = false
                viewModel.selectMood(null)
                viewModel.selectTimeAndProceed(pendingMinutes)
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 36.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "You've got $pendingMinutes minutes.",
                            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = "How are you feeling right now?",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    TextButton(
                        onClick = {
                            showMoodSheet = false
                            viewModel.selectMood(null)
                            viewModel.selectTimeAndProceed(pendingMinutes)
                        }
                    ) {
                        Text("Skip")
                    }
                }

                val moods = Mood.entries.toList()
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    moods.forEach { mood ->
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(14.dp))
                                .clickable {
                                    showMoodSheet = false
                                    viewModel.selectMood(mood)
                                    viewModel.selectTimeAndProceed(pendingMinutes)
                                },
                            shape = RoundedCornerShape(14.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                            )
                        ) {
                            Row(
                                modifier = Modifier.padding(14.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = mood.emoji, fontSize = 24.sp)
                                Spacer(modifier = Modifier.width(14.dp))
                                Column {
                                    Text(
                                        text = mood.label,
                                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                                    )
                                    Text(
                                        text = mood.description,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
