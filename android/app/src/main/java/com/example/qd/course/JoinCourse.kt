package com.example.qd.course

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.view.View
import android.widget.Toast
import com.alibaba.fastjson.JSONObject
import com.example.qd.MyApplication
import com.example.qd.MyHandler
import com.example.qd.R
import com.example.qd.course.CourseInfoActivity.Companion.CLASS_NUMBER
import com.example.qd.course.CourseInfoActivity.Companion.COURSE_NAME
import com.example.qd.course.CourseInfoActivity.Companion.TEACHER
import com.example.qd.dao.CourseInfo
import com.example.qd.dao.PersonalInfo
import com.example.qd.netAccess.NetResponseListener
import kotlinx.android.synthetic.main.activity_join_course.*
import kotlinx.android.synthetic.main.top_block.*
import okhttp3.*
import java.io.IOException

class JoinCourse : AppCompatActivity(),View.OnClickListener , NetResponseListener<String> {

    var identifyUrl = "http://47.106.131.133:3000/sc"
    var myHandler: MyHandler = MyHandler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_course)
        topLeftImageView.setImageResource(R.drawable.left_arrow)
        topLeftTextView.text = "班课"
        topMiddleText.text = "加入班课"
        topRightTextView.text = "下一步"
        topRightTextView.setOnClickListener(object:View.OnClickListener{
            override fun onClick(v: View?) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                nextStep()
            }

        })

        topLeftImageView.setOnClickListener(this)
        topLeftTextView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        finish()
    }

    fun nextStep(){
        if (checkCourseNum()){

            fillDataToIntent(intent,createCourseNumber.text.toString())

        }
    }
    fun checkCourseNum():Boolean{
        var len:Int = createCourseNumber.text?.length?:0
        if (len<=0){
            return false
        }
        return true
    }
    fun fillDataToIntent(intent:Intent,courseNum:String){
        var myApplication:MyApplication = this.application as MyApplication
        var personInfo:PersonalInfo? = myApplication.getPersonalInfo()
        var cid = createCourseNumber.text.toString()
        if(personInfo==null){
            Log.i("LZH","不能获取用户信息")
            return
        }
        myHandler.setProcesser(this as NetResponseListener<Any>)


        var okHttpClient: OkHttpClient = OkHttpClient()
        val requestBody = FormBody.Builder().add("sid", personInfo!!.userId.toString()).add("cid", cid).build()
        var request: Request = Request.Builder().url(identifyUrl).method("POST",requestBody).build()
        var call: Call = okHttpClient.newCall(request)
        call.enqueue(object: Callback {
            override fun onFailure(call: Call?, e: IOException?) {

            }

            override fun onResponse(call: Call?, response: Response?) {
                var body:String = response!!.body().string()
                Log.i("LZH","code: "+response!!.code()+" body: "+body)
                var message: Message = myHandler.obtainMessage()
                message.obj = body
                myHandler.sendMessage(message)
            }

        })


    }
    override fun process(t: String) {
        var data: JSONObject = JSONObject.parseObject(t).getJSONObject("data")
        var courseInfo:CourseInfo = CourseInfo()
        courseInfo.courseId = data.getString("courseid")
        courseInfo.courseName = data.getString("course_name")
        courseInfo.courseTime = data.getString("time")
        courseInfo.coursePlace = data.getString("class")
        courseInfo.weekNum = data.getString("week")
        courseInfo.studentNum = data.getString("number").toInt()

        var myApplication:MyApplication = this.application as MyApplication
        myApplication.courseListChanged = true

        var intent:Intent = Intent(this,CourseInfoActivity::class.java)
        intent.putExtra(CourseInfoActivity.COURSE_INFO,courseInfo)
        this.startActivity(intent)
        finish()
    }
}
