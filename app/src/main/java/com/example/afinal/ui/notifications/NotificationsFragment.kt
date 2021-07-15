package com.example.afinal.ui.notifications

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.afinal.R
import com.example.myapplication.Program
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import com.youth.banner.loader.ImageLoader

//工具箱
class NotificationsFragment : Fragment() {
  private val programList = ArrayList<Program>()
  override fun onCreateView(

    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {


    val root = inflater.inflate(R.layout.fragment_notifications, container, false)
    val banner = root.findViewById<Banner>(R.id.banner)

    val imageUrl = arrayListOf<Int>(
      R.drawable.one, R.drawable.two,
      R.drawable.three, R.drawable.four
    )
    val imageTitle = arrayListOf<String>("one", "two", "three", "four")
    //设置内置样式
    banner.setBannerStyle(1)
    //设置图片加载器
    banner.setImageLoader(MyLoader())
    //设置图片集合
    banner.setImages(imageUrl)
    //设置轮播图的标题集合
    banner.setBannerTitles(imageTitle)
    //轮播间隔时间
    banner.setDelayTime(2000)
    //设置是否为自动轮播
    banner.isAutoPlay(true)
    //设置指示器的位置
    banner.setIndicatorGravity(BannerConfig.CENTER)
    var i = 0;
    //设置点击事件
    banner.setOnBannerListener {
      i++;
      banner.isAutoPlay(false)

    }
    //必须最后调用方法，启动轮播图
    banner.start()
    return root
  }

  private class MyLoader : ImageLoader() {
    override fun displayImage(context: Context, path: Any, imageView: ImageView) {
      Glide.with(context).load(path as Int).into(imageView)
    }
  }
}