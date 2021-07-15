package com.example.afinal

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.afinal.course.CourseActivity
import com.example.afinal.ui.dashboard.AddActivity
import com.example.afinal.ui.dashboard.DashboardFragment

@Database(version = 1,entities = [Course::class])
abstract class AppDatabase :RoomDatabase() {
    abstract fun courseDao(): CourseDao

    companion object{
        private var instance : AppDatabase? = null

        @Synchronized
        fun getDatabase(context: MainActivity):AppDatabase{
            instance?.let {
                return it
            }

            return Room.databaseBuilder(context.applicationContext,
            AppDatabase::class.java,"app_database")
                .build().apply {
                    instance = this
                }
        }

        @Synchronized
        fun getDatabase(context: CourseActivity):AppDatabase{
            instance?.let {
                return it
            }

            return Room.databaseBuilder(context.applicationContext,
                AppDatabase::class.java,"app_database")
                .build().apply {
                    instance = this
                }
        }

        @Synchronized
        fun getDatabase(context: AddActivity):AppDatabase{
            instance?.let {
                return it
            }

            return Room.databaseBuilder(context.applicationContext,
                AppDatabase::class.java,"app_database")
                .build().apply {
                    instance = this
                }
        }


    }
}