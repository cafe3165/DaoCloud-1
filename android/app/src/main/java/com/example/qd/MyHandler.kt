package com.example.qd

import android.os.Handler
import android.os.Message
import com.example.qd.netAccess.NetResponseListener

class MyHandler: Handler() {
    lateinit var netProcesser:NetResponseListener<Any>
    fun setProcesser(p:NetResponseListener<Any>){
        netProcesser = p
    }
    override fun handleMessage(msg: Message?) {
//        super.handleMessage(msg)
        netProcesser.process(msg!!.obj)
    }
}