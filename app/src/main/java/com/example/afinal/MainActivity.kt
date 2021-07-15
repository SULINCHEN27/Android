package com.example.afinal

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.NetworkOnMainThreadException
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.afinal.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import okhttp3.*
import org.jsoup.Jsoup
import java.io.*
import java.lang.Exception
import kotlin.concurrent.thread
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

open class MainActivity : AppCompatActivity() {
    private var verifyCodeUrl:String = "https://jwcjwxt1.fzu.edu.cn/plus/verifycode.asp"
    private var postUrl:String = "https://jwcjwxt1.fzu.edu.cn/logincheck.asp"


    private var cookie01: String =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //协程获取验证码
        runBlocking {
            getVerifyCode()
        }


        val job = Job()
        val scope = CoroutineScope(job)


        binding.login.setOnClickListener {
            val muser :String = binding.accountEdit.text.toString()
            val passwd : String = binding.passwordEdit.text.toString()
            val verifyCode : String = binding.verityEdit.text.toString()

            scope.launch {

                    val response = post(muser,passwd,verifyCode,postUrl,cookie01)
                    val data = response.toString()
                    val data01 = data.substring(data.indexOf("url="),data.length-1)
                    val data02 = data.substring(data.indexOf("token="),data.length-1)

                    val url = data01.replace("url=","")
                    val str = data02.split("&").toTypedArray()
                    SSLogin(url,str)
            }
            job.cancel()



            val intent = Intent(this,MenuActivity::class.java)
            startActivity(intent)
            runBlocking {
                delay(1000)
                finish()
            }


        }

    }

    //第一步，获取验证码

    suspend fun getVerifyCode(){
        try {
            val response = request(verifyCodeUrl)
            showImage(response)
        }catch(e: Exception){
            Toast.makeText(this@MainActivity,"获取验证码失败",Toast.LENGTH_SHORT).show()
        }
    }

    suspend fun request(verifyCodeUrl:String):Bitmap{
        return suspendCoroutine {
            continuation ->
            HttpUtil.sendCookies(verifyCodeUrl, object :HttpCallbackListener{
                override fun onFinish(response: Bitmap,cookie:String) {
                    continuation.resume(response)
                    cookie01 = cookie
                }

                override fun onError(e: Exception) {
                    continuation.resumeWithException(e)
                }
            })
        }
    }

    private fun showImage(bitmap: Bitmap){
        Log.d("aaa","正在执行")
        runOnUiThread {
            val verityImage = findViewById<ImageView>(R.id.verityCodeImg)
            verityImage.setImageBitmap(bitmap)
        }
    }



    //第二步，验证提交的数据是否正确，拿到token和cookie
    suspend fun post(username: String,password: String, text:String,postUrl: String,cookie:String):Response{
        return suspendCoroutine {
            continuation ->
            OkHttpUtil.postUser(username,password, text,postUrl,cookie, object : Callback{
                override fun onFailure(call: Call, e: IOException) {
                    continuation.resumeWithException(e)
                }

                override fun onResponse(call: Call, response: Response) {
                    continuation.resume(response)
                }

            })
        }
    }

    //第三步
    private fun SSLogin(url:String,str: Array<String>) {
        thread {
            try {
                val client = OkHttpClient()
                val request = Request.Builder().url(url).build()
                val response = client.newCall(request).execute()
                val cookie02 : String? = response.header("Set-Cookie")
                if (cookie02 != null) {
                    sendSSLogin(cookie02,str)
                }

            }catch (e: NetworkOnMainThreadException) {
                e.printStackTrace()
            } finally {
            }
        }
    }

    //第四步
    private fun sendSSLogin(cookie02:String,str:Array<String>){
        thread {
            try {
                val client = OkHttpClient()
                val token = str[0].replace("token=","")
                val formBody = FormBody.Builder().add("token",token).build()
                val request = Request.Builder()
                        .addHeader("cookie",cookie02).url("https://jwcjwxt2.fzu.edu.cn/Sfrz/SSOLogin")
                        .post(formBody).build()
                val response = client.newCall(request).execute()
                val headers : Headers = response.headers
                val cookie03 = headers.values("Set-Cookie")
                val cookie =cookie02+";"+cookie03[0]+";"+cookie03[1]


               loginchk(cookie,str)

            }catch (e: NetworkOnMainThreadException) {
                e.printStackTrace()
            } finally {
            }
        }
    }

    //第五步
    private fun loginchk(cookie : String, str: Array<String>){
        thread {
            try {
                val client = OkHttpClient()
                val url = "https://jwcjwxt2.fzu.edu.cn/Home/index?"+str[2]+"&"+str[5]
                val request = Request.Builder().addHeader("cookie", cookie).url(url).build()
                val response = client.newCall(request).execute()

            }catch (e: NetworkOnMainThreadException) {
                e.printStackTrace()
            } finally {
            }
        }
    }


}




