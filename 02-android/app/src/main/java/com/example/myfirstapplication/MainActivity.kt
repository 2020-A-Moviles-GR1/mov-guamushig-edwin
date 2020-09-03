package com.example.myfirstapplication

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
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

        btn_intent_implicito.setOnClickListener {
            enviarIntentConRespuesta()
        }

        btn_respuesta_propia.setOnClickListener {
            enviarIntentConRespuestaPropia()
        }

        btn_http.setOnClickListener {
            abrirActivityHttp()
        }

        btn_recylcer.setOnClickListener {
            abrirRecyclerView()
        }
    }

    fun abrirRecyclerView() {
        val intentExplicito = Intent(
            this,
            RecyclerViewActivity::class.java
        )
        startActivity(intentExplicito)
    }

    fun abrirActivityHttp() {
        val intentExplicito = Intent(
            this,
            HttpActivity::class.java
        )
        startActivity(intentExplicito)
    }

    fun enviarIntentConRespuestaPropia(){
        val intentExplicito = Intent(
            this,
            IntentEnviarParametros::class.java
        )
        startActivityForResult(intentExplicito, 305)
    }

    fun irCicloDeVida() {
        val intentExplicito = Intent(
            this,
            ActivityCicloVida::class.java
        )

        startActivity(intentExplicito)
    }

    fun enviarIntentConRespuesta() {
        val intentConRespuesta = Intent(
            Intent.ACTION_PICK,
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        )

        startActivityForResult(intentConRespuesta, 304)
    }

    override fun onActivityResult(
        requestCode: Int, // numero que enviamos
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)

        when (resultCode) {
            Activity.RESULT_OK -> {
                Log.i("resultado", "resultado Ok")
                when (requestCode) {
                    304 -> { // codigo result code de constactos
                        val uri = data?.data
                        if (uri != null) {
                            val cursor = contentResolver.query(
                                uri,
                                null,
                                null,
                                null,
                                null,
                                null
                            )
                            cursor?.moveToFirst()
                            val indiceTelefono = cursor?.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER
                            )
                            val telefono = cursor?.getString(indiceTelefono!!)
                            cursor?.close()
                            Log.i("resultado", "Telefono: ${telefono}")
                        }
                    }
                    305 -> {
                        if(data != null) {
                            val nombre = data.getStringExtra("nombre")
                            val edad = data.getIntExtra("edad", -1)
                            Log.i("resultado", "Nombre: ${nombre} y edad: ${edad}")
                        }
                    }
                }
            }
            Activity.RESULT_CANCELED -> {
                Log.i("resultado", "resultado bad :c")
            }
        }
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