package com.example.qd.clockIn

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.qd.R

class ClockInAdapter constructor(context: Context,dataList: MutableList<ClockInItem>
): RecyclerView.Adapter<ViewHolder>(){
    var itemList : MutableList<ClockInItem>? = null
    var context: Context? = null
    init {
        this.context = context
        this.itemList = dataList
    }
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        var view: View = LayoutInflater.from(parent.context).inflate(R.layout.clock_in_info,parent,false)
        var holder: ViewHolder = ViewHolder(view)
        return holder
    }

    override fun getItemCount(): Int {
        return itemList?.size?:0
    }

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        var item:ClockInItem? = itemList?.get(pos)
        holder.describeView.text = item?.clockDescribe?:"unknownDescribe"
        holder.stateView.text = item?.clockState?:"unknownState"
        holder.timeView.text = item?.clockTime?:"unknownTime"
    }
    fun addItem(item:ClockInItem){
        itemList?.add(item)
    }

}