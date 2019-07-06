package com.example.qd.mainPage

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import com.alibaba.fastjson.JSONObject
import com.example.qd.MyApplication
import com.example.qd.MyHandler
import com.example.qd.R
import com.example.qd.course.CreateCourseActivity
import com.example.qd.course.JoinCourse
import com.example.qd.dao.PersonalInfo
import com.example.qd.myself.MySelfFragment
import com.example.qd.netAccess.NetResponseListener
import com.example.qd.setting.Setting
import com.example.qd.sharePreference.MyShareSave
import kotlinx.android.synthetic.main.class_fragment.*
import kotlinx.android.synthetic.main.main_page.*
import kotlinx.android.synthetic.main.top_block.*


class MainPage : AppCompatActivity() {

    companion object{
        var LOG_IN_DATA:String = "SignInData"
    }

    private lateinit var textMessage: TextView
    private lateinit var fm: FragmentManager
    private var chooseState = false
    private var myHandler:MyHandler = MyHandler()
    private lateinit var classFragment:ClassFragment
    private lateinit var mySelfFragment: MySelfFragment

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                changeTopViewState(1)
//                textMessage.setText(R.string.title_home)
                return@OnNavigationItemSelectedListener true
            }

            R.id.navigation_notifications -> {
                changeTopViewState(3)
//                textMessage.setText(R.string.title_notifications)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_page)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        fm = supportFragmentManager

        initView()

        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    fun initData(){
        classFragment = ClassFragment()
        mySelfFragment = MySelfFragment()

        var myShareSave:MyShareSave = MyShareSave(this!!.applicationContext)
        if(myShareSave.read(MainPage.LOG_IN_DATA)==null){
            return
        }
        var info = JSONObject.parseObject(myShareSave.read(MainPage.LOG_IN_DATA))
        var personInfo = transform2PersonInf(info)
        var myApplication:MyApplication = this.application as MyApplication
        myApplication.setPersonalInfo(personInfo)
    }
    fun initView(){
        initData()

        changeTopViewState(1)

    }
    fun transform2PersonInf(info:JSONObject): PersonalInfo {
        var personalInfo: PersonalInfo = PersonalInfo(info.getString("account"),
            info.getString("email"),info.getIntValue("role").toString(),
            info.getString("school"),info.getString("name"),info.getIntValue("ref"))
        return personalInfo
    }
    fun changeTopViewState(flag:Int){
        var ft:FragmentTransaction = fm.beginTransaction()
        when(flag){
            1->{
                topLeftImageView.setImageResource(0)
                topLeftTextView.text = ""
                topMiddleText.text = "班课"
                topRightTextView.text = "编辑"
                classFragment.setHandler(myHandler)
                hideSelectItem()
                floatCompoents.visibility = View.VISIBLE
                ft.replace(R.id.fragment_content,classFragment)
            }
            3->{
                topLeftImageView.setImageResource(0)
                topLeftTextView.text = ""
                topRightTextView.text = ""
                topMiddleText.text = "我的"
                floatCompoents.visibility = View.GONE
                ft.replace(R.id.fragment_content,mySelfFragment)
            }
        }
        ft.commit()
    }

    fun goSetting(view:View){
        var intent = Intent(this, Setting::class.java)
        startActivity(intent)
    }
    fun chooseItem(view:View){
        Log.i("LZH","chooseState: "+chooseState)
        if (!chooseState){
            var animator1 = AnimationUtils.loadAnimation(this,R.anim.add_anim)
            var animator2 = AnimationUtils.loadAnimation(this,R.anim.add_anim)
            var animator3 = AnimationUtils.loadAnimation(this,R.anim.rotate_anim1)
            selectItems.visibility = View.VISIBLE
//            select1.startAnimation(animator1)
//            select2.startAnimation(animator2)
            animator3.setAnimationListener(object: Animation.AnimationListener {
                override fun onAnimationRepeat(animation: Animation?) {
//                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onAnimationEnd(animation: Animation?) {
//                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    floatButton.setImageResource(R.drawable.clear)
                }

                override fun onAnimationStart(animation: Animation?) {
//                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

            })
            floatButton.startAnimation(animator3)

        }else{
            closeChooseState()
        }
        chooseState=!chooseState
    }
    fun closeChooseState(){
        var animator4 = AnimationUtils.loadAnimation(this,R.anim.rotate_anim2)
        animator4.setAnimationListener(object: Animation.AnimationListener{
            override fun onAnimationRepeat(animation: Animation?) {
//                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onAnimationEnd(animation: Animation?) {
                floatButton.setImageResource(R.drawable.add)
                Log.i("LZH","animator end")
            }

            override fun onAnimationStart(animation: Animation?) {
//                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
        floatButton.startAnimation(animator4)
        hideSelectItem()
    }
    fun createCourse(view:View){
        var intent: Intent = Intent(this, CreateCourseActivity::class.java)
        startActivity(intent)
    }
    fun joinCourse(view:View){
        var intent: Intent = Intent(this, JoinCourse::class.java)
        startActivity(intent)
    }
    fun hideSelectItem(){
        selectItems.visibility = View.GONE
    }


}
