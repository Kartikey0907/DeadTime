package com.example

import com.example.data.local.InitialActivities
import com.example.data.model.Category
import com.example.data.model.Mood
import com.example.data.model.PreferredMode
import com.example.engine.AiAssistantEngine
import com.example.engine.RecommendationEngine
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun testRecommendationEngineTimeCeiling() {
        val activities = InitialActivities.list
        val recs5 = RecommendationEngine.generateRecommendations(
            availableMinutes = 5,
            selectedMood = Mood.NORMAL,
            selectedCategory = Category.ALL,
            activities = activities,
            recentSessions = emptyList(),
            userGoals = emptyList(),
            studentSubjects = emptyList(),
            careerField = null,
            preferredMode = PreferredMode.BALANCED,
            limit = 5
        )

        assertTrue(recs5.isNotEmpty())
        for (rec in recs5) {
            assertTrue("Duration ${rec.durationMinutes} must be <= 5", rec.durationMinutes <= 5)
        }
    }

    @Test
    fun testTiredMoodPrioritizesRestAndLowEnergy() {
        val activities = InitialActivities.list
        val recsTired = RecommendationEngine.generateRecommendations(
            availableMinutes = 15,
            selectedMood = Mood.TIRED,
            selectedCategory = Category.ALL,
            activities = activities,
            recentSessions = emptyList(),
            userGoals = emptyList(),
            studentSubjects = emptyList(),
            careerField = null,
            preferredMode = PreferredMode.BALANCED,
            limit = 4
        )

        assertTrue(recsTired.isNotEmpty())
        val top = recsTired.first()
        assertTrue(top.isRest || top.energyLevel.name == "LOW" || top.category == Category.HEALTH || top.category == Category.RELAX)
    }

    @Test
    fun testAiAssistantMicroPlanGeneration() {
        val plan = AiAssistantEngine.generateMicroPlan("I have 23 minutes and I'm tired")
        assertEquals(23, plan.totalMinutes)
        assertTrue(plan.breakdown.isNotEmpty())
        val sumMins = plan.breakdown.sumOf { it.minutes }
        assertEquals(23, sumMins)
    }
}
