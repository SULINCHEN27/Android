package com.example.afinal

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CourseDao {

    @Insert
    fun insertCourse(course: Course):Long

    @Query("select * from Course where num = :num and day = :day")
    fun findCourse(num:Int, day:Int):Course
}