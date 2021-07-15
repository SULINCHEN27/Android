package com.example.afinal.course

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.example.afinal.AppDatabase
import com.example.afinal.Course
import com.example.afinal.MainActivity
import com.example.afinal.R
import com.example.table.SectionsPagerAdapter
import com.google.android.material.tabs.TabLayout
import org.jsoup.Jsoup
import java.io.File
import kotlin.concurrent.thread

class CourseActivity : MainActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course)
        val courseDao = AppDatabase.getDatabase(this).courseDao()


        val file = File("/data/data/com.example.afinal/files/table.html")
        val doc = Jsoup.parse(file,"UTF-8")
        val temp: String = doc.html().replace("<br>", "$$$$$")
        val document = Jsoup.parse(temp);
        val element = document.getElementById("ContentPlaceHolder1_LB_kb")
        val table = element.select("tr")
        val e = table.size

        for(i in 0 until e){
            val tr = table.get(i)
            val fonts = tr.select("td")

            var num:Int=0;
            var day:Int=0;
            for (j in 0 until fonts.size){
                val font = fonts.get(j)

                val spilt = font.text().split("$$$$$")

                if (spilt.size==2){
                    num = spilt[0].toInt()
                }
                if(spilt.size>3||spilt.toString() ==("[]")) day++
                if(spilt.size==5){
                    val splitWeek = spilt[4].split("-")
                    val course: Course = Course(num,day,spilt[0],spilt[1],spilt[2],spilt[3],spilt[4],splitWeek[0].toInt(),splitWeek[1].toInt(),true);
                    thread {
                        courseDao.insertCourse(course)
                    }


                }else{
                    val course:Course = Course(num,day,"","","","","",0,0,false)
                    thread {
                        courseDao.insertCourse(course)
                    }

                }

            }
        }
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
    }
}