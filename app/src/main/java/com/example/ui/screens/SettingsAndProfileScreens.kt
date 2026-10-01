package com.example.ui.screens

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.theme.*
import com.example.ui.viewmodel.DeadTimeViewModel
import com.example.ui.viewmodel.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(viewModel: DeadTimeViewModel) {
    val profile by viewModel.userProfile.collectAsStateWithLifecycle()
    val totalReclaimedMinutes by viewModel.totalReclaimedMinutes.collectAsStateWithLifecycle()
    val allSessions by viewModel.allSessions.collectAsStateWithLifecycle()

    val completedCount = allSessions.count { it.status == "COMPLETED" }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile & Progress", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { viewModel.navigateBack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            // User Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
            ) {
                Row(
                    modifier = Modifier.padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        modifier = Modifier.size(54.dp),
                        shape = CircleShape,
                        color = AmberPrimary.copy(alpha = 0.2f),
                        border = androidx.compose.foundation.BorderStroke(1.5.dp, AmberPrimary)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(Icons.Default.Person, contentDescription = null, tint = AmberPrimary, modifier = Modifier.size(28.dp))
                        }
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = profile?.name ?: "Guest User",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = if (profile?.isGuest == true) "Guest Mode (Local Only)" else profile?.email.orEmpty().ifBlank { "Signed In" },
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    if (profile?.isGuest == true) {
                        OutlinedButton(
                            onClick = { viewModel.navigateTo(Screen.Auth) },
                            shape = RoundedCornerShape(10.dp),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text("Sign In", style = MaterialTheme.typography.labelSmall)
                        }
                    }
                }
            }

            // Milestones Row
            Text("Reclaimed Milestones", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                val m1 = totalReclaimedMinutes >= 30
                val m2 = totalReclaimedMinutes >= 60
                val m3 = completedCount >= 5

                MilestoneBadge(title = "30m", desc = "First sprint", unlocked = m1, modifier = Modifier.weight(1f))
                MilestoneBadge(title = "1 Hour", desc = "Time master", unlocked = m2, modifier = Modifier.weight(1f))
                MilestoneBadge(title = "5 Done", desc = "Consistency", unlocked = m3, modifier = Modifier.weight(1f))
            }

            // Quick Hub Items
            Text("Features & Hubs", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))

            val hubItems = listOf(
                Triple("Personal Goals", "Track targets and micro-tasks", Screen.Goals),
                Triple("Student Mode", "Subjects, formulas and exams", Screen.StudentMode),
                Triple("Career Mode", "Resume bullets and interview wins", Screen.CareerMode),
                Triple("Create Custom Activity", "Add your personal routine items", Screen.CustomActivity),
                Triple("Privacy Center", "Permissions transparency and zero tracking", Screen.PrivacyCenter),
                Triple("Notification Settings", "Quiet hours & gentle prompts", Screen.NotificationSettings),
                Triple("DeadTime Plus", "Preview premium features", Screen.Premium),
                Triple("Help & FAQ", "Core principles and answers", Screen.Help),
                Triple("About DeadTime", "Version 1.0 & mission", Screen.About)
            )

            hubItems.forEach { (title, subtitle, screen) ->
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .clickable { viewModel.navigateTo(screen) },
                    shape = RoundedCornerShape(14.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.25f))
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(title, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                            Text(subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Icon(Icons.Default.ChevronRight, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }
    }
}

