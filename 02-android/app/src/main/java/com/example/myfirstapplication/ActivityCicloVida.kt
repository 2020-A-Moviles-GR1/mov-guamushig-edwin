package com.example.myfirstapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_ciclo_vida.*

class ActivityCicloVida : AppCompatActivity() {

    var numeroActual = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ciclo_vida)
        Log.i("Activity", "On create")
        numeroActual = ServicioDBBMemoria.numero
        if (numeroActual != 0) {
            txt_view_valores.text = numeroActual.toString()
        }
        btn_aniadir.setOnClickListener {
            sumarUnValor()
        }
    }

    fun sumarUnValor() {
        ServicioDBBMemoria.aniadirNumero()
        numeroActual = numeroActual + 1
        txt_view_valores.text = numeroActual.toString()
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.i("Activity", "On save instance")
        outState.run {
            putInt("numeroActualGuardado", numeroActual)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.i("Activity", "On restore instance")
        val valorRecuperado = savedInstanceState?.getInt("numeroActualGuardado")
        if (valorRecuperado != null) {
            this.numeroActual = valorRecuperado
            txt_view_valores.text = valorRecuperado.toString()
        }
    }
}