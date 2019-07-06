package com.example.qd.setting

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.qd.ChangePassword
import com.example.qd.R
import com.example.qd.logn.LognInActivity
import kotlinx.android.synthetic.main.top_block.*
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import com.example.qd.mainPage.MainPage
import com.example.qd.sharePreference.MyShareSave


class Setting : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        initTopBlock()
    }

    fun initTopBlock(){
        topLeftImageView.setImageResource(R.drawable.left_arrow)
        topLeftTextView.text = ""
        topRightTextView.text = ""
        topMiddleText.text = "设置"

        topLeftImageView.setOnClickListener(object:View.OnClickListener{
            override fun onClick(v: View?) {
                finish()
            }

        })
    }
    fun changepw(view:View){
        var intent:Intent = Intent(this,ChangePassword::class.java)
        startActivity(intent)
    }
    fun aboutUs(view:View){
        var intent:Intent = Intent(this,Aboutus::class.java)
        startActivity(intent)
    }
    fun logOut(view:View){
        var myShareSave = MyShareSave(this.applicationContext)
        myShareSave.write(MainPage.LOG_IN_DATA,null)
        var intent:Intent = Intent(this,LognInActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()

    }
}
