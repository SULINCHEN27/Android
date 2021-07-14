package com.example.afinal

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.NetworkOnMainThreadException
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.afinal.databinding.ActivityMainBinding
import okhttp3.*
import org.jsoup.Jsoup
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

open class MainActivity : AppCompatActivity() {
    private var verifyCodeUrl:String = "https://jwcjwxt1.fzu.edu.cn/plus/verifycode.asp"


    private var cookie01: String =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //sendCookies(verifyCodeUrl)



        binding.login.setOnClickListener {
//            val muser :String = binding.accountEdit.text.toString()
//            val passwd : String = binding.passwordEdit.text.toString()
//            val verifyCode : String = binding.verityEdit.text.toString()
//            Log.d("用户信息","muser:"+muser+" password:"+passwd+" verifyCode:"+verifyCode)
//            postUser(muser,passwd,verifyCode)
            val file = File("/data/data/com.example.afinal/files/table.html")
            val doc = Jsoup.parse(file,"UTF-8")
            val temp: String = doc.html().replace("<br>", "$$$$$")
            val document = Jsoup.parse(temp);
            val element = document.getElementById("ContentPlaceHolder1_LB_kb")
            val table = element.select("tr")
            val e = table.size

            val courseDao = AppDatabase.getDatabase(this).courseDao()
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
                        val course:Course= Course(num,day,spilt[0],spilt[1],spilt[2],spilt[3],spilt[4],splitWeek[0].toInt(),splitWeek[1].toInt(),true);
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

            val intent = Intent(this,MenuActivity::class.java)
            startActivity(intent)
            finish()

        }

    }

    //第一步，获取验证码
    private fun sendCookies(imageURL: String){
        Log.d("sendCookies","方法正在执行")
        thread {
            var connection: HttpURLConnection? = null
            try {
                var img = imageURL
                val url = URL(img)
                connection = url.openConnection() as HttpURLConnection
                connection.connectTimeout = 800000
                connection.readTimeout = 800000
                if(connection.getHeaderField("Set-Cookie")!=null){
                    cookie01 = connection.getHeaderField("Set-Cookie")
                    Log.d("Set-Cookie", cookie01)
                }

                val input = connection.inputStream
                val readera : ByteArray = readStream(input)
                val bitmap= BitmapFactory.decodeByteArray(readera, 0, readera.size);
                showImage(bitmap)
            } catch (e: NetworkOnMainThreadException) {
                e.printStackTrace()
            } finally {
                connection?.disconnect()
            }
        }
    }


    private fun showImage(bitmap: Bitmap){
        runOnUiThread {
            val verityImage = findViewById<ImageView>(R.id.verityCodeImg)
            verityImage.setImageBitmap(bitmap)
        }
    }

    private fun readStream(inStream : InputStream): ByteArray {
        val buffer  = ByteArray(2048)
        var len :Int = -1;
        val outStream = ByteArrayOutputStream()
        len = inStream.read(buffer)
        while (len  != -1) {
            outStream.write(buffer, 0, len);
            len = inStream.read(buffer)
        }
        val data : ByteArray = outStream.toByteArray();
        outStream.close();
        inStream.close();
        return data;

    }


    //第二步，验证提交的数据是否正确，拿到token和cookie
    private fun postUser(username: String,password: String, text:String){
        thread {
            try {
                val client = OkHttpClient()
                val formBody = FormBody.Builder().add("muser","831903207")
                    .add("passwd","csl20001227").add("verifyCode",text)
                    .build()
                val request = Request.Builder().url("https://jwcjwxt1.fzu.edu.cn/logincheck.asp")
                    .addHeader("cookie",cookie01).post(formBody).build()
                val response = client.newCall(request).execute()
                val data = response.toString()
                val data01 = data.substring(data.indexOf("url="),data.length-1)
                val data02 = data.substring(data.indexOf("token="),data.length-1)

                val url = data01.replace("url=","")
                val str = data02.split("&").toTypedArray()
                SSLogin(url,str)

            }catch (e: NetworkOnMainThreadException) {
                e.printStackTrace()
            } finally {
            }
        }

    }

    private fun showResponse(response: String){
        runOnUiThread {
            Log.d("response",response)
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
                Log.d("第三步cookie",cookie02.toString())
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

                Log.d("第四步cookie",cookie02+";"+cookie03[0]+";"+cookie03[1])
                val cookie =cookie02+";"+cookie03[0]+";"+cookie03[1]


               loginchk(cookie,str)



            }catch (e: NetworkOnMainThreadException) {
                e.printStackTrace()
            } finally {
            }
        }
    }

    private fun loginchk(cookie : String, str: Array<String>){
        thread {
            try {
                val client = OkHttpClient()
                val url = "https://jwcjwxt2.fzu.edu.cn/Home/index?"+str[2]+"&"+str[5]
                val request = Request.Builder().addHeader("cookie", cookie).url(url).build()
                val response = client.newCall(request).execute()





//                val document = Jsoup.parse(file,"UTF-8")
//                Log.d("document",document.text())
            }catch (e: NetworkOnMainThreadException) {
                e.printStackTrace()
            } finally {
            }
        }
    }


}




