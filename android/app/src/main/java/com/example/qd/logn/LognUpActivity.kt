package com.example.qd.logn

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Message
import android.support.design.widget.TextInputLayout
import android.support.v4.app.NotificationCompat
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import com.alibaba.fastjson.JSONObject
import com.example.qd.MyApplication
import com.example.qd.MyHandler
import com.example.qd.R
import com.example.qd.clockIn.ClockIn
import com.example.qd.dao.PersonalInfo
import com.example.qd.mainPage.MainPage
import com.example.qd.netAccess.NetResponseListener
import com.example.qd.sharePreference.MyShareSave
import kotlinx.android.synthetic.main.sign_up.*
import okhttp3.*
import java.io.IOException
import java.util.regex.Pattern
import kotlin.random.Random

class LognUpActivity: AppCompatActivity() , NetResponseListener<String> {
    //    var nameInputView:TextInputLayout?=null
    var identifyUrl = "http://47.106.131.133:3000/user"
    var maxTextLen:Int = 6
    var phoneText:String? = null
    var myHandler = MyHandler()
    var successState = 200
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up)

        setListener()
        myHandler.setProcesser(this as NetResponseListener<Any>)

    }
    fun setListener(){
        signUpNameTextInput?.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                if(count>maxTextLen){
                    signUpNameInput.error = "不能大于6个字"
                    signUpNameInput.isErrorEnabled = true
                }else{
                    signUpNameInput.isErrorEnabled = false
                }
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })


        signUpNamePhoneTextInput?.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(s: Editable?) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                phoneText= s?.toString()?:""
                signUpNamePhoneInput.isErrorEnabled = false
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
        signUpNamePhoneTextInput?.setOnFocusChangeListener(object :View.OnFocusChangeListener{
            override fun onFocusChange(v: View?, hasFocus: Boolean) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                var len:Int= phoneText?.length?:0
                if(len>0){
                    val phone:String = phoneText?:""
                    if(!isPhone(phone)){
                        signUpNamePhoneInput.error = "手机号错误"
                        signUpNamePhoneInput.isErrorEnabled = true
                    }
                }
            }

        })


    }

    fun isPhone(phone: String): Boolean {
        val regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$"
        if (phone.length != 11) {
            return false
        } else {
            val p = Pattern.compile(regex)
            val m = p.matcher(phone)
            val isMatch = m.matches()
            return isMatch
        }
    }
    fun signUpFinish(view:View){
        var intent:Intent = Intent(this,MainPage::class.java)
        var userName = signUpNameTextInput.text.toString()
        var phone = signUpNamePhoneTextInput.text.toString()
        var pw = password.text.toString()

        var okHttpClient: OkHttpClient = OkHttpClient()
        val requestBody = FormBody.Builder().add("account", userName).add("password",pw).
            add("email", phone).add("role","2").
            add("school","福州大学").
            add("name",userName).build()
        var request: Request = Request.Builder().url(identifyUrl).method("POST",requestBody).build()
        var call: Call = okHttpClient.newCall(request)
        call.enqueue(object: Callback {
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
        startActivity(intent)
    }
    fun getCode(view:View){
        var time:Int = 4
        var identification:String = ""
        while(time>0){
            identification+=""+ Random.nextInt(9)
            time--
        }

        Toast.makeText(this,identification,Toast.LENGTH_LONG).show()
    }

    override fun process(t: String) {
        var json:JSONObject = JSONObject.parseObject(t)
        var data:JSONObject = json.get("info") as JSONObject
        transform(data)
    }
    fun transform(data: JSONObject){
        var intent: Intent = Intent(this, MainPage::class.java)
        var myShareSave: MyShareSave = MyShareSave(this.applicationContext)
        var info: JSONObject = data
        myShareSave.write(MainPage.LOG_IN_DATA,info.toJSONString())

        var personalInfo = transform2PersonInf(info)
        var myApplication: MyApplication = this.application as MyApplication
        myApplication.setPersonalInfo(personalInfo)

        startActivity(intent)
        finish()
    }
    fun transform2PersonInf(info: JSONObject): PersonalInfo {
        var personalInfo: PersonalInfo = PersonalInfo(info.getString("account"),
            info.getString("email"),info.getIntValue("role").toString(),
            info.getString("school"),info.getString("name"),info.getIntValue("ref"))
        return personalInfo
    }
}