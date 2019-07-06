package com.example.qd.clockIn

import android.animation.Animator
import android.animation.Animator.*
import android.animation.ValueAnimator
import android.graphics.Point
import android.os.Bundle
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Toast
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import com.example.qd.R
import com.example.qd.dao.CourseInfo
import kotlinx.android.synthetic.main.clock_in.*
import kotlinx.android.synthetic.main.top_block.*
import okhttp3.*
import java.io.IOException
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.max


class ClockIn: AppCompatActivity() {
    private var identifyUrl = "http://47.106.131.133:3000/roll"
    var mLocationClient : LocationClient? = null
    val itemData: MutableList<ClockInItem> = ArrayList<ClockInItem>()
    var clockInAdapter : ClockInAdapter?=null
    var courseInfo:CourseInfo? = null
    companion object{
        val Course_Info:String = "Course_Info"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.clock_in)

        mLocationClient = LocationClient(getApplicationContext())
        mLocationClient?.registerLocationListener(object : BDAbstractLocationListener() {
            override fun onReceiveLocation(p0: BDLocation?) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                if(p0==null){
                    Log.i("LZH","p0 is null")
                }
                var latitude:Double = p0?.latitude?:0.0
                var longitude:Double = p0?.longitude?:0.0
                var radius:Float = p0?.radius?:0f
                var errorCode:Int = p0?.locType?:-1
//                showLocation.text = "lat: "+latitude.toFloat()+" lon: "+longitude.toFloat()
//                Log.i("LZH","error code: "+errorCode)
//                Log.i("LZH","lat: "+latitude.toFloat()+" lon: "+longitude.toFloat())
            }

        })

        init()
        initClockList()
        initData()
    }
    fun initData(){
        courseInfo = intent.getParcelableExtra<CourseInfo>("Course_Info")
        topMiddleText.text = courseInfo?.courseName
        topLeftImageView.setImageResource(0)
        topLeftTextView.text = ""
        topRightTextView.text = ""
    }
    fun initClockList(){

        var item:ClockInItem = ClockInItem("2019-12-12 签到","13:32","已签到")
        itemData.add(item)
        clockInInfos.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        clockInAdapter = ClockInAdapter(this,itemData)
        clockInInfos.adapter = clockInAdapter
        clockInInfos.itemAnimator = DefaultItemAnimator()
    }
    fun init(){
        val option = LocationClientOption()

        option.locationMode = LocationClientOption.LocationMode.Hight_Accuracy
        //可选，设置定位模式，默认高精度
        //LocationMode.Hight_Accuracy：高精度；
        //LocationMode. Battery_Saving：低功耗；
        //LocationMode. Device_Sensors：仅使用设备；

        option.setCoorType("bd09ll")
        //可选，设置返回经纬度坐标类型，默认GCJ02
        //GCJ02：国测局坐标；
        //BD09ll：百度经纬度坐标；
        //BD09：百度墨卡托坐标；
        //海外地区定位，无需设置坐标类型，统一返回WGS84类型坐标

        option.setScanSpan(1000)
        //可选，设置发起定位请求的间隔，int类型，单位ms
        //如果设置为0，则代表单次定位，即仅定位一次，默认为0
        //如果设置非0，需设置1000ms以上才有效

        option.isOpenGps = true
        //可选，设置是否使用gps，默认false
        //使用高精度和仅用设备两种定位模式的，参数必须设置为true

        option.isLocationNotify = true
        //可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false

        option.setIgnoreKillProcess(false)
        //可选，定位SDK内部是一个service，并放到了独立进程。
        //设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)

        option.SetIgnoreCacheException(false)
        //可选，设置是否收集Crash信息，默认收集，即参数为false

        option.setWifiCacheTimeOut(5 * 60 * 1000)
        //可选，V7.2版本新增能力
        //如果设置了该接口，首次启动定位时，会先判断当前Wi-Fi是否超出有效期，若超出有效期，会先重新扫描Wi-Fi，然后定位

        option.setEnableSimulateGps(false)
        //可选，设置是否需要过滤GPS仿真结果，默认需要，即参数为false

        mLocationClient?.setLocOption(option)
        //mLocationClient为第二步初始化过的LocationClient对象
        //需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        //更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明
    }
    fun start(view: View){
        mLocationClient?.start()

    }
    fun wave(view:View){
        var startRadius = 0;
        var endRadius = max(view.width,view.height)
        var startAlpha = 0
        var endAlpha = 75
//        var gradient = endRadius/20f
//        while(startRadius<endRadius){
//            waveButton.waveRadius = startRadius
//            waveButton.postInvalidateDelayed(100)
//            startRadius+=gradient.toInt()
//        }
//        waveButton.waveRadius = endRadius.toInt()
//        waveButton.postInvalidateDelayed(100)


//        val anim = ValueAnimator.ofFloat(startRadius.toFloat(), endRadius)
        val anim = ValueAnimator.ofObject(MyTypeEvaluator(),Point(0,0),Point(endAlpha,endRadius.toInt()))
        anim.duration = 400

        anim.addUpdateListener { animation ->
//            val currentValue = animation.animatedValue as Float
//            Log.d("LZH", "cuurent value is $currentValue")
            var point:Point = animation.animatedValue as Point
            waveButton.waveRadius = point.y
            waveButton.alpha = point.x
            waveButton.invalidate()
        }
        anim.addListener(object:AnimatorListener{
            override fun onAnimationRepeat(animation: Animator?) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onAnimationCancel(animation: Animator?) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onAnimationStart(animation: Animator?) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onAnimationEnd(animation: Animator?) {
                waveButton.waveRadius = 0
                waveButton.alpha = 0
                waveButton.invalidate()
            }

        })
        anim.start()
        Log.i("LZH","start wave")
        addItem()
    }

    fun addItem(){
        var item:ClockInItem = ClockInItem(getDay()+" 签到",getHour(),"已签到")
        clockInAdapter?.addItem(item)
        clockInAdapter?.notifyDataSetChanged()
    }
    fun sendQianDaoInfo(){
        var okHttpClient: OkHttpClient = OkHttpClient()
        var id = courseInfo?.courseId
        var place = courseInfo?.coursePlace
        var state = 1
        if(Random().nextInt(3)>=2){
            state = 3
        }
        val requestBody = FormBody.Builder().add("id", id).add("time", getDayHour())
            .add("position",place).add("state",state.toString()).build()
        var request: Request = Request.Builder().url(identifyUrl).method("POST",requestBody).build()
        var call: Call = okHttpClient.newCall(request)
        call.enqueue(object: Callback {
            override fun onFailure(call: Call?, e: IOException?) {

            }

            override fun onResponse(call: Call?, response: Response?) {
                var body:String = response!!.body().string()
                Log.i("LZH","code: "+response!!.code()+" body: "+body)
            }

        })
    }
    fun getDay():String{
        var df:DateFormat = SimpleDateFormat("yyyy-mm-dd")
        return df.format(Date())
    }
    fun getHour():String{
        var df:DateFormat = SimpleDateFormat("HH:mm")
        return df.format(Date())
    }
    fun getDayHour():String{
        var df:DateFormat = SimpleDateFormat("yyyy-mm-dd HH:mm")
        return df.format(Date())
    }
}