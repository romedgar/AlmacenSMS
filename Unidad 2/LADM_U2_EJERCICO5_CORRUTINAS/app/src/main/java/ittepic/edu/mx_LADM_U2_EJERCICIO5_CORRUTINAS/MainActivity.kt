package ittepic.edu.mx_LADM_U2_EJERCICIO5_CORRUTINAS

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ittepic.edu.mx_LADM_U2_EJERCICIO5_CORRUTINAS.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import kotlin.coroutines.EmptyCoroutineContext

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    val mensajes = arrayOf("Hola mundo","Terricola","Android","Pelicula")
    var cadena = ""

    val scope = CoroutineScope(Job()+Dispatchers.Main)
    var contador = 0
    val objetoCorrutinaControlada = scope.launch(EmptyCoroutineContext, CoroutineStart.LAZY){
        while (true){
            runOnUiThread {
                binding.etJob.text = mensajes[contador++]
            }
            if(contador==mensajes.size) contador=0
            delay(500L)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)


        setContentView(binding.root)

        binding.btnRunblocking.setOnClickListener {
            rutina1erPlanoAsincrona()
            binding.etRunblocking.text=cadena

        }

        binding.btnGlobalscope.setOnClickListener {
            rutina2doPlanoAsincrona()
        }

        binding.btnJob.setOnClickListener {
            if(objetoCorrutinaControlada.isActive){
                objetoCorrutinaControlada.cancel()
                return@setOnClickListener
            }
            if(objetoCorrutinaControlada.isCancelled){
                setTitle("SE DIO DE BAJA LA MEMORIA DE LA COROUTINE")
                return@setOnClickListener
            }
            objetoCorrutinaControlada.start()
        }
    }

    fun rutina1erPlanoAsincrona() = runBlocking {
        launch {
            for (mensaje in mensajes){
                cadena+="\n$mensaje"
                delay(500L)
            }
        }
        cadena = "MENSAJES CONCATENADOS:"
    }
    //Sustituyeron las AsyncTask = hilo asíncrono
    fun rutina2doPlanoAsincrona() = GlobalScope.launch {
        for (mensaje in mensajes){
            runOnUiThread {
                binding.etGlobalscope.text = mensaje
            }
            delay(3000L)
        }
    }
}