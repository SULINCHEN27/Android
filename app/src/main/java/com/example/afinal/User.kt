package com.example.afinal

import androidx.room.Entity
import androidx.room.PrimaryKey

class User(val muser:String, val passwd:String, val verifyCode:String)

class Return(val protocol:String, val code:String,val message:String, val url :String)

