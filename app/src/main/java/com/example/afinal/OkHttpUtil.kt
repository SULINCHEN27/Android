package com.example.afinal

import okhttp3.*

object OkHttpUtil {
    fun postUser(
        username: String,
        password: String, text:String,
        postUrl: String,
        cookie:String,
        callback: Callback
    ){

                val client = OkHttpClient()
                val formBody = FormBody.Builder().add("muser",username)
                    .add("passwd",password).add("verifyCode",text)
                    .build()
                val request = Request.Builder().url(postUrl)
                    .addHeader("cookie",cookie).post(formBody).build()

        client.newCall(request).enqueue(callback)



    }
}


