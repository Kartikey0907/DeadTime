package com.example.engine

import com.example.data.model.AiMicroPlan
import com.example.data.model.AiMicroStep
import com.example.data.model.Category
import java.util.regex.Pattern

object AiAssistantEngine {

    fun generateMicroPlan(userPrompt: String): AiMicroPlan {
        val lower = userPrompt.lowercase()
        val minutes = extractMinutes(userPrompt) ?: 15

        val isTired = lower.contains("tired") || lower.contains("exhausted") || lower.contains("drained") || lower.contains("sleepy")
        val isStressed = lower.contains("stress") || lower.contains("anxious") || lower.contains("overwhelm")
        val isMath = lower.contains("math") || lower.contains("exam") || lower.contains("calculus") || lower.contains("physics")
        val isCoding = lower.contains("code") || lower.contains("program") || lower.contains("developer") || lower.contains("python")
        val isCareer = lower.contains("job") || lower.contains("internship") || lower.contains("resume") || lower.contains("interview")

        return when {
            isTired && minutes >= 18 -> {
                val walkMins = 8
                val planMins = minOf(6, minutes - walkMins - 4)
                val restMins = minutes - walkMins - planMins
                AiMicroPlan(
                    prompt = userPrompt,
                    headline = "$minutes-Minute Restorative Reset",
                    breakdown = listOf(
                        AiMicroStep("Gentle Walk or Natural Light", walkMins, Category.HEALTH, "Reactivates dopamine receptors without mental strain"),
                        AiMicroStep("Jot Down Tomorrow's Top 3 Priorities", planMins, Category.LIFE, "Clears open loops from your mind"),
                        AiMicroStep("Deep Breathing & Hydration", restMins, Category.RELAX, "Sip cold water and relax eye muscles")
                    ),
                    totalMinutes = minutes
                )
            }
            isTired -> {
                val napOrBreathe = minOf(minutes - 2, 8)
                val water = minutes - napOrBreathe
                AiMicroPlan(
                    prompt = userPrompt,
                    headline = "$minutes-Minute Gentle Recharge",
                    breakdown = listOf(
                        AiMicroStep("Rest with Eyes Closed & Lo-Fi Sounds", napOrBreathe, Category.RELAX, "Allow non-sleep deep rest"),
                        AiMicroStep("Cold Water Splash & Hydration", water, Category.HEALTH, "Reboots nervous system alertness")
                    ),
                    totalMinutes = minutes
                )
            }
            isMath || lower.contains("study") -> {
                val studyMins = (minutes * 0.75).toInt().coerceAtLeast(5)
                val testMins = minutes - studyMins
                AiMicroPlan(
                    prompt = userPrompt,
                    headline = "$minutes-Minute Active Exam Sprint",
                    breakdown = listOf(
                        AiMicroStep("Review Core Formulas & Summaries", studyMins, Category.STUDY, "Focus on high-yield formulas and definitions"),
                        AiMicroStep("Solve 2 Quick Practice Questions", testMins, Category.STUDY, "Verify application under time constraints")
                    ),
                    totalMinutes = minutes
                )
            }
            isCoding -> {
                val readMins = minOf(6, minutes / 2)
                val codeMins = minutes - readMins
                AiMicroPlan(
                    prompt = userPrompt,
                    headline = "$minutes-Minute Focused Dev Blitz",
                    breakdown = listOf(
                        AiMicroStep("Analyze Problem Constraints & Edge Cases", readMins, Category.CAREER, "Write pseudocode logic before typing"),
                        AiMicroStep("Code Solution & Run 2 Test Cases", codeMins, Category.CAREER, "Execute and optimize space-time complexity")
                    ),
                    totalMinutes = minutes
                )
            }
            isCareer -> {
                val bulletMins = minOf(10, (minutes * 0.6).toInt())
                val reviewMins = minutes - bulletMins
                AiMicroPlan(
                    prompt = userPrompt,
                    headline = "$minutes-Minute Career Accelerator",
                    breakdown = listOf(
                        AiMicroStep("Polish One Specific Project Impact Metric", bulletMins, Category.CAREER, "Quantify outcomes: reduced latency by X% or shipped to Y users"),
                        AiMicroStep("Bookmark 2 Matching Job or Internship Roles", reviewMins, Category.CAREER, "Queue up high-alignment opportunities for later")
                    ),
                    totalMinutes = minutes
                )
            }
            isStressed -> {
                val breathMins = minOf(5, minutes / 3)
                val stretchMins = minOf(6, minutes / 3)
                val calmMins = minutes - breathMins - stretchMins
                AiMicroPlan(
                    prompt = userPrompt,
                    headline = "$minutes-Minute Mindful Decompression",
                    breakdown = listOf(
                        AiMicroStep("4x4 Box Breathing", breathMins, Category.RELAX, "Signals safety to your amygdala"),
                        AiMicroStep("Neck & Shoulder Release", stretchMins, Category.HEALTH, "Unclamps physical desk tension"),
                        AiMicroStep("Quiet Tea or Screen-Free Breathing", calmMins, Category.RELAX, "Gives your mind unhurried space")
                    ),
                    totalMinutes = minutes
                )
            }
            else -> {
                val part1 = (minutes * 0.65).toInt().coerceAtLeast(3)
                val part2 = minutes - part1
                AiMicroPlan(
                    prompt = userPrompt,
                    headline = "$minutes-Minute Balanced Flow",
                    breakdown = listOf(
                        AiMicroStep("One Meaningful Micro-Win", part1, Category.STUDY, "Complete a high-leverage small task"),
                        AiMicroStep("Intentional Physical Rest & Water", part2, Category.HEALTH, "Unwind so you transition smoothly into your next hour")
                    ),
                    totalMinutes = minutes
                )
            }
        }
    }

    private fun extractMinutes(text: String): Int? {
        val pattern = Pattern.compile("(\\d+)\\s*(?:min|mins|minutes|m\\b)?", Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(text)
        if (matcher.find()) {
            return matcher.group(1)?.toIntOrNull()?.coerceIn(2, 120)
        }
        return null
    }
}
