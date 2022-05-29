package ittepic.edu.ladm_u3_ejercicio2_sqliteclasecontrolador

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import ittepic.edu.ladm_u3_ejercicio2_sqliteclasecontrolador.databinding.ActivityMain2Binding
import ittepic.edu.ladm_u3_ejercicio2_sqliteclasecontrolador.databinding.ActivityMainBinding

class MainActivity2 : AppCompatActivity() {
    lateinit var binding: ActivityMain2Binding
    var noControl = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        noControl = this.intent.extras!!.getString("nocontrol")!!
        val alumno = Alumno(this).mostrarUno(noControl)

        binding.nombre.setText(alumno.nombre)
        binding.carrera.setText(alumno.carrera)

        binding.actualizar.setOnClickListener {
            var alumno = Alumno(this)
            alumno.noControl = noControl
            alumno.nombre = binding.nombre.text.toString()
            alumno.carrera = binding.carrera.text.toString()

            val respuesta = alumno.actualizar()

            if(respuesta){
                Toast.makeText(this,"Se Actualizo correctamente", Toast.LENGTH_LONG)
                    .show()
                binding.nombre.setText("")
                binding.carrera.setText("")
            } else {
                AlertDialog.Builder(this)
                    .setTitle("Atencion")
                    .setMessage("ERROR NO SE ACTUALIZO")
            }
        }
        binding.regresar.setOnClickListener {
            finish()
        }
    }
}