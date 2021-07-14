package com.example.afinal

import android.text.Editable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Course(
    var num:Int, var day:Int, var name: String,
    var text:String, var room: String, var teacher: String,
    var week:String, var minWeek:Int, var maxWeek:Int, var isCourse: Boolean){
    @PrimaryKey(autoGenerate = true)
    var id : Long=0
}