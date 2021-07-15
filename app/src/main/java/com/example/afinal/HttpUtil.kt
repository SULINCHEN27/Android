package com.example.afinal

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.NetworkOnMainThreadException
import android.util.Log
import android.widget.ImageView
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

object HttpUtil{
     fun sendCookies(imageURL: String, listener: HttpCallbackListener) {
         thread {
             var connection: HttpURLConnection? = null
             var cookie01 = ""
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
                 listener.onFinish(bitmap,cookie01)
             } catch (e: NetworkOnMainThreadException) {
                 e.printStackTrace()
                 listener.onError(e)
             } finally {
                 connection?.disconnect()
             }
         }


    }

    fun readStream(inStream : InputStream): ByteArray {
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



}
