package com.example.qd.mainPage

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.example.qd.R

class ClassViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var view:View = itemView
    var classNumber:TextView = itemView.findViewById(R.id.classNumber)
    var className:TextView = itemView.findViewById(R.id.className)
    var teacher:TextView = itemView.findViewById(R.id.teacher)


}