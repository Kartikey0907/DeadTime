package com.example.data.local.dao

import androidx.room.*
import com.example.data.local.entity.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ActivityDao {
    @Query("SELECT * FROM activities WHERE active = 1 ORDER BY popularity DESC")
    fun getAllActivities(): Flow<List<ActivityEntity>>

    @Query("SELECT * FROM activities WHERE active = 1")
    suspend fun getAllActivitiesList(): List<ActivityEntity>

    @Query("SELECT * FROM activities WHERE activityId = :id LIMIT 1")
    suspend fun getActivityById(id: String): ActivityEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(activities: List<ActivityEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActivity(activity: ActivityEntity)

    @Update
    suspend fun updateActivity(activity: ActivityEntity)

    @Delete
    suspend fun deleteActivity(activity: ActivityEntity)

    @Query("SELECT COUNT(*) FROM activities")
    suspend fun countActivities(): Int
}

@Dao
interface SessionDao {
    @Query("SELECT * FROM sessions ORDER BY completedAt DESC")
    fun getAllSessions(): Flow<List<SessionEntity>>

    @Query("SELECT * FROM sessions WHERE startedAt >= :startOfDay AND startedAt < :endOfDay ORDER BY completedAt DESC")
    fun getTodaySessions(startOfDay: Long, endOfDay: Long): Flow<List<SessionEntity>>

    @Query("SELECT * FROM sessions WHERE startedAt >= :since ORDER BY completedAt DESC")
    fun getSessionsSince(since: Long): Flow<List<SessionEntity>>

    @Query("SELECT * FROM sessions ORDER BY completedAt DESC LIMIT 20")
    suspend fun getRecentSessions(): List<SessionEntity>

    @Query("SELECT COALESCE(SUM(durationActual), 0) FROM sessions WHERE status = 'COMPLETED' OR status = 'PARTIAL'")
    fun getTotalReclaimedMinutes(): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(session: SessionEntity): Long

    @Delete
    suspend fun deleteSession(session: SessionEntity)

    @Query("DELETE FROM sessions")
    suspend fun clearAll()
}

@Dao
interface GoalDao {
    @Query("SELECT * FROM goals ORDER BY priority DESC, createdAt DESC")
    fun getAllGoals(): Flow<List<GoalEntity>>

    @Query("SELECT * FROM goals WHERE status = 'ACTIVE' ORDER BY createdAt DESC")
    suspend fun getActiveGoalsList(): List<GoalEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGoal(goal: GoalEntity): Long

    @Update
    suspend fun updateGoal(goal: GoalEntity)

    @Delete
    suspend fun deleteGoal(goal: GoalEntity)
}

@Dao
interface StudentDao {
    @Query("SELECT * FROM student_subjects ORDER BY subjectId ASC")
    fun getAllSubjects(): Flow<List<StudentSubjectEntity>>

    @Query("SELECT * FROM student_subjects")
    suspend fun getAllSubjectsList(): List<StudentSubjectEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubject(subject: StudentSubjectEntity): Long

    @Delete
    suspend fun deleteSubject(subject: StudentSubjectEntity)
}

@Dao
interface UserProfileDao {
    @Query("SELECT * FROM user_profiles WHERE userId = :id LIMIT 1")
    fun getUserProfile(id: String = "current_user"): Flow<UserProfileEntity?>

    @Query("SELECT * FROM user_profiles WHERE userId = :id LIMIT 1")
    suspend fun getUserProfileSync(id: String = "current_user"): UserProfileEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(profile: UserProfileEntity)
}

@Dao
interface RoutineDao {
    @Query("SELECT * FROM routines ORDER BY sessionCount DESC LIMIT 5")
    fun getTopRoutines(): Flow<List<RoutineWindowEntity>>

    @Query("SELECT * FROM routines WHERE hourOfDay = :hour LIMIT 1")
    suspend fun findRoutineByHour(hour: Int): RoutineWindowEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(routine: RoutineWindowEntity)
}
