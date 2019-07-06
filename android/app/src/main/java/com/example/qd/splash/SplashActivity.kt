package com.example.qd.splash

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.qd.R
import com.example.qd.logn.LognInActivity
import com.example.qd.mainPage.MainPage
import com.example.qd.netAccess.NetResponseListener
import com.example.qd.sharePreference.MyShareSave

class SplashActivity : AppCompatActivity() {

    var delayTime:Long = 2000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

    }

    override fun onResume() {
        super.onResume()
        var handler:Handler = Handler()
        handler.postDelayed(object:Runnable{
            override fun run() {
                checkLogInState()
            }

        },delayTime)

    }

    fun hasLogIn():Boolean{
        var myShareSave = MyShareSave(this.applicationContext)
        if(myShareSave.read(MainPage.LOG_IN_DATA)==null){
            return false
        }else return true
    }
    fun checkLogInState(){
        if(hasLogIn()){
            var intent = Intent(this,MainPage::class.java)
            startActivity(intent)
            finish()
        }else{
            var intent = Intent(this,LognInActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
