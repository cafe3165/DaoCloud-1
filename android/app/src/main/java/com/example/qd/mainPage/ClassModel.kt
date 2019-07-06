package com.example.qd.mainPage

import android.arch.lifecycle.ViewModel;
import com.example.qd.dao.CourseInfo
import com.example.qd.data.ClassInfoNode

class ClassModel : ViewModel() {
    // TODO: Implement the ViewModel
    var classInfos:MutableList<CourseInfo> = mutableListOf<CourseInfo>()
    fun addClassInfo(classNumber:String,className:String,teacher:String){
        var node:CourseInfo = CourseInfo(classNumber,className,teacher)
        classInfos.add(node)
    }
}
