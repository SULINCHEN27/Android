package com.example.afinal

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.lang.Thread.sleep
import kotlin.concurrent.thread


class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        thread {
            sleep(1000)
            val it = Intent(applicationContext, MainActivity::class.java)
            startActivity(it)
            finish();
        }



    }


}