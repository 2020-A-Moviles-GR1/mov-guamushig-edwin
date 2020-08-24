package com.example.myfirstapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.github.kittinunf.fuel.httpGet
import kotlinx.android.synthetic.main.activity_http.*
import com.github.kittinunf.result.Result

class HttpActivity : AppCompatActivity() {
    val url = "http://192.168.100.10:1337"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_http)

        btn_obtner.setOnClickListener {
            obtenerUsuarios()
        }
    }

    fun obtenerUsuarios() {
        val urlUsuario = "$url/usuario"
        urlUsuario
            .httpGet()
            .responseString {
                request, response, result ->
                when(result) {
                    is Result.Success -> {
                        val data = result.get()
                        Log.i("http-klaxon", "Data: ${data}")
                    }
                    is Result.Failure -> {
                        val ex = result.getException()
                        Log.i("http-klaxon", "Error http ${ex.message}")
                    }
                }
            }
    }
}