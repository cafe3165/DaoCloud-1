package com.example.qd.clockIn

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.example.qd.R
import kotlinx.android.synthetic.main.clock_in_info.view.*

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var describeView:TextView = itemView.findViewById<TextView>(R.id.clockDescribe)
    var timeView:TextView = itemView.findViewById(R.id.clockInTime)
    var stateView:TextView = itemView.findViewById(R.id.clockInState)

}