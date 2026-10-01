package com.example.engine

import com.example.data.local.entity.*
import com.example.data.model.*
import kotlin.math.abs
import kotlin.random.Random

object RecommendationEngine {

    fun generateRecommendations(
        availableMinutes: Int,
        selectedMood: Mood?,
        selectedCategory: Category?,
        activities: List<ActivityEntity>,
        recentSessions: List<SessionEntity>,
        userGoals: List<GoalEntity>,
        studentSubjects: List<StudentSubjectEntity>,
        careerField: String?,
        preferredMode: PreferredMode = PreferredMode.BALANCED,
        limit: Int = 4
    ): List<RecommendedActivity> {
        // 1. Strict time filter: activity must fit within available time
        // Allow activities with minimumDuration <= availableMinutes
        val candidates = activities.filter { activity ->
            activity.active && activity.minimumDuration <= availableMinutes
        }

        if (candidates.isEmpty()) {
            // Fallback: relax min duration slightly or return empty
            return emptyList()
        }

        val recentActivityIds = recentSessions.take(8).map { it.activityId }.toSet()
        val completedActivityCount = recentSessions.groupBy { it.activityId }.mapValues { it.value.size }

        // Category matching filter if specific category chosen (other than ALL)
        val filteredCandidates = if (selectedCategory != null && selectedCategory != Category.ALL) {
            val matching = candidates.filter { 
                it.category.equals(selectedCategory.name, ignoreCase = true) 
            }
            if (matching.isNotEmpty()) matching else candidates
        } else {
            candidates
        }

        // 2. Score candidates
        val scored = filteredCandidates.map { activity ->
            var score = 100f
            val reasons = mutableListOf<String>()

            // A. Duration fit: prefer activities whose default or max duration is close to availableMinutes
            val diff = abs(activity.defaultDuration - availableMinutes)
            score -= (diff * 2.5f)
            if (activity.defaultDuration <= availableMinutes && activity.maximumDuration >= availableMinutes) {
                score += 15f
                reasons.add("Fits your ${availableMinutes}m window perfectly")
            }

            // B. Mood fit
            val mood = selectedMood ?: Mood.NORMAL
            val activityMoods = activity.moodTags.split(",").map { it.trim().uppercase() }
            if (activityMoods.contains(mood.name)) {
                score += 25f
                reasons.add("Matches your ${mood.label.lowercase()} mood")
            }

            when (mood) {
                Mood.TIRED -> {
                    if (activity.energyRequired.equals("LOW", ignoreCase = true) || activity.isRest) {
                        score += 30f
                        reasons.add("Gentle and low-effort for when tired")
                    } else if (activity.energyRequired.equals("HIGH", ignoreCase = true)) {
                        score -= 50f
                    }
                }
                Mood.STRESSED -> {
                    if (activity.category.equals("RELAX", ignoreCase = true) || 
                        activity.category.equals("HEALTH", ignoreCase = true) || activity.isRest) {
                        score += 35f
                        reasons.add("Calming and restorative for stress relief")
                    }
                }
                Mood.ENERGETIC -> {
                    if (activity.energyRequired.equals("HIGH", ignoreCase = true) || 
                        activity.energyRequired.equals("MEDIUM", ignoreCase = true)) {
                        score += 20f
                        reasons.add("Engaging task matching high energy")
                    }
                }
                Mood.RELAXED -> {
                    if (activity.category.equals("RELAX", ignoreCase = true) || 
                        activity.category.equals("FUN", ignoreCase = true) ||
                        activity.category.equals("PERSONAL_DEV", ignoreCase = true)) {
                        score += 15f
                    }
                }
                Mood.NORMAL -> {
                    score += 5f
                }
            }

            // C. Mode alignment
            when (preferredMode) {
                PreferredMode.PRODUCTIVE -> {
                    if (activity.category.equals("STUDY", ignoreCase = true) || 
                        activity.category.equals("CAREER", ignoreCase = true)) {
                        score += 20f
                    }
                }
                PreferredMode.RELAXED -> {
                    if (activity.category.equals("RELAX", ignoreCase = true) || 
                        activity.category.equals("FUN", ignoreCase = true) || activity.isRest) {
                        score += 25f
                    }
                }
                PreferredMode.BALANCED -> {
                    // Slight boost to diversity
                }
            }

            // D. Goal alignment
            val goalTagsList = activity.goalTags.lowercase().split(",")
            val matchesGoal = userGoals.any { goal ->
                goalTagsList.any { tag -> goal.title.lowercase().contains(tag) || goal.category.equals(activity.category, ignoreCase = true) }
            }
            if (matchesGoal) {
                score += 25f
                reasons.add("Advances one of your active goals")
            }

            // E. Student Mode / Career Mode synergy
            if (activity.category.equals("STUDY", ignoreCase = true) && studentSubjects.isNotEmpty()) {
                val subjectMatch = studentSubjects.any { subject ->
                    goalTagsList.any { tag -> subject.subjectName.lowercase().contains(tag) || subject.topics.lowercase().contains(tag) }
                }
                if (subjectMatch) {
                    score += 20f
                    reasons.add("Directly supports your upcoming exam")
                }
            }
            if (activity.category.equals("CAREER", ignoreCase = true) && !careerField.isNullOrBlank()) {
                score += 10f
            }

            // F. Variety penalty (avoid repetition)
            if (recentActivityIds.contains(activity.activityId)) {
                score -= 40f
            }
            val timesDone = completedActivityCount[activity.activityId] ?: 0
            if (timesDone > 2) {
                score -= (timesDone * 5f)
            }

            // Small jitter for freshness
            score += Random.nextFloat() * 4f

            val primaryReason = if (reasons.isNotEmpty()) {
                reasons.distinct().take(2).joinToString(" • ")
            } else {
                "Quick ${activity.defaultDuration}-minute win for right now"
            }

            val categoryEnum = Category.fromString(activity.category)
            val difficultyEnum = try {
                Difficulty.valueOf(activity.difficulty.uppercase())
            } catch (e: Exception) {
                Difficulty.EASY
            }
            val energyEnum = try {
                EnergyLevel.valueOf(activity.energyRequired.uppercase())
            } catch (e: Exception) {
                EnergyLevel.LOW
            }

            val effectiveDuration = minOf(activity.defaultDuration, availableMinutes)

            RecommendedActivity(
                activityId = activity.activityId,
                title = activity.title,
                description = activity.description,
                category = categoryEnum,
                durationMinutes = effectiveDuration,
                difficulty = difficultyEnum,
                energyLevel = energyEnum,
                isRest = activity.isRest,
                tags = activity.goalTags.split(",").map { it.trim() }.filter { it.isNotEmpty() },
                matchScore = score,
                matchReason = primaryReason
            )
        }

        // Return top distinct categories if possible to offer varied choices
        val sorted = scored.sortedByDescending { it.matchScore }
        val result = mutableListOf<RecommendedActivity>()
        val seenCategories = mutableSetOf<Category>()

        for (item in sorted) {
            if (!seenCategories.contains(item.category) && result.size < limit) {
                result.add(item)
                seenCategories.add(item.category)
            }
        }
        // Fill remaining slots
        for (item in sorted) {
            if (result.size >= limit) break
            if (!result.contains(item)) {
                result.add(item)
            }
        }

        return result
    }

    fun pickSurpriseActivity(
        availableMinutes: Int,
        activities: List<ActivityEntity>
    ): RecommendedActivity {
        val candidates = activities.filter { it.active && it.minimumDuration <= availableMinutes }
        val chosen = candidates.randomOrNull() ?: activities.first()
        val duration = minOf(chosen.defaultDuration, availableMinutes)
        return RecommendedActivity(
            activityId = chosen.activityId,
            title = chosen.title,
            description = chosen.description,
            category = Category.fromString(chosen.category),
            durationMinutes = duration,
            difficulty = Difficulty.EASY,
            energyLevel = EnergyLevel.LOW,
            isRest = chosen.isRest,
            tags = listOf("Surprise", "Spontaneous"),
            matchScore = 100f,
            matchReason = "Spontaneous pick! Zero decision fatigue."
        )
    }
}
