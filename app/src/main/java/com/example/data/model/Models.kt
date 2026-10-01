package com.example.data.model

enum class Category(val displayName: String, val iconName: String) {
    ALL("All", "Apps"),
    STUDY("Study", "School"),
    CAREER("Career", "Work"),
    LIFE("Life", "Home"),
    HEALTH("Health", "Favorite"),
    RELAX("Relax", "Spa"),
    FUN("Fun", "Mood"),
    PERSONAL_DEV("Self", "AutoAwesome");

    companion object {
        fun fromString(value: String): Category {
            return entries.firstOrNull { it.name.equals(value, ignoreCase = true) || it.displayName.equals(value, ignoreCase = true) } ?: ALL
        }
    }
}

enum class Difficulty {
    EASY, MEDIUM, HARD
}

enum class EnergyLevel {
    LOW, MEDIUM, HIGH
}

enum class Mood(val label: String, val emoji: String, val description: String) {
    ENERGETIC("Energetic", "⚡", "Ready to tackle something active or challenging"),
    NORMAL("Normal", "🙂", "Good baseline energy for whatever fits"),
    TIRED("Tired", "😴", "Low battery - prefer gentle, restorative activities"),
    STRESSED("Stressed", "😵", "Overwhelmed - need calm, grounding, or a breath"),
    RELAXED("Relaxed", "😌", "Chilled out - open to quiet focus or leisure")
}

enum class PreferredMode(val label: String, val description: String) {
    BALANCED("Balanced", "A mindful mix of productivity and restorative breaks"),
    PRODUCTIVE("Productive", "Prioritize learning, career wins, and goals"),
    RELAXED("Relaxed", "Prioritize unwinding, low-effort recharge, and rest")
}

enum class SessionStatus {
    COMPLETED, PARTIAL, SKIPPED
}

data class RecommendedActivity(
    val activityId: String,
    val title: String,
    val description: String,
    val category: Category,
    val durationMinutes: Int,
    val difficulty: Difficulty,
    val energyLevel: EnergyLevel,
    val isRest: Boolean = false,
    val tags: List<String> = emptyList(),
    val matchScore: Float = 0f,
    val matchReason: String = ""
)

data class CalendarGap(
    val startMillis: Long,
    val endMillis: Long,
    val durationMinutes: Int,
    val titleBefore: String,
    val titleAfter: String
)

data class AiMicroPlan(
    val prompt: String,
    val headline: String,
    val breakdown: List<AiMicroStep>,
    val totalMinutes: Int
)

data class AiMicroStep(
    val title: String,
    val minutes: Int,
    val category: Category,
    val tip: String
)
