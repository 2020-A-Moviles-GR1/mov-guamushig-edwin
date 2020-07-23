package com.example.myfirstapplication

class ServicioDBBMemoria {

    companion object {
        var numero = 0
        fun aniadirNumero() {
            this.numero = this.numero + 1
        }
    }
}