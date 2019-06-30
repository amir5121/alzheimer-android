package com.amir.alzheimer.infrastructure.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.amir.alzheimer.infrastructure.database.relative.Relative
import com.amir.alzheimer.infrastructure.database.relative.RelativeDao
import com.amir.alzheimer.infrastructure.database.user.User
import com.amir.alzheimer.infrastructure.database.user.UserDao

@Database(entities = [Relative::class, User::class], version = 3)
abstract class AlzhimerDatabase : RoomDatabase() {
    abstract fun relativeDao(): RelativeDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AlzhimerDatabase? = null

        fun getDatabase(context: Context? = null): AlzhimerDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                        context!!.applicationContext,
                        AlzhimerDatabase::class.java,
                        "Alzhimer_database"
                )
                        .fallbackToDestructiveMigration()
                        .build()
                INSTANCE = instance
                instance
            }
        }
    }

//    private class WordDatabaseCallback(
//            private val scope: CoroutineScope
//    ) : RoomDatabase.Callback() {
//
//        override fun onOpen(db: SupportSQLiteDatabase) {
//            super.onOpen(db)
//            INSTANCE?.let { database ->
//                scope.launch(Dispatchers.IO) {
//                    populateDatabase(database.relativeDao())
//                }
//            }
//        }
//
//        suspend fun populateDatabase(relativeDao: RelativeDao) {
//            relativeDao.deleteAll()
//
//            val word = Relative(1, "Hello", "asss")
//            relativeDao.insert(word)
//        }
//    }
}

