package com.example.qd.setting

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.qd.R
import kotlinx.android.synthetic.main.activity_aboutus.*
import kotlinx.android.synthetic.main.top_block.*

class Aboutus : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aboutus)
        initTopBlock()
        initData()
    }
    fun initTopBlock(){
        topLeftImageView.setImageResource(R.drawable.left_arrow)
        topLeftTextView.text = ""
        topRightTextView.text = "关于我们"
        topMiddleText.text = ""

        topLeftImageView.setOnClickListener(object:View.OnClickListener{
            override fun onClick(v: View?) {
                this@Aboutus.finish()
            }

        })
    }
    fun initData(){
        var text="1、项目名称：到云\n"
        text+="2、团队成员：黄志豪，黄志明，黄旭昇，黄辉昌，陈碧琼\n"
        textView.text = text
    }
}
