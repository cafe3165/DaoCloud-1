package com.example.qd.course

import android.Manifest
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.qd.R
import kotlinx.android.synthetic.main.activity_create_course.*
import kotlinx.android.synthetic.main.top_block.*
import android.provider.MediaStore
import android.R.attr.data
import android.app.Activity
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.Manifest.permission
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.support.v4.app.ActivityCompat
import android.content.pm.PackageManager
import android.os.Build
import android.support.annotation.RequiresApi
import com.example.qd.mainPage.MainPage


class CreateCourseActivity : AppCompatActivity() ,View.OnClickListener{


    private var IMAGEFLAG:Int = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_course)

        initTopBlock()

        Request()
    }
    fun createNewCourse(view:View){
        if(!checkInfo()){
            return
        }
        var intent:Intent = Intent(this,CreateCourseState::class.java)
        startActivity(intent)
        finish()
    }
    fun checkInfo():Boolean{
        if(className.text?.length?:-1>0 && courseName.text?.length?:-1>0){
            return true;
        }
        return false;
    }

    fun searchImage(view:View){
        var intent:Intent = Intent(Intent.ACTION_PICK,
            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent,IMAGEFLAG)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === IMAGEFLAG && resultCode === Activity.RESULT_OK && data != null) {
            val selectedImage = data.data

            val imagePath = getImagePath(selectedImage,null)
            showImage(imagePath)
        }
    }
    fun getImagePath(uri: Uri, selection:String? ):String {
        var path:String  = "";
        Log.i("LZH",uri.toString())
        //通过uri 和 selection 获取真实的图片路径
        var cursor:Cursor  = getContentResolver().query(uri,null,selection,null,null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                Log.i("LZH",cursor.getColumnIndex(MediaStore.Images
                    .Media.DATA).toString())
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images
                    .Media.DATA));
                Log.i("LZH",path)
            }
            cursor.close();
        }
        return path;
    }
    fun showImage(path:String){
        Log.i("LZH",path)
//        var bitmap:Bitmap  = BitmapFactory.decodeFile(path)
//        courseIcon.setImageBitmap(bitmap)
        if (path != null) {
            var bitmap:Bitmap  = BitmapFactory.decodeFile(path);
            courseIcon.setImageBitmap(bitmap);
        }
    }
    fun initTopBlock(){
        topLeftImageView.setImageResource(R.drawable.left_arrow)
        topLeftTextView.text = "取消"
        topRightTextView.text = ""
        topMiddleText.text = "创建班课"

        topLeftImageView.setOnClickListener(this)
        topLeftTextView.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        finish()
    }
    fun Request() {
        //获取相机拍摄读写权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //版本判断
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA
                    ),
                    1
                )
            }
        }
    }

}