@Composable
fun MilestoneBadge(title: String, desc: String, unlocked: Boolean, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        color = if (unlocked) AmberPrimary.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
        border = androidx.compose.foundation.BorderStroke(1.dp, if (unlocked) AmberPrimary else MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(if (unlocked) "🏆" else "🔒", fontSize = 20.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(title, style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold), color = if (unlocked) AmberPrimary else MaterialTheme.colorScheme.onSurfaceVariant)
            Text(desc, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(viewModel: DeadTimeViewModel) {
    val profile by viewModel.userProfile.collectAsStateWithLifecycle()
    var showClearDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { viewModel.navigateBack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Appearance", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                listOf("DARK", "LIGHT", "SYSTEM").forEach { theme ->
                    val isSelected = profile?.themeMode.equals(theme, ignoreCase = true)
                    FilterChip(
                        selected = isSelected,
                        onClick = { viewModel.updateThemeMode(theme) },
                        label = { Text(theme.lowercase().replaceFirstChar { it.uppercase() }) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Text("Preferences", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))

            // Routine Learning Switch
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                color = MaterialTheme.colorScheme.surfaceVariant
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Routine Learning", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))
                        Text("Notice voluntary time patterns to make conservative suggestions", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Switch(
                        checked = profile?.routineLearningEnabled != false,
                        onCheckedChange = { checked ->
                            profile?.let {
                                viewModel.updatePreferences(it.studentModeEnabled, it.careerModeEnabled, it.careerField, checked, it.notificationsEnabled)
                            }
                        }
                    )
                }
            }

            // Student Mode Switch
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                color = MaterialTheme.colorScheme.surfaceVariant
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Student Mode Features", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))
                        Text("Subjects, formula sprints, and exam support", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Switch(
                        checked = profile?.studentModeEnabled != false,
                        onCheckedChange = { checked ->
                            profile?.let {
                                viewModel.updatePreferences(checked, it.careerModeEnabled, it.careerField, it.routineLearningEnabled, it.notificationsEnabled)
                            }
                        }
                    )
                }
            }

            // Career Mode Switch
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                color = MaterialTheme.colorScheme.surfaceVariant
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Career Mode Features", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))
                        Text("Resume bullets, coding tasks, and networking sprints", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Switch(
                        checked = profile?.careerModeEnabled != false,
                        onCheckedChange = { checked ->
                            profile?.let {
                                viewModel.updatePreferences(it.studentModeEnabled, checked, it.careerField, it.routineLearningEnabled, it.notificationsEnabled)
                            }
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text("Data & Privacy", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))

            OutlinedButton(
                onClick = { showClearDialog = true },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error),
                shape = RoundedCornerShape(14.dp)
            ) {
                Icon(Icons.Default.DeleteForever, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Reset All Reclaimed History")
            }
        }
    }

    if (showClearDialog) {
        AlertDialog(
            onDismissRequest = { showClearDialog = false },
            title = { Text("Reset All History?") },
            text = { Text("This will permanently clear all recorded sessions. Goal definitions and preset activities will remain.") },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.clearAllUserData()
                        showClearDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Clear Everything")
                }
            },
            dismissButton = {
                TextButton(onClick = { showClearDialog = false }) { Text("Cancel") }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivacyCenterScreen(viewModel: DeadTimeViewModel) {
    val profile by viewModel.userProfile.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Privacy Center", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { viewModel.navigateBack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = TealSecondary.copy(alpha = 0.12f)),
                border = androidx.compose.foundation.BorderStroke(1.dp, TealSecondary.copy(alpha = 0.4f))
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Security, contentDescription = null, tint = TealSecondary)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Privacy is a Core Feature", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "DeadTime works 100% offline. We never sell data, never track background location, and never upload your personal calendar details.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Text("Current Device Permissions", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))

            val permissions = listOf(
                Triple("Calendar Access", if (profile?.calendarConnected == true) "ON (Optional)" else "OFF", "Only used locally to calculate gaps if enabled"),
                Triple("Location Tracking", "OFF", "Never requested. DeadTime doesn't track where you are."),
                Triple("Physical Activity Recognition", "OFF", "Never requested. You voluntarily start sessions."),
                Triple("Microphone & Audio", "OFF", "Never requested."),
                Triple("Contact List", "OFF", "Never requested."),
                Triple("Reminders & Notifications", if (profile?.notificationsEnabled == true) "ON" else "OFF", "Conservative, zero-spam positive reminders")
            )

            permissions.forEach { (name, status, desc) ->
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.25f))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(name, style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = if (status.startsWith("ON")) TealSecondary.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surface
                            ) {
                                Text(
                                    text = status,
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                    color = if (status.startsWith("ON")) TealSecondary else MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(desc, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationSettingsScreen(viewModel: DeadTimeViewModel) {
    val profile by viewModel.userProfile.collectAsStateWithLifecycle()
    var enabled by remember { mutableStateOf(profile?.notificationsEnabled ?: false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Notification Settings", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { viewModel.navigateBack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                color = MaterialTheme.colorScheme.surfaceVariant
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Allow Gentle Prompts", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))
                        Text("Useful, never annoying prompts like 'You've got 10 minutes before your next event'", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Switch(
                        checked = enabled,
                        onCheckedChange = {
                            enabled = it
                            profile?.let { p ->
                                viewModel.updatePreferences(p.studentModeEnabled, p.careerModeEnabled, p.careerField, p.routineLearningEnabled, it)
                            }
                        }
                    )
                }
            }

            Text("Quiet Hours", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
            Text("No notifications will ever be sent during your quiet hours: 10:00 PM – 8:00 AM.", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)

            Text("Daily Frequency Cap", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
            Text("Strict maximum of 2 gentle prompts per day. Zero spam.", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarSyncScreen(viewModel: DeadTimeViewModel) {
    val context = LocalContext.current
    val profile by viewModel.userProfile.collectAsStateWithLifecycle()
    val calendarGaps by viewModel.calendarGaps.collectAsStateWithLifecycle()

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            viewModel.scanCalendar(context)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Calendar Gaps", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { viewModel.navigateBack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "📅 Optional Calendar Intelligence",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "If enabled, DeadTime reads your device calendar to spot free gaps between classes or meetings (e.g. 'You have 20 minutes between classes').\n\nNo calendar data is ever uploaded.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            if (profile?.calendarConnected == true || calendarGaps.isNotEmpty()) {
                Text("Detected Gaps Today", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))

                if (calendarGaps.isEmpty()) {
                    Text("No free gaps currently detected in the next 8 hours.", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                } else {
                    calendarGaps.forEach { gap ->
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(14.dp))
                                .clickable {
                                    viewModel.selectTimeAndProceed(gap.durationMinutes)
                                },
                            shape = RoundedCornerShape(14.dp),
                            color = TealSecondary.copy(alpha = 0.12f),
                            border = androidx.compose.foundation.BorderStroke(1.dp, TealSecondary.copy(alpha = 0.4f))
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text("${gap.durationMinutes} min free", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                                    Text("Between '${gap.titleBefore}' and '${gap.titleAfter}'", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                                Button(onClick = { viewModel.selectTimeAndProceed(gap.durationMinutes) }) {
                                    Text("Use")
                                }
                            }
                        }
                    }
                }

                OutlinedButton(
                    onClick = { viewModel.disconnectCalendar() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Disconnect Calendar")
                }
            } else {
                Button(
                    onClick = {
                        permissionLauncher.launch(Manifest.permission.READ_CALENDAR)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Icon(Icons.Default.CalendarToday, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Connect Calendar (Optional)")
                }

                Text(
                    text = "You never need a calendar to use DeadTime. Quick manual duration taps on the home screen always work instantly.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PremiumScreen(viewModel: DeadTimeViewModel) {
    val profile by viewModel.userProfile.collectAsStateWithLifecycle()
    val isPremium = profile?.isPremium == true

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("DeadTime Plus", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { viewModel.navigateBack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = AmberPrimary.copy(alpha = 0.15f)),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, AmberPrimary)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text("✨ DeadTime Plus", style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold), color = AmberPrimary)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("All core session features are 100% free and offline forever. Plus supports ongoing development and unlocks advanced tools.", style = MaterialTheme.typography.bodyMedium)
                }
            }

            Text("Plus Features", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))

            val perks = listOf(
                "Advanced AI Multi-Step Micro-Schedules",
                "Unlimited Custom Activities & Goals",
                "Routine Prediction & Gap Insights",
                "Detailed 30-Day Reclaimed Analytics",
                "Cross-Device Cloud Backup"
            )

            perks.forEach { perk ->
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant
                ) {
                    Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = AmberPrimary, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(perk, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium))
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = { viewModel.togglePremium(!isPremium) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AmberPrimary, contentColor = Color(0xFF1E1500))
            ) {
                Text(if (isPremium) "Plus Active (Tap to Toggle)" else "Try DeadTime Plus (Demo)", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpScreen(viewModel: DeadTimeViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Help & FAQ", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { viewModel.navigateBack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            val faqs = listOf(
                "Why DeadTime?" to "Most time management apps ask you to schedule every minute of your day. DeadTime is the opposite: it gives you immediate, realistic choices for the 5, 10, or 20 minutes you happen to have right now.",
                "Does rest count as reclaimed time?" to "Absolutely! Intentional rest, breathing, taking a walk, and doing nothing are vital. DeadTime treats intentional recharge as just as valuable as studying or work.",
                "Do I need to connect a calendar?" to "Never. DeadTime is designed around manual duration buttons on the home screen so you can jump into a session in under 3 seconds.",
                "How does recommendation scoring work?" to "The engine checks your available time (strict ceiling), your current mood, your student or career goals, and recently completed activities so suggestions remain fresh and achievable."
            )

            faqs.forEach { (q, a) ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(q, style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(a, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(viewModel: DeadTimeViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("About DeadTime", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { viewModel.navigateBack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Surface(
                modifier = Modifier.size(72.dp),
                shape = RoundedCornerShape(20.dp),
                color = AmberPrimary.copy(alpha = 0.2f),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, AmberPrimary)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.HourglassBottom, contentDescription = null, tint = AmberPrimary, modifier = Modifier.size(36.dp))
                }
            }

            Text("DeadTime", style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Black))
            Text("Version 1.0 • Build 2026", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)

            Text(
                text = "Make your spare minutes count.",
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
                color = AmberPrimary
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text("Core Principle", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "DeadTime never tells you 'You are wasting time.' Instead, we ask 'You've got 12 minutes. Want to use them?' Reclaim your minutes with joy and zero guilt.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
