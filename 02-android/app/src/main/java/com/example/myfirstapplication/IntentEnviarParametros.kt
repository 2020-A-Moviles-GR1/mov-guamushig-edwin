package com.example.myfirstapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_intent_enviar_parametros.*

class IntentEnviarParametros : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intent_enviar_parametros)

        val numeroEncontrado = intent.getIntExtra("numero", -1)

        if(numeroEncontrado != -1) {
            Log.i("intents", "Numero encontrado ${numeroEncontrado}")
        }

        val textoCompartido: String? = intent.getStringExtra(Intent.EXTRA_TEXT)

        if(textoCompartido != null) {
            Log.i("intents", "Texto compartido ${textoCompartido}")
        }

        btn_devolcver_respuesta
            .setOnClickListener {
                // metodo de la clase this.finish
                finish()
            }
    }
}