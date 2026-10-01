package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "activities")
data class ActivityEntity(
    @PrimaryKey val activityId: String,
    val title: String,
    val description: String,
    val category: String, // STUDY, CAREER, LIFE, HEALTH, RELAX, FUN, PERSONAL_DEV
    val minimumDuration: Int,
    val maximumDuration: Int,
    val defaultDuration: Int,
    val difficulty: String, // EASY, MEDIUM, HARD
    val energyRequired: String, // LOW, MEDIUM, HIGH
    val indoor: Boolean = true,
    val requiresPhone: Boolean = false,
    val requiresInternet: Boolean = false,
    val requiresEquipment: Boolean = false,
    val isRest: Boolean = false,
    val goalTags: String = "", // comma-separated
    val moodTags: String = "", // comma-separated
    val popularity: Int = 10,
    val completionCount: Int = 0,
    val isCustom: Boolean = false,
    val active: Boolean = true
)

@Entity(tableName = "sessions")
data class SessionEntity(
    @PrimaryKey(autoGenerate = true) val sessionId: Long = 0,
    val userId: String = "guest_user",
    val activityId: String,
    val activityTitle: String,
    val category: String,
    val durationPlanned: Int,
    val durationActual: Int,
    val status: String, // COMPLETED, PARTIAL, SKIPPED
    val mood: String, // ENERGETIC, NORMAL, TIRED, STRESSED, RELAXED
    val startedAt: Long,
    val completedAt: Long,
    val note: String = ""
)

@Entity(tableName = "goals")
data class GoalEntity(
    @PrimaryKey(autoGenerate = true) val goalId: Long = 0,
    val userId: String = "guest_user",
    val title: String,
    val category: String,
    val targetMinutes: Int = 60,
    val completedMinutes: Int = 0,
    val deadline: String = "",
    val priority: String = "MEDIUM", // HIGH, MEDIUM, LOW
    val status: String = "ACTIVE", // ACTIVE, COMPLETED
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "student_subjects")
data class StudentSubjectEntity(
    @PrimaryKey(autoGenerate = true) val subjectId: Long = 0,
    val userId: String = "guest_user",
    val subjectName: String,
    val examDate: String = "",
    val topics: String = "", // comma-separated topics
    val priority: String = "HIGH"
)

@Entity(tableName = "user_profiles")
data class UserProfileEntity(
    @PrimaryKey val userId: String = "current_user",
    val name: String = "Guest User",
    val email: String = "",
    val isGuest: Boolean = true,
    val onboardingComplete: Boolean = false,
    val preferredMode: String = "BALANCED", // BALANCED, PRODUCTIVE, RELAXED
    val interests: String = "Study,Health,Relax", // comma-separated
    val typicalAvailableMinutes: Int = 15,
    val notificationsEnabled: Boolean = false,
    val quietHoursStart: String = "22:00",
    val quietHoursEnd: String = "08:00",
    val maxNotificationsPerDay: Int = 3,
    val dailyReminderEnabled: Boolean = false,
    val dailyReminderTime: String = "09:00",
    val routineLearningEnabled: Boolean = true,
    val calendarConnected: Boolean = false,
    val studentModeEnabled: Boolean = true,
    val careerModeEnabled: Boolean = true,
    val careerField: String = "Software Development",
    val isPremium: Boolean = false,
    val themeMode: String = "SYSTEM" // SYSTEM, DARK, LIGHT
)

@Entity(tableName = "routines")
data class RoutineWindowEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: String = "guest_user",
    val hourOfDay: Int,
    val minuteOfDay: Int,
    val sessionCount: Int = 1,
    val averageDuration: Int = 15,
    val lastDetectedAt: Long = System.currentTimeMillis()
)
