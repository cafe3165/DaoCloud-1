package com.example.qd.logn

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import com.example.qd.MyHandler
import com.example.qd.R
import com.example.qd.clockIn.ClockIn
import com.example.qd.mainPage.MainPage
import com.example.qd.netAccess.NetResponseListener
import kotlinx.android.synthetic.main.sign_in.*
import okhttp3.*
import java.io.IOException
import okhttp3.FormBody
import okhttp3.RequestBody
import okhttp3.OkHttpClient
import android.os.Build
import android.os.StrictMode
import android.Manifest.permission
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.support.v4.app.ActivityCompat
import android.content.pm.PackageManager
import com.example.qd.MyApplication
import com.example.qd.dao.PersonalInfo
import com.example.qd.mainPage.MainPage.Companion.LOG_IN_DATA
import com.example.qd.sharePreference.MyShareSave


class LognInActivity: AppCompatActivity() ,NetResponseListener<String>{
    var identifyUrl = "http://47.106.131.133:3000/loginmanager"
    var myHandler:MyHandler = MyHandler()
    var successState = 1

    var testData:JSONObject = JSONObject.parseObject("{\"ref\":1,\"role\":1,\"success\":1}")
    var testPhone:String = "15712341234"
    var pw:String = "qwer123456"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_in);

        myHandler.setProcesser(this as NetResponseListener<Any>)


    }
    fun signInFinish(view:View){
        checkIdentification(userPhone.text.toString(),userPW.text.toString())
//        test()

    }
    fun GoToSignUp(view:View){
        var intent: Intent = Intent(this, LognUpActivity::class.java)
        startActivity(intent)
        finish()
    }
    fun checkIdentification(user:String,password:String){
        if(user.equals(testPhone)&&password.equals(pw)){
            transform(testData)
            return
        }
        var okHttpClient:OkHttpClient = OkHttpClient()
        val requestBody = FormBody.Builder().add("account", user).add("password", password).build()
        var request:Request = Request.Builder().url(identifyUrl).method("POST",requestBody).build()
        var call:Call = okHttpClient.newCall(request)
        call.enqueue(object:Callback{
            override fun onFailure(call: Call?, e: IOException?) {

            }

            override fun onResponse(call: Call?, response: Response?) {
                var body:String = response!!.body().string()
                Log.i("LZH","code: "+response!!.code()+" body: "+body)
                var message:Message = myHandler.obtainMessage()
                message.obj = body
                myHandler.sendMessage(message)
            }

        })
    }
    override fun process(t: String) {
        var json:JSONObject = JSONObject.parseObject(t)
        var data:JSONObject = json.get("data") as JSONObject
        var success:Int = data.getString("success").toInt()
        Log.i("LZH","successState "+success)
        if(success == successState){
            transform(data)
        }else{
            Toast.makeText(this,"用户名或密码错误",Toast.LENGTH_LONG).show()
        }

    }
    fun transform(data:JSONObject){
        var intent: Intent = Intent(this, MainPage::class.java)
        var myShareSave:MyShareSave = MyShareSave(this.applicationContext)
        var info:JSONObject = data.getJSONObject("info")
        myShareSave.write(LOG_IN_DATA,info.toJSONString())

        var personalInfo = transform2PersonInf(info)
        var myApplication:MyApplication = this.application as MyApplication
        myApplication.setPersonalInfo(personalInfo)

        startActivity(intent)
        finish()
    }
    fun transform2PersonInf(info:JSONObject):PersonalInfo{
        var personalInfo:PersonalInfo = PersonalInfo(info.getString("account"),
            info.getString("email"),info.getIntValue("role").toString(),
            info.getString("school"),info.getString("name"),info.getIntValue("ref"))
        return personalInfo
    }

}