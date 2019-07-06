package com.example.testmap

import android.util.Log
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation

class MyLocationListener: BDAbstractLocationListener() {
    override fun onReceiveLocation(p0: BDLocation?) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        if(p0==null){
            Log.i("LZH","p0 is null")
        }
        var latitude:Double = p0?.latitude?:0.0
        var longitude:Double = p0?.longitude?:0.0
        var radius:Float = p0?.radius?:0f
        var errorCode:Int = p0?.locType?:-1
//        var showlat:Int = latitude.toInt()
//        Log.i("LZH","error code: "+errorCode)
//        Log.i("LZH","lat: "+latitude.toFloat()+" lon: "+longitude.toFloat())

    }
}