package com.example.afinal.ui.dashboard

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.afinal.AppDatabase
import com.example.afinal.Course
import com.example.afinal.R
import com.example.afinal.databinding.ActivityAddBinding
import kotlin.concurrent.thread

class AddActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var num:Int=0
        var day:Int=0
        var minWeek:Int=0
        var maxWeek:Int=0
        val arr = arrayOf(1,3,5,7,9)



        val  mItems=resources.getStringArray(R.array.spinnername)
        val  nItems =resources.getStringArray(R.array.spinnerday)
        val  lItems=resources.getStringArray(R.array.spinnerWeek)
        // Create an ArrayAdapter using a simple spinner layout and languages array
        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, mItems)
        val bb = ArrayAdapter(this, android.R.layout.simple_spinner_item, nItems)
        val cc = ArrayAdapter(this, android.R.layout.simple_spinner_item, lItems)
        // Set layout to use when the list of choices appear
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bb.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        cc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        //Set Adapter to Spinner
        binding.spinnerNum!!.adapter = aa
        binding.spinnerDay!!.adapter = bb
        binding.spinnerMaxWeek!!.adapter = cc
        binding.spinnerMinWeek!!.adapter = cc
        binding.spinnerNum.onItemSelectedListener= object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                num = position
            }

    }

        binding.spinnerDay.onItemSelectedListener= object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                day = position+1
            }

        }

        binding.spinnerMinWeek.onItemSelectedListener= object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                minWeek = position+1
            }

        }

        binding.spinnerMaxWeek.onItemSelectedListener= object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                maxWeek = position+1
            }

        }
        val courseDao = AppDatabase.getDatabase(this).courseDao()
        binding.login.setOnClickListener {

            val course = binding.course.text.toString()
            val room = binding.room.text.toString()
            val teacher = binding.teacher.text.toString()
            val a = arr[num]
            Log.d("button", "$course $room $teacher $num $a $day $minWeek $maxWeek")
            val addCourse = Course(a,day,course," ",room,teacher,"$minWeek - $maxWeek",minWeek,maxWeek,true)
            thread {
                courseDao.deleteCourse(a,day)
                courseDao.insertCourse(addCourse)
            }

            val intent = Intent(this,DashboardFragment::class.java)
            startActivity(intent)



        }
}}