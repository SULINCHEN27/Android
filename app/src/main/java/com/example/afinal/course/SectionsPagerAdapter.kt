package com.example.table

import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.afinal.R
import com.example.table.week.*


class SectionsPagerAdapter(private val context: Context, fm: FragmentManager)
    : FragmentPagerAdapter(fm) {
    //顶部tablayout的字符
    private val TAB_TITLES = arrayOf(
        R.string.tab_text_1,
        R.string.tab_text_2,
        R.string.tab_text_3,
        R.string.tab_text_4,
        R.string.tab_text_5,
        R.string.tab_text_6,
        R.string.tab_text_7,
        R.string.tab_text_8,
        R.string.tab_text_9,
        R.string.tab_text_10,
        R.string.tab_text_11,
        R.string.tab_text_12,
        R.string.tab_text_13,
        R.string.tab_text_14,
        R.string.tab_text_15,
        R.string.tab_text_16,
        R.string.tab_text_17,
        R.string.tab_text_18,
        R.string.tab_text_19,
        R.string.tab_text_20

    )

    override fun getItem(position: Int): Fragment {
        // 顶部有多少个tab，就会运行多少次（初次会加载第一第二，第三第四要点第三个tab才会运行）
        /*******新建Fragment******/
        Log.d("WY+","运行第几次："+(position+1))
        when (position) {
            0 -> {
                return Fragment1.newInstance(position + 1)
            }
            1 -> {
                return Fragment1.newInstance(position + 1)
            }
            2 -> {
                return Fragment1.newInstance(position + 1)
            }
            3 -> {
                return Fragment1.newInstance(position + 1)
            }
            4 -> {
                return Fragment1.newInstance(position + 1)
            }
            5 -> {
                return Fragment1.newInstance(position + 1)
            }
            6 -> {
                return Fragment1.newInstance(position + 1)
            }
            7 -> {
                return Fragment1.newInstance(position + 1)
            }
            8 -> {
                return Fragment1.newInstance(position + 1)
            }
            9 -> {
                return Fragment1.newInstance(position + 1)
            }
            10 -> {
                return Fragment1.newInstance(position + 1)
            }
            11 -> {
                return Fragment1.newInstance(position + 1)
            }
            12 -> {
                return Fragment1.newInstance(position + 1)
            }
            13 -> {
                return Fragment1.newInstance(position + 1)
            }
            14 -> {
                return Fragment1.newInstance(position + 1)
            }
            15 -> {
                return Fragment1.newInstance(position + 1)
            }
            16 -> {
                return Fragment1.newInstance(position + 1)
            }
            17 -> {
                return Fragment1.newInstance(position + 1)
            }
            18 -> {
                return Fragment1.newInstance(position + 1)
            }
            19 -> {
                return Fragment1.newInstance(position + 1)
            }

        }
        return Fragment1.newInstance(position + 1)

    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        // 顶部显示多少个页，不要超过TAB_TITLES的总数.
        return 20
    }
}
