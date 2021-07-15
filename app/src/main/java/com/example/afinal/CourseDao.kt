package com.example.afinal

import androidx.room.*

@Dao
interface CourseDao {

    @Insert
    fun insertCourse(course: Course):Long

    @Query("select * from Course where num = :num and day = :day")
    fun findCourse(num:Int, day:Int):Course

    @Query("delete from Course where num = :num and day = :day")
    fun deleteCourse(num:Int, day:Int)
}