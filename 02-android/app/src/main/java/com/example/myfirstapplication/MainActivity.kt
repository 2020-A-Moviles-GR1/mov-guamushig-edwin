package com.example.myfirstapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i("Activity", "On create")
    }

    override fun onStart() {
        super.onStart()
        Log.i("Activity", "On start")
    }

    override fun onResume() {
        super.onResume()
        Log.i("Activity", "On resume")
    }

    override fun onPause() {
        super.onPause()
        Log.i("Activity", "On pause")
    }

    override fun onStop() {
        super.onStop()
        Log.i("Activity", "On stop")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i("Activity", "On restart")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("Activity", "On destroy")
    }
}