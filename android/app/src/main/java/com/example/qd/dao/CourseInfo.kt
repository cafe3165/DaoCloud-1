package com.example.qd.dao

import android.os.Parcel
import android.os.Parcelable
import java.util.*

class CourseInfo() :Parcelable{
    var classNumber:String = "default"
    var courseName:String = "default"
    var courseTime:String = "default"
    var coursePlace:String = "default"
    var courseId:String = "default"
    var teacherName:String = "default"
    var weekNum:String = "default"
    var studentNum:Int = 0
    var courseImage:Any = "default"

    constructor(parcel: Parcel) : this() {
        classNumber = parcel.readString()
        courseName = parcel.readString()
        courseTime = parcel.readString()
        coursePlace = parcel.readString()
        courseId = parcel.readString()
        teacherName = parcel.readString()
        weekNum = parcel.readString()
        studentNum = parcel.readInt()
    }


    constructor(cNumbler:String ,cName:String,t:String):this(){
        classNumber = cNumbler
        courseName = cName
        teacherName = t;
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(classNumber)
        parcel.writeString(courseName)
        parcel.writeString(courseTime)
        parcel.writeString(coursePlace)
        parcel.writeString(courseId)
        parcel.writeString(teacherName)
        parcel.writeString(weekNum)
        parcel.writeInt(studentNum)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CourseInfo> {
        override fun createFromParcel(parcel: Parcel): CourseInfo {
            return CourseInfo(parcel)
        }

        override fun newArray(size: Int): Array<CourseInfo?> {
            return arrayOfNulls(size)
        }
    }


}