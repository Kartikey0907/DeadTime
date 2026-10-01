package com.example.data.repository

import com.example.data.local.DeadTimeDatabase
import com.example.data.local.entity.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.util.Calendar

class DeadTimeRepository(private val db: DeadTimeDatabase) {

    val allActivities: Flow<List<ActivityEntity>> = db.activityDao().getAllActivities()
    val allSessions: Flow<List<SessionEntity>> = db.sessionDao().getAllSessions()
    val allGoals: Flow<List<GoalEntity>> = db.goalDao().getAllGoals()
    val allSubjects: Flow<List<StudentSubjectEntity>> = db.studentDao().getAllSubjects()
    val userProfile: Flow<UserProfileEntity?> = db.userProfileDao().getUserProfile()
    val totalReclaimedMinutes: Flow<Int> = db.sessionDao().getTotalReclaimedMinutes()
    val topRoutines: Flow<List<RoutineWindowEntity>> = db.routineDao().getTopRoutines()

    fun getTodaySessions(): Flow<List<SessionEntity>> {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val startOfDay = calendar.timeInMillis
        val endOfDay = startOfDay + (24 * 60 * 60 * 1000)
        return db.sessionDao().getTodaySessions(startOfDay, endOfDay)
    }

    fun getWeeklySessions(): Flow<List<SessionEntity>> {
        val calendar = Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, -7)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        return db.sessionDao().getSessionsSince(calendar.timeInMillis)
    }

    suspend fun recordSession(
        activityId: String,
        activityTitle: String,
        category: String,
        durationPlanned: Int,
        durationActual: Int,
        status: String,
        mood: String,
        note: String = ""
    ): Long = withContext(Dispatchers.IO) {
        val now = System.currentTimeMillis()
        val session = SessionEntity(
            activityId = activityId,
            activityTitle = activityTitle,
            category = category,
            durationPlanned = durationPlanned,
            durationActual = durationActual,
            status = status,
            mood = mood,
            startedAt = now - (durationActual * 60 * 1000L),
            completedAt = now,
            note = note
        )
        val id = db.sessionDao().insertSession(session)

        // Increment routine frequency if routine learning enabled
        val profile = db.userProfileDao().getUserProfileSync()
        if (profile?.routineLearningEnabled != false) {
            val cal = Calendar.getInstance()
            val hour = cal.get(Calendar.HOUR_OF_DAY)
            val min = cal.get(Calendar.MINUTE)
            val existing = db.routineDao().findRoutineByHour(hour)
            if (existing != null) {
                db.routineDao().insertOrUpdate(
                    existing.copy(
                        sessionCount = existing.sessionCount + 1,
                        averageDuration = (existing.averageDuration + durationActual) / 2,
                        lastDetectedAt = now
                    )
                )
            } else {
                db.routineDao().insertOrUpdate(
                    RoutineWindowEntity(
                        hourOfDay = hour,
                        minuteOfDay = min,
                        sessionCount = 1,
                        averageDuration = durationActual,
                        lastDetectedAt = now
                    )
                )
            }
        }

        // Update goals if matched category
        val goals = db.goalDao().getActiveGoalsList()
        for (goal in goals) {
            if (goal.category.equals(category, ignoreCase = true) || 
                activityTitle.contains(goal.title, ignoreCase = true)) {
                val updatedMinutes = goal.completedMinutes + durationActual
                val isCompleted = updatedMinutes >= goal.targetMinutes
                db.goalDao().updateGoal(
                    goal.copy(
                        completedMinutes = updatedMinutes,
                        status = if (isCompleted) "COMPLETED" else "ACTIVE"
                    )
                )
            }
        }

        id
    }

    suspend fun createCustomActivity(
        title: String,
        description: String,
        category: String,
        duration: Int,
        difficulty: String,
        energy: String,
        isRest: Boolean = false
    ) = withContext(Dispatchers.IO) {
        val id = "custom_" + System.currentTimeMillis()
        val activity = ActivityEntity(
            activityId = id,
            title = title,
            description = description,
            category = category,
            minimumDuration = maxOf(2, duration - 3),
            maximumDuration = duration + 5,
            defaultDuration = duration,
            difficulty = difficulty,
            energyRequired = energy,
            isRest = isRest,
            isCustom = true,
            goalTags = "$category,custom",
            moodTags = "NORMAL,ENERGETIC,RELAXED"
        )
        db.activityDao().insertActivity(activity)
    }

    suspend fun createGoal(
        title: String,
        category: String,
        targetMinutes: Int,
        deadline: String,
        priority: String
    ) = withContext(Dispatchers.IO) {
        db.goalDao().insertGoal(
            GoalEntity(
                title = title,
                category = category,
                targetMinutes = targetMinutes,
                deadline = deadline,
                priority = priority
            )
        )
    }

    suspend fun deleteGoal(goal: GoalEntity) = withContext(Dispatchers.IO) {
        db.goalDao().deleteGoal(goal)
    }

    suspend fun addSubject(
        subjectName: String,
        examDate: String,
        topics: String,
        priority: String = "HIGH"
    ) = withContext(Dispatchers.IO) {
        db.studentDao().insertSubject(
            StudentSubjectEntity(
                subjectName = subjectName,
                examDate = examDate,
                topics = topics,
                priority = priority
            )
        )
    }

    suspend fun deleteSubject(subject: StudentSubjectEntity) = withContext(Dispatchers.IO) {
        db.studentDao().deleteSubject(subject)
    }

    suspend fun updateProfile(profile: UserProfileEntity) = withContext(Dispatchers.IO) {
        db.userProfileDao().insertOrUpdate(profile)
    }

    suspend fun deleteSession(session: SessionEntity) = withContext(Dispatchers.IO) {
        db.sessionDao().deleteSession(session)
    }

    suspend fun clearAllData() = withContext(Dispatchers.IO) {
        db.sessionDao().clearAll()
    }
}
