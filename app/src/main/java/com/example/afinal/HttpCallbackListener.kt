package com.example.afinal

import android.graphics.Bitmap
import java.lang.Exception

interface HttpCallbackListener {
    fun onFinish(response:Bitmap,cookie:String)
    fun onError(e: Exception)

}