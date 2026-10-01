package com.example.ui.viewmodel

import android.app.Application
import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.DeadTimeDatabase
import com.example.data.local.entity.*
import com.example.data.model.*
import com.example.data.repository.DeadTimeRepository
import com.example.engine.AiAssistantEngine
import com.example.engine.CalendarGapEngine
import com.example.engine.RecommendationEngine
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

sealed class Screen {
    data object Splash : Screen()
    data object Welcome : Screen()
    data object Auth : Screen()
    data object Onboarding : Screen()
    data object Home : Screen()
    data object Recommendations : Screen()
    data object ActiveTimer : Screen()
    data object Completion : Screen()
    data object Goals : Screen()
    data object CreateGoal : Screen()
    data object StudentMode : Screen()
    data object CareerMode : Screen()
    data object Statistics : Screen()
    data object History : Screen()
    data object CustomActivity : Screen()
    data object CalendarSync : Screen()
    data object PrivacyCenter : Screen()
    data object NotificationSettings : Screen()
    data object Profile : Screen()
    data object Settings : Screen()
    data object Premium : Screen()
    data object Help : Screen()
    data object About : Screen()
}

class DeadTimeViewModel(application: Application) : AndroidViewModel(application) {

    private val db = DeadTimeDatabase.getDatabase(application, viewModelScope)
    val repository = DeadTimeRepository(db)

    // Navigation State
    private val _currentScreen = MutableStateFlow<Screen>(Screen.Splash)
    val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()

    private val screenBackStack = mutableListOf<Screen>()

    // Core User Flow State
    private val _selectedMinutes = MutableStateFlow(10)
    val selectedMinutes: StateFlow<Int> = _selectedMinutes.asStateFlow()

    private val _selectedMood = MutableStateFlow<Mood?>(null)
    val selectedMood: StateFlow<Mood?> = _selectedMood.asStateFlow()

    private val _selectedCategory = MutableStateFlow(Category.ALL)
    val selectedCategory: StateFlow<Category> = _selectedCategory.asStateFlow()

    private val _recommendations = MutableStateFlow<List<RecommendedActivity>>(emptyList())
    val recommendations: StateFlow<List<RecommendedActivity>> = _recommendations.asStateFlow()

    private val _selectedActivityDetail = MutableStateFlow<RecommendedActivity?>(null)
    val selectedActivityDetail: StateFlow<RecommendedActivity?> = _selectedActivityDetail.asStateFlow()

    // Active Session & Timer State
    private val _activeActivity = MutableStateFlow<RecommendedActivity?>(null)
    val activeActivity: StateFlow<RecommendedActivity?> = _activeActivity.asStateFlow()

    private val _timerSecondsRemaining = MutableStateFlow(0)
    val timerSecondsRemaining: StateFlow<Int> = _timerSecondsRemaining.asStateFlow()

    private val _timerTotalSeconds = MutableStateFlow(0)
    val timerTotalSeconds: StateFlow<Int> = _timerTotalSeconds.asStateFlow()

    private val _isTimerRunning = MutableStateFlow(false)
    val isTimerRunning: StateFlow<Boolean> = _isTimerRunning.asStateFlow()

    private var timerJob: Job? = null

    // Completion Record State
    private val _lastCompletedTitle = MutableStateFlow("")
    val lastCompletedTitle: StateFlow<String> = _lastCompletedTitle.asStateFlow()

    private val _lastReclaimedMinutes = MutableStateFlow(0)
    val lastReclaimedMinutes: StateFlow<Int> = _lastReclaimedMinutes.asStateFlow()

    // Assistant & Calendar State
    private val _aiMicroPlan = MutableStateFlow<AiMicroPlan?>(null)
    val aiMicroPlan: StateFlow<AiMicroPlan?> = _aiMicroPlan.asStateFlow()

    private val _calendarGaps = MutableStateFlow<List<CalendarGap>>(emptyList())
    val calendarGaps: StateFlow<List<CalendarGap>> = _calendarGaps.asStateFlow()

