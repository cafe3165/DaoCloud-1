package com.example.qd.mainPage

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Message
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import com.example.qd.MyApplication
import com.example.qd.MyHandler

import com.example.qd.R
import com.example.qd.dao.CourseInfo
import com.example.qd.dao.PersonalInfo
import com.example.qd.netAccess.NetResponseListener
import com.example.qd.sharePreference.MyShareSave
import kotlinx.android.synthetic.main.class_fragment.*
import kotlinx.android.synthetic.main.main_page.*
import okhttp3.*
import java.io.IOException

class ClassFragment : Fragment() ,NetResponseListener<String>{

    companion object {
        fun newInstance() = ClassFragment()
    }
    private var identifyUrl = "http://47.106.131.133:3000/studentcourse"
    private lateinit var adapter:ClassAdapter
    private var chooseState = false
    private lateinit var viewModel: ClassModel
    private lateinit var myHandler:MyHandler
    private lateinit var data:JSONObject
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.class_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ClassModel::class.java)

        initDataView()

    }

    fun initDataView(){
//        fillDataToViewModel(viewModel)

        adapter = ClassAdapter(this.context!!,viewModel.classInfos)

        classItemList.layoutManager = LinearLayoutManager(this.context)
        classItemList.adapter =adapter
//        classItemList.addItemDecoration(RecycleViewDivider(this.context!!,LinearLayoutManager.HORIZONTAL))

        updateData()
    }
    fun fillDataToViewModel(viewModel:ClassModel){
        viewModel.addClassInfo("1班","工程实践1","池老标")
        viewModel.addClassInfo("2班","工程实践2","池老标")
    }
    fun loadData(personinfo: PersonalInfo){
        if(loadLocalCourseList()){
            return
        }
        var ref = personinfo.userId
        var okHttpClient: OkHttpClient = OkHttpClient()
        var url = identifyUrl+"/"+ref.toString()
        var request: Request = Request.Builder().url(url).method("GET",null).build()
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

        var myApplication:MyApplication = this.context?.applicationContext as MyApplication
        var datas: JSONArray = JSONObject.parseObject(t).get("data") as JSONArray
        if(datas.size<=0){
            return
        }
        addCourseData(datas)

        myApplication.setCourseList(datas.toJSONString())
        myApplication.courseListChanged = false
    }
    fun loadLocalCourseList():Boolean{
        var myApplication:MyApplication = this.context?.applicationContext as MyApplication
        if(myApplication==null||myApplication.getCourseList()==null||myApplication.courseListChanged){
            return false
        }
        var courseList = myApplication.getCourseList()
        Log.i("LZH","courseList: "+courseList)
        var datas: JSONArray = JSONArray.parseArray(courseList)
        addCourseData(datas)
        return true
    }
    fun addCourseData(datas:JSONArray){
        var i = 0
        var course: JSONObject
        var courseInfo: CourseInfo
        while (i<datas.size){
            course = datas.getJSONObject(i)
            courseInfo = CourseInfo()
            courseInfo.courseId = course.getString("course_id")
            courseInfo.courseName = course.getString("course_name")
            courseInfo.teacherName = course.getString("name")+"老师"
            courseInfo.coursePlace = course.getString("class")
            courseInfo.courseTime = course.getString("time")
            courseInfo.weekNum = course.getString("week")
            courseInfo.studentNum = course.getString("number").toInt()
            courseInfo.classNumber = course.getString("courseid")

            adapter.addItem(courseInfo)
            i++
        }

        adapter.notifyDataSetChanged()
        Log.i("LZH","data update")
    }
    fun updateData(){
        myHandler.setProcesser(this as NetResponseListener<Any>)
        var myApplication:MyApplication = this.activity?.application as MyApplication
        var personInfo = myApplication.getPersonalInfo()
        if(personInfo==null){
            Log.i("LZH","不能获取用户信息")
            return
        }
        loadData(personInfo!!)
    }

    fun setHandler(handler:MyHandler){
        myHandler = handler
    }
}

