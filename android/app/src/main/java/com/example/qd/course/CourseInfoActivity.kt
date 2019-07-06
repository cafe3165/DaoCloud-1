package com.example.qd.course

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.qd.R
import com.example.qd.clockIn.ClockIn
import com.example.qd.clockIn.ClockIn.Companion.Course_Info
import com.example.qd.dao.CourseInfo
import kotlinx.android.synthetic.main.activity_course_info.*
import kotlinx.android.synthetic.main.top_block.*

class CourseInfoActivity : AppCompatActivity() {
    lateinit var courseName:String
    lateinit var courseInfo:CourseInfo
    companion object{
        var COURSE_INFO:String = "COURSE_INFO"
        var CLASS_NUMBER:String = "CLASS_NUMBER"
        var COURSE_NAME:String = "COURSE_NAME"
        var TEACHER:String = "TEACHER"

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_info)
        fillDataToInterface(intent)
    }
    fun fillDataToInterface(intent:Intent){
        courseInfo = intent.getParcelableExtra(COURSE_INFO)
        topMiddleText.text = courseInfo.courseName
        topLeftImageView.setImageResource(0)
        topLeftTextView.text = ""
        topRightTextView.text = ""

        courseName = courseInfo.courseName
        courseNumberShow.text = courseInfo.courseId
        teacherShow.text = courseInfo.teacherName
        courseTimeShow.text = courseInfo.courseTime
        coursePlaceShow.text = courseInfo.coursePlace
    }
    fun qianDao(view:View){
        var intent:Intent = Intent(this,ClockIn::class.java)
        intent.putExtra(Course_Info,courseInfo)
        startActivity(intent)
    }
}