    // Database Reactive Flows
    val userProfile: StateFlow<UserProfileEntity?> = repository.userProfile
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val todaySessions: StateFlow<List<SessionEntity>> = repository.getTodaySessions()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val weeklySessions: StateFlow<List<SessionEntity>> = repository.getWeeklySessions()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allSessions: StateFlow<List<SessionEntity>> = repository.allSessions
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val totalReclaimedMinutes: StateFlow<Int> = repository.totalReclaimedMinutes
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val allGoals: StateFlow<List<GoalEntity>> = repository.allGoals
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allSubjects: StateFlow<List<StudentSubjectEntity>> = repository.allSubjects
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val topRoutines: StateFlow<List<RoutineWindowEntity>> = repository.topRoutines
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allActivities: StateFlow<List<ActivityEntity>> = repository.allActivities
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        // Evaluate initial screen based on profile
        viewModelScope.launch {
            delay(1200) // Brief splash display
            val profile = repository.userProfile.first()
            if (profile != null && profile.onboardingComplete) {
                _currentScreen.value = Screen.Home
            } else {
                _currentScreen.value = Screen.Welcome
            }
        }
    }

    // Navigation Methods
    fun navigateTo(screen: Screen) {
        if (_currentScreen.value != screen) {
            screenBackStack.add(_currentScreen.value)
            _currentScreen.value = screen
        }
    }

    fun navigateBack(): Boolean {
        if (screenBackStack.isNotEmpty()) {
            val previous = screenBackStack.removeAt(screenBackStack.size - 1)
            _currentScreen.value = previous
            return true
        }
        if (_currentScreen.value != Screen.Home) {
            _currentScreen.value = Screen.Home
            return true
        }
        return false
    }

    // Core User Flow: Time Selection
    fun selectTimeAndProceed(minutes: Int) {
        _selectedMinutes.value = minutes
        // Refresh recommendations for selected minutes
        refreshRecommendations(minutes, _selectedMood.value, _selectedCategory.value)
        navigateTo(Screen.Recommendations)
    }

    fun selectMood(mood: Mood?) {
        _selectedMood.value = mood
        refreshRecommendations(_selectedMinutes.value, mood, _selectedCategory.value)
    }

    fun selectCategoryFilter(category: Category) {
        _selectedCategory.value = category
        refreshRecommendations(_selectedMinutes.value, _selectedMood.value, category)
    }

    fun refreshRecommendations(
        minutes: Int = _selectedMinutes.value,
        mood: Mood? = _selectedMood.value,
        category: Category = _selectedCategory.value
    ) {
        _selectedMinutes.value = minutes
        viewModelScope.launch {
            val activities = allActivities.value
            val recent = repository.allSessions.first()
            val goals = repository.allGoals.first()
            val subjects = repository.allSubjects.first()
            val profile = repository.userProfile.first()

            val mode = when (profile?.preferredMode?.uppercase()) {
                "PRODUCTIVE" -> PreferredMode.PRODUCTIVE
                "RELAXED" -> PreferredMode.RELAXED
                else -> PreferredMode.BALANCED
            }

            val result = RecommendationEngine.generateRecommendations(
                availableMinutes = minutes,
                selectedMood = mood,
                selectedCategory = category,
                activities = activities,
                recentSessions = recent,
                userGoals = goals,
                studentSubjects = subjects,
                careerField = profile?.careerField,
                preferredMode = mode
            )
            _recommendations.value = result
        }
    }

    fun surpriseMe() {
        viewModelScope.launch {
            val activities = allActivities.value
            if (activities.isNotEmpty()) {
                val surprise = RecommendationEngine.pickSurpriseActivity(_selectedMinutes.value, activities)
                _recommendations.value = listOf(surprise) + _recommendations.value.filter { it.activityId != surprise.activityId }
            }
        }
    }

    fun openActivityDetail(activity: RecommendedActivity) {
        _selectedActivityDetail.value = activity
    }

    fun closeActivityDetail() {
        _selectedActivityDetail.value = null
    }

    // Active Timer Management
    fun startActivity(activity: RecommendedActivity) {
        _activeActivity.value = activity
        val seconds = activity.durationMinutes * 60
        _timerTotalSeconds.value = seconds
        _timerSecondsRemaining.value = seconds
        _isTimerRunning.value = true
        _selectedActivityDetail.value = null

        navigateTo(Screen.ActiveTimer)
        startTimerCoroutine()
    }

    fun startIntentionalRest(durationMinutes: Int = 10) {
        val restActivity = RecommendedActivity(
            activityId = "intentional_rest_custom",
            title = "Intentional Rest & Recharge",
            description = "Step back, breathe, or do nothing without pressure. Rest is valuable.",
            category = Category.RELAX,
            durationMinutes = durationMinutes,
            difficulty = Difficulty.EASY,
            energyLevel = EnergyLevel.LOW,
            isRest = true,
            tags = listOf("Rest", "Mindful"),
            matchReason = "You chose intentional rest. Unhurried space to recharge."
        )
        startActivity(restActivity)
    }

    private fun startTimerCoroutine() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (_timerSecondsRemaining.value > 0 && _isTimerRunning.value) {
                delay(1000)
                if (_isTimerRunning.value) {
                    _timerSecondsRemaining.value = _timerSecondsRemaining.value - 1
                }
            }
            if (_timerSecondsRemaining.value <= 0 && _isTimerRunning.value) {
                // Completed naturally!
                vibrateDevice()
                completeCurrentActivity(isFullDuration = true)
            }
        }
    }

    fun toggleTimerPause() {
        _isTimerRunning.value = !_isTimerRunning.value
        if (_isTimerRunning.value) {
            startTimerCoroutine()
        }
    }

    fun completeCurrentActivity(isFullDuration: Boolean = true) {
        timerJob?.cancel()
        _isTimerRunning.value = false
        val activity = _activeActivity.value ?: return

        val plannedMinutes = activity.durationMinutes
        val elapsedSeconds = _timerTotalSeconds.value - _timerSecondsRemaining.value
        val actualMinutes = if (isFullDuration) {
            plannedMinutes
        } else {
            maxOf(1, elapsedSeconds / 60)
        }

        viewModelScope.launch {
            repository.recordSession(
                activityId = activity.activityId,
                activityTitle = activity.title,
                category = activity.category.name,
                durationPlanned = plannedMinutes,
                durationActual = actualMinutes,
                status = "COMPLETED",
                mood = (_selectedMood.value ?: Mood.NORMAL).name
            )

            _lastCompletedTitle.value = activity.title
            _lastReclaimedMinutes.value = actualMinutes
            _activeActivity.value = null
            navigateTo(Screen.Completion)
        }
    }

    fun stopSessionEarly(status: SessionStatus) {
        timerJob?.cancel()
        _isTimerRunning.value = false
        val activity = _activeActivity.value ?: run {
            navigateTo(Screen.Home)
            return
        }

        val elapsedSeconds = _timerTotalSeconds.value - _timerSecondsRemaining.value
        val actualMinutes = maxOf(1, elapsedSeconds / 60)

        if (status == SessionStatus.SKIPPED) {
            _activeActivity.value = null
            navigateTo(Screen.Home)
            return
        }

        viewModelScope.launch {
            repository.recordSession(
                activityId = activity.activityId,
                activityTitle = activity.title,
                category = activity.category.name,
                durationPlanned = activity.durationMinutes,
                durationActual = actualMinutes,
                status = status.name,
                mood = (_selectedMood.value ?: Mood.NORMAL).name
            )

            _lastCompletedTitle.value = activity.title
            _lastReclaimedMinutes.value = actualMinutes
            _activeActivity.value = null
            navigateTo(Screen.Completion)
        }
    }

    private fun vibrateDevice() {
        try {
            val vibrator = getApplication<Application>().getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
            vibrator?.let {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    it.vibrate(VibrationEffect.createOneShot(350, VibrationEffect.DEFAULT_AMPLITUDE))
                } else {
                    @Suppress("DEPRECATION")
                    it.vibrate(350)
                }
            }
        } catch (_: Exception) {}
    }

    // Onboarding Completion
    fun completeOnboarding(
        mode: String,
        interests: String,
        typicalMinutes: Int,
        remindersEnabled: Boolean
    ) {
        viewModelScope.launch {
            val existing = repository.userProfile.first()
            val updated = (existing ?: UserProfileEntity()).copy(
                onboardingComplete = true,
                preferredMode = mode,
                interests = interests,
                typicalAvailableMinutes = typicalMinutes,
                notificationsEnabled = remindersEnabled
            )
            repository.updateProfile(updated)
            navigateTo(Screen.Home)
        }
    }

    // Auth & Guest Actions
    fun continueAsGuest() {
        viewModelScope.launch {
            val existing = repository.userProfile.first()
            val updated = (existing ?: UserProfileEntity()).copy(
                isGuest = true,
                name = "Guest User"
            )
            repository.updateProfile(updated)
            if (updated.onboardingComplete) {
                navigateTo(Screen.Home)
            } else {
                navigateTo(Screen.Onboarding)
            }
        }
    }

    fun authenticateUser(name: String, email: String) {
        viewModelScope.launch {
            val existing = repository.userProfile.first()
            val updated = (existing ?: UserProfileEntity()).copy(
                isGuest = false,
                name = name.ifBlank { "User" },
                email = email
            )
            repository.updateProfile(updated)
            if (updated.onboardingComplete) {
                navigateTo(Screen.Home)
            } else {
                navigateTo(Screen.Onboarding)
            }
        }
    }

    // Custom Activities
    fun saveCustomActivity(
        title: String,
        description: String,
        category: String,
        duration: Int,
        difficulty: String,
        energy: String
    ) {
        viewModelScope.launch {
            repository.createCustomActivity(
                title = title,
                description = description,
                category = category,
                duration = duration,
                difficulty = difficulty,
                energy = energy
            )
            refreshRecommendations()
            navigateTo(Screen.Home)
        }
    }

    // Goal Management
    fun createGoal(
        title: String,
        category: String,
        targetMinutes: Int,
        deadline: String,
        priority: String
    ) {
        viewModelScope.launch {
            repository.createGoal(title, category, targetMinutes, deadline, priority)
            navigateTo(Screen.Goals)
        }
    }

    fun deleteGoal(goal: GoalEntity) {
        viewModelScope.launch {
            repository.deleteGoal(goal)
        }
    }

    // Student Mode
    fun addStudentSubject(name: String, examDate: String, topics: String) {
        viewModelScope.launch {
            repository.addSubject(name, examDate, topics)
        }
    }

    fun deleteStudentSubject(subject: StudentSubjectEntity) {
        viewModelScope.launch {
            repository.deleteSubject(subject)
        }
    }

    // AI Assistant
    fun requestAiMicroPlan(userQuery: String) {
        val plan = AiAssistantEngine.generateMicroPlan(userQuery)
        _aiMicroPlan.value = plan
    }

    fun dismissAiMicroPlan() {
        _aiMicroPlan.value = null
    }

    // Calendar Integration
    fun scanCalendar(context: Context) {
        val gaps = CalendarGapEngine.findUpcomingGaps(context)
        _calendarGaps.value = gaps
        viewModelScope.launch {
            val profile = repository.userProfile.first()
            if (profile != null && CalendarGapEngine.hasCalendarPermission(context)) {
                repository.updateProfile(profile.copy(calendarConnected = true))
            }
        }
    }

    fun disconnectCalendar() {
        viewModelScope.launch {
            val profile = repository.userProfile.first()
            if (profile != null) {
                repository.updateProfile(profile.copy(calendarConnected = false))
                _calendarGaps.value = emptyList()
            }
        }
    }

    // Profile & Settings
    fun updateThemeMode(mode: String) {
        viewModelScope.launch {
            val profile = repository.userProfile.first()
            if (profile != null) {
                repository.updateProfile(profile.copy(themeMode = mode))
            }
        }
    }

    fun updatePreferences(
        studentMode: Boolean,
        careerMode: Boolean,
        careerField: String,
        routineLearning: Boolean,
        notifications: Boolean
    ) {
        viewModelScope.launch {
            val profile = repository.userProfile.first()
            if (profile != null) {
                repository.updateProfile(
                    profile.copy(
                        studentModeEnabled = studentMode,
                        careerModeEnabled = careerMode,
                        careerField = careerField,
                        routineLearningEnabled = routineLearning,
                        notificationsEnabled = notifications
                    )
                )
            }
        }
    }

    fun togglePremium(isPremium: Boolean) {
        viewModelScope.launch {
            val profile = repository.userProfile.first()
            if (profile != null) {
                repository.updateProfile(profile.copy(isPremium = isPremium))
            }
        }
    }

    fun clearAllUserData() {
        viewModelScope.launch {
            repository.clearAllData()
            navigateTo(Screen.Home)
        }
    }

    fun deleteSession(session: SessionEntity) {
        viewModelScope.launch {
            repository.deleteSession(session)
        }
    }
}
