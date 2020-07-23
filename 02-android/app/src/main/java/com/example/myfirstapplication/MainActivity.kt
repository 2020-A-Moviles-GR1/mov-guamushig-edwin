package com.example.myfirstapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_ciclo_vida.setOnClickListener { boton ->
            irCicloDeVida()
        }

        btn_list_view.setOnClickListener { boton ->
            irAListView()
        }

        btn_intent_respuesta.setOnClickListener {
            irAIntentRespuesta()
        }
    }

    fun irCicloDeVida() {
        val intentExplicito = Intent(
            this,
            ActivityCicloVida::class.java
        )

        startActivity(intentExplicito)
    }

    fun irAIntentRespuesta() {
        val intentExplicito = Intent(
            this,
            IntentEnviarParametros::class.java
        )
        intentExplicito.putExtra("numero", 69)
        startActivity(intentExplicito)
    }

    fun irAListView() {
        val intentExplicito = Intent(
            this,
            BListViewActivity::class.java
        )
        startActivity(intentExplicito)
    }

}