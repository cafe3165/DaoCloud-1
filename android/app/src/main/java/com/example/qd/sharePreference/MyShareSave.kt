package com.example.qd.sharePreference

import android.content.Context
import android.content.SharedPreferences

class MyShareSave(context: Context) {
    companion object{
        lateinit var sharePreference:SharedPreferences
    }
    init {
        sharePreference = context.getSharedPreferences("QD",Context.MODE_PRIVATE)
    }
    fun write(key:String,value:String?){
        var editor:SharedPreferences.Editor = sharePreference.edit()
        editor.putString(key, value)
        editor.commit()
    }
    fun read(key:String):String?{
        return sharePreference.getString(key,null)
    }
}