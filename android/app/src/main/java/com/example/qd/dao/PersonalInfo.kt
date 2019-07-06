package com.example.qd.dao

import android.os.Parcel
import android.os.Parcelable

class PersonalInfo() :Parcelable{
    var userName = "default"
    var phoneNum = "default"
    var role = "default"
    var school = "default"
    var personalName = "default"
    var userId = 0

    constructor(parcel: Parcel) : this() {
        userName = parcel.readString()
        phoneNum = parcel.readString()
        role = parcel.readString()
        school = parcel.readString()
        personalName = parcel.readString()
        userId = parcel.readInt()
    }

    constructor(usrName:String,phoneNum:String,role:String,school:String,
                personalName:String,userId:Int):this(){
        this.userName = usrName
        this.phoneNum = phoneNum
        this.role = role
        this.school = school
        this.userId = userId
        this.personalName = personalName
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(userName)
        parcel.writeString(phoneNum)
        parcel.writeString(role)
        parcel.writeString(school)
        parcel.writeString(personalName)
        parcel.writeInt(userId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PersonalInfo> {
        override fun createFromParcel(parcel: Parcel): PersonalInfo {
            return PersonalInfo(parcel)
        }

        override fun newArray(size: Int): Array<PersonalInfo?> {
            return arrayOfNulls(size)
        }
    }
}