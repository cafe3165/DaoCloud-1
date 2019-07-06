package com.example.qd

import android.app.Application
import com.example.qd.dao.PersonalInfo

class MyApplication: Application() {
    private var personalInfo:PersonalInfo? = null
    private var courseInfo: String? = null
    private var courseList:String? = null
    var courseListChanged:Boolean = false
    override fun onCreate() {
        super.onCreate()

    }
    fun setPersonalInfo(info:PersonalInfo){
        personalInfo = info
    }
    fun getPersonalInfo():PersonalInfo?{
        return personalInfo
    }
    fun setCourseInfo(info:String){
        courseInfo = info
    }
    fun getCourseInfo():String? {
        return courseInfo
    }

    fun setCourseList(info:String){
        courseList = info
    }
    fun getCourseList():String? {
        return courseList
    }

}