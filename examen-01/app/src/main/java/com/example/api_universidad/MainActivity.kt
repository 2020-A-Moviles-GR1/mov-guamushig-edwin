package com.example.api_universidad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val universidad_view = findViewById<ImageView>(R.id.img_view_universidad) as ImageView
        val estudiante_view = findViewById<ImageView>(R.id.img_view_estudiante) as ImageView

        universidad_view.setOnClickListener {
            irAUniversidad()
        }

        estudiante_view.setOnClickListener {
        }
    }

    fun irAUniversidad() {
        val intentExplicito = Intent(
            this,
            UniversidadActivity::class.java
        )
        startActivity(intentExplicito)
    }

    fun irAEstudiante() {

    }
}