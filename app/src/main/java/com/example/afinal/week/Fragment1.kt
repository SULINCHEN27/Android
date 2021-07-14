package com.example.table.week

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.example.afinal.AppDatabase
import com.example.afinal.MainActivity
import com.example.afinal.R
import com.example.afinal.course.CourseActivity
import com.example.table.PageViewModel
import kotlin.concurrent.thread


class Fragment1 : Fragment() {

    private lateinit var pageViewModel: PageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main, container, false)
        val num = arrayOf(1,3,5,7,9)
        val day = arrayOf(1,2,3,4,5,6,7)
        val title = root.findViewById<TextView>(R.id.title)

        val bundle = arguments
        val isWeek = bundle?.getInt("section_number")
        title.setText("第"+isWeek+"周")
        val arr =
            arrayOf(intArrayOf(R.id.a11,R.id.a12,R.id.a13,R.id.a14,R.id.a15,R.id.a16,R.id.a17),
                    intArrayOf(R.id.a31,R.id.a32,R.id.a33,R.id.a34,R.id.a35,R.id.a36,R.id.a37),
                    intArrayOf(R.id.a51,R.id.a52,R.id.a53,R.id.a54,R.id.a55,R.id.a56,R.id.a57),
                    intArrayOf(R.id.a71,R.id.a72,R.id.a73,R.id.a74,R.id.a75,R.id.a76,R.id.a77),
                    intArrayOf(R.id.a91,R.id.a92,R.id.a93,R.id.a94,R.id.a95,R.id.a96,R.id.a97))

        val courseDao = AppDatabase.getDatabase(activity as CourseActivity).courseDao()
        for (i in 0 until num.size){
            for (j in 0 until day.size){
                val int = arr[i][j]
                val text = root.findViewById<Button>(int)
                thread {
                    val course = courseDao.findCourse(num[i],day[j])
                    val isCourse = course.isCourse
                    if (isCourse){
                        if (isWeek != null) {
                            if(isWeek>=course.minWeek&&isWeek<=course.maxWeek)
                                text.visibility = View.VISIBLE
                        }
                        text.setText(course.name)
                        text.setOnClickListener {
                            val message = "教室："+course.room+"\n"+"教室："+course.teacher+"\n"+"周数："+course.week
                            Toast.makeText(activity,message,Toast.LENGTH_SHORT).show()

                        }
                    }
                }


            }
        }
        return root
    }

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * 返回给定节编号的此片段的新实例。
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): Fragment1 {
            return Fragment1().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}
