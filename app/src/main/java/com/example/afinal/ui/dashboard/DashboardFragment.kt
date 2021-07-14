package com.example.afinal.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.example.afinal.R
import com.example.afinal.course.CourseActivity
import com.example.table.SectionsPagerAdapter
import com.google.android.material.tabs.TabLayout

class DashboardFragment : Fragment() {

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {

    val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
    val check = root.findViewById<Button>(R.id.btnText)
    check.setOnClickListener {
      val intent = Intent(activity,CourseActivity::class.java)
      startActivity(intent)

    }

//    val add = root.findViewById<Button>(R.id.add)
//    add.setOnClickListener {
//      val intent = Intent(activity,AddActivity::class.java)
//      startActivity(intent)
//    }


    return root
  }


}