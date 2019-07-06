package com.example.qd.course

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.qd.R
import com.example.qd.mainPage.MainPage
import kotlinx.android.synthetic.main.activity_create_course_state.*
import kotlinx.android.synthetic.main.top_block.*
import kotlin.random.Random

class CreateCourseState : AppCompatActivity(),View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_course_state)
        initTopBlock()

        topLeftImageView.setOnClickListener(this)
        topLeftTextView.setOnClickListener(this)

        generateRandomCourseID()
    }
    fun initTopBlock(){
        topLeftImageView.background = resources.getDrawable(R.drawable.left_arrow)
        topLeftTextView.text = "班课"
        topRightTextView.text = ""
        topMiddleText.text = "创建成功"
    }
    fun generateRandomCourseID(){
        var text = ""
        var time = 6;
        while(time>0){
            time--
            text+= Random.nextInt(9)
        }
        courseId.text = text
    }
    override fun onClick(v: View?) {
        var intent: Intent = Intent(this,MainPage::class.java)
        startActivity(intent)
        finish()
    }

}
