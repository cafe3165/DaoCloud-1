package com.example.qd.clockIn

class ClockInItem constructor(clockDescribe:String,clockTime:String,clockState:String){
    var clockDescribe:String = ""
    var clockTime:String = ""
    var clockState:String = ""
    init {
        this.clockDescribe = clockDescribe
        this.clockState = clockState
        this.clockTime = clockTime
    }
}