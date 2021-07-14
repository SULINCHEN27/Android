package com.example.afinal
import com.squareup.okhttp.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import java.io.InputStream

interface UserService {

    @POST("logincheck.asp")
    fun postUserData(@Body user:User):Call<List<Return>>
}