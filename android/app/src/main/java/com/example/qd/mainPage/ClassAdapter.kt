package com.example.qd.mainPage

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.qd.R
import com.example.qd.course.CourseInfoActivity
import com.example.qd.course.CourseInfoActivity.Companion.COURSE_INFO
import com.example.qd.dao.CourseInfo
import com.example.qd.data.ClassInfoNode

class ClassAdapter(context: Context,list:MutableList<CourseInfo>) : RecyclerView.Adapter<ClassViewHolder>(), View.OnClickListener {
    var context:Context = context
    var classInfos :MutableList<CourseInfo> = list
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ClassViewHolder {
        var view:View = LayoutInflater.from(p0.context).inflate(R.layout.class_item,p0,false)
        return ClassViewHolder(view)
    }

    override fun getItemCount(): Int {
        return classInfos.size
    }
    fun addItem(node:CourseInfo){
        classInfos.add(node)
    }
    override fun onBindViewHolder(p0: ClassViewHolder, p1: Int) {
        var courseInfo:CourseInfo = classInfos.get(p1)
        p0.classNumber.text = courseInfo.classNumber
        p0.className.text = courseInfo.courseName
        p0.teacher.text = courseInfo.teacherName
        p0.view.setTag(courseInfo)
        p0.view.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        var intent: Intent = Intent(context,CourseInfoActivity::class.java)
        var courseInfo:CourseInfo = v?.tag as CourseInfo
        intent.putExtra(COURSE_INFO,courseInfo)
        context.startActivity(intent)
    }
}