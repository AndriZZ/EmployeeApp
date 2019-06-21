package com.mentormate.mentornet.data

import android.content.Context
import android.content.SharedPreferences
import android.text.Editable
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mentormate.mentornet.data.trainings.EmployeeTrainingsFull
import com.mentormate.mentornet.data.trainings.TrainingDao
import com.mentormate.mentornet.data.authentication.AuthCredentials
import com.mentormate.mentornet.data.authentication.AuthCredentialsDao
import com.mentormate.mentornet.data.employee.*
import com.mentormate.mentornet.data.kudos.CompleteKudoDao
import com.mentormate.mentornet.data.kudos.Kudo
import com.mentormate.mentornet.data.kudos.KudoDao
import com.mentormate.mentornet.data.leaderboard.LeaderBoard
import com.mentormate.mentornet.data.leaderboard.LeaderBoardDao
import com.mentormate.mentornet.data.leaderboard.LeaderBoardPositionConverter
import com.mentormate.mentornet.data.leaderboard.LeaderBoardPositionsConverter
import com.mentormate.mentornet.data.store.StoreItem
import com.mentormate.mentornet.data.store.StoreItemsDao
import com.mentormate.mentornet.data.trainings.АllCourseDao
import com.mentormate.mentornet.data.trainings.CourseIdsConverter
import com.mentormate.mentornet.data.trainings.KingdomConverter
import com.mentormate.mentornet.data.trainings.Training
import com.mentormate.mentornet.utilities.DATABASE_NAME
import com.commonsware.cwac.saferoom.SafeHelperFactory
import com.mentormate.mentornet.utilities.DB_PASS


@Database(
    entities = [
        Employee::class,
        AuthCredentials::class,
        Area::class,
        Client::class,
        Country::class,
        Department::class,
        Office::class,
        Position::class,
        LeaderBoard::class,
        StoreItem::class,
        Kudo::class,
        Training::class,
        EmployeeTrainingsFull::class

    ],
    version = 15,
    exportSchema = false
)
@TypeConverters(
    LeaderBoardPositionsConverter::class,
    LeaderBoardPositionConverter::class,
    KingdomConverter::class,
    CourseIdsConverter::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun employeeDao(): EmployeeDao
    abstract fun storeItemsDao(): StoreItemsDao
    abstract fun authCredentialsDao(): AuthCredentialsDao
    abstract fun generalInformationDao(): GeneralInformationDao
    abstract fun getCompleteEmployeeDao(): CompleteEmployeeDao
    abstract fun kudoDao(): KudoDao
    abstract fun completeKudoDao(): CompleteKudoDao
    abstract fun getLeaderBoardsDao(): LeaderBoardDao
    abstract fun trainingDao(): TrainingDao
    abstract fun courseDao(): АllCourseDao


    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {

            val factory = SafeHelperFactory.fromUser(Editable.Factory.getInstance().newEditable(DB_PASS))
            return Room
                .databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .openHelperFactory(factory)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}