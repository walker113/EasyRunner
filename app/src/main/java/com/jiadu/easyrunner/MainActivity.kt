package com.jiadu.easyrunner

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.jiadu.easyrunner.hencode.custom_size_layout.CustomSizeLayoutActivity
import com.jiadu.easyrunner.hencode.touch.TouchActivity

class MainActivity : AppCompatActivity() , View.OnClickListener{

    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.btn_custom_size ->
                startActivity(Intent(this, CustomSizeLayoutActivity::class.java))
            R.id.btn_touch ->
                startActivity(Intent(this, TouchActivity::class.java))

        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btn_custom_size).setOnClickListener(this)
        findViewById<Button>(R.id.btn_touch).setOnClickListener(this)

    }


}
