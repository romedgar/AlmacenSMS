package ittepic.edu.ladm_u3_ejercicio2_sqliteclasecontrolador

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import ittepic.edu.ladm_u3_ejercicio2_sqliteclasecontrolador.databinding.ActivityMainBinding
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    var listaIDs = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mostrarDatosEnListView()

        binding.insertar.setOnClickListener {
            var alumno = Alumno(this)
            alumno.noControl = binding.nocontrol.text.toString()
            alumno.nombre = binding.nombre.text.toString()
            alumno.carrera = binding.carrera.text.toString()

            val resultado = alumno.insertar()
            if(resultado){
                Toast.makeText(this,"SE INSERTO CON EXITO", Toast.LENGTH_LONG)
                    .show()
                mostrarDatosEnListView()
                binding.nocontrol.setText("")
                binding.nombre.setText("")
                binding.carrera.setText("")
            }else{
               AlertDialog.Builder(this)
                   .setTitle("ERROR")
                   .setMessage("NO SE PUDO INSERTAR")
                   .show()
            }
        }
    }

    fun mostrarDatosEnListView(){
        var listaAlumnos = Alumno(this).mostrarTodos()
        var nombreAlumnos = ArrayList<String>()

        listaIDs.clear()
        (0..listaAlumnos.size-1).forEach{
            val al = listaAlumnos.get(it)
            nombreAlumnos.add(al.nombre)
            listaIDs.add(al.noControl)
        }

        binding.lista.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,nombreAlumnos)
        binding.lista.setOnItemClickListener { adapterView, view, indice, l ->
            val noControlLista = listaIDs.get(indice)
            val alumno = Alumno(this).mostrarUno(noControlLista)

            AlertDialog.Builder(this)
                .setTitle("Atención")
                .setMessage("Qué deseas hacer con ${alumno.nombre},\n Carrera: ${alumno.carrera}?")
                .setNegativeButton("Eliminar"){d,i->
                    alumno.eliminar()
                    mostrarDatosEnListView()
                }
                .setPositiveButton("Actualizar"){d,i->
                    var otraVentana = Intent(this, MainActivity2::class.java)
                    otraVentana.putExtra("nocontrol",alumno.noControl)
                    startActivity(otraVentana)
                }
                .setNeutralButton("Cerrar"){d,i->}
                .show()
        }
    }

    override fun onRestart() {
        super.onRestart()
        mostrarDatosEnListView()
    }
}
