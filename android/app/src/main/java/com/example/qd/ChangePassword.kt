package com.example.qd

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.example.qd.mainPage.MainPage
import kotlinx.android.synthetic.main.top_block.*
import kotlin.random.Random

class ChangePassword : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.change_password);
        initTopBlock()
    }
    fun finishPassword(view :View){
        var intent:Intent = Intent(this, MainPage::class.java)
        startActivity(intent)
        finish()
    }
    fun showIdentificationId(view: View){
        var time:Int = 4
        var identification:String = "";
        while(time>0){
            identification+=""+Random.nextInt(9)
            time--
        }
        Toast.makeText(this,identification,Toast.LENGTH_LONG)
    }
    fun initTopBlock(){
        topLeftImageView.setImageResource(R.drawable.left_arrow)
        topLeftTextView.text = ""
        topRightTextView.text = ""
        topMiddleText.text = "修改密码"

        topLeftImageView.setOnClickListener(object:View.OnClickListener{
            override fun onClick(v: View?) {
                this@ChangePassword.finish()
            }

        })
    }
}