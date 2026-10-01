package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.data.local.dao.*
import com.example.data.local.entity.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        ActivityEntity::class,
        SessionEntity::class,
        GoalEntity::class,
        StudentSubjectEntity::class,
        UserProfileEntity::class,
        RoutineWindowEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class DeadTimeDatabase : RoomDatabase() {
    abstract fun activityDao(): ActivityDao
    abstract fun sessionDao(): SessionDao
    abstract fun goalDao(): GoalDao
    abstract fun studentDao(): StudentDao
    abstract fun userProfileDao(): UserProfileDao
    abstract fun routineDao(): RoutineDao

    companion object {
        @Volatile
        private var INSTANCE: DeadTimeDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): DeadTimeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DeadTimeDatabase::class.java,
                    "deadtime_db"
                ).addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        INSTANCE?.let { database ->
                            scope.launch(Dispatchers.IO) {
                                populateInitialData(database)
                            }
                        }
                    }
                }).build()
                INSTANCE = instance
                // Also verify population in case onCreate was skipped
                scope.launch(Dispatchers.IO) {
                    if (instance.activityDao().countActivities() == 0) {
                        populateInitialData(instance)
                    }
                }
                instance
            }
        }

        private suspend fun populateInitialData(database: DeadTimeDatabase) {
            // Seed all 100+ activities
            database.activityDao().insertAll(InitialActivities.list)

            // Seed default profile
            database.userProfileDao().insertOrUpdate(
                UserProfileEntity(
                    userId = "current_user",
                    name = "Alex",
                    email = "",
                    isGuest = true,
                    onboardingComplete = false,
                    preferredMode = "BALANCED",
                    interests = "Study,Career,Health,Relax",
                    typicalAvailableMinutes = 15,
                    notificationsEnabled = false,
                    studentModeEnabled = true,
                    careerModeEnabled = true,
                    careerField = "Software Engineering"
                )
            )

            // Seed default student subjects
            database.studentDao().insertSubject(
                StudentSubjectEntity(
                    subjectName = "Engineering Mathematics",
                    examDate = "Oct 20",
                    topics = "Laplace transforms, Fourier series, Differential equations",
                    priority = "HIGH"
                )
            )
            database.studentDao().insertSubject(
                StudentSubjectEntity(
                    subjectName = "Data Structures & Algorithms",
                    examDate = "Nov 12",
                    topics = "Binary Trees, Dynamic Programming, Graphs",
                    priority = "HIGH"
                )
            )

            // Seed initial goals
            database.goalDao().insertGoal(
                GoalEntity(
                    title = "Pass Semester Exams with Honors",
                    category = "STUDY",
                    targetMinutes = 120,
                    completedMinutes = 35,
                    deadline = "Nov 25",
                    priority = "HIGH"
                )
            )
            database.goalDao().insertGoal(
                GoalEntity(
                    title = "Land Summer Software Internship",
                    category = "CAREER",
                    targetMinutes = 90,
                    completedMinutes = 20,
                    deadline = "Dec 15",
                    priority = "HIGH"
                )
            )
            database.goalDao().insertGoal(
                GoalEntity(
                    title = "Consistent Daily Rest & Mobility",
                    category = "HEALTH",
                    targetMinutes = 60,
                    completedMinutes = 25,
                    deadline = "Ongoing",
                    priority = "MEDIUM"
                )
            )

            // Seed initial routine window
            database.routineDao().insertOrUpdate(
                RoutineWindowEntity(
                    hourOfDay = 16,
                    minuteOfDay = 30,
                    sessionCount = 4,
                    averageDuration = 15
                )
            )
        }
    }
}
