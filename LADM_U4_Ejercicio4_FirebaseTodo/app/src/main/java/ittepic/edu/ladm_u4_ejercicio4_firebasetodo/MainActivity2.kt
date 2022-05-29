package ittepic.edu.ladm_u4_ejercicio4_firebasetodo

import android.content.Intent
import android.graphics.BitmapFactory
import android.icu.util.GregorianCalendar
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import ittepic.edu.ladm_u4_ejercicio4_firebasetodo.databinding.ActivityMain2Binding
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class MainActivity2 : AppCompatActivity() {
    lateinit var binding: ActivityMain2Binding
    lateinit var imagen : Uri
    lateinit var listaArchivos : ArrayList<String>

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        cargarLista()

        binding.insertar.setOnClickListener {
            val baseRemota = FirebaseFirestore.getInstance()

            val datos = hashMapOf(
                "descripcion" to binding.descripcion.text.toString(),
                "direccion" to binding.direccion.text.toString(),
            )
            baseRemota.collection("almacen")
                .add(datos)
                .addOnSuccessListener {
                    binding.descripcion.text.clear()
                    binding.direccion.text.clear()
                    Toast.makeText(this,"Se insertó",Toast.LENGTH_LONG)
                        .show()
                }
                .addOnFailureListener {
                    AlertDialog.Builder(this).setMessage(it.message).show()
                }
        }

        binding.seleccionar.setOnClickListener {
            val galeria = Intent(Intent.ACTION_GET_CONTENT)
            galeria.type = "image/*"
            startActivityForResult(galeria, 2)
        }

        binding.enviar.setOnClickListener {
            var nombrarArchivo = ""
            val cal = GregorianCalendar.getInstance()

            nombrarArchivo = cal.get(Calendar.YEAR).toString()+
                    cal.get(Calendar.MONTH).toString()+
                    cal.get(Calendar.DAY_OF_MONTH).toString()+
                    cal.get(Calendar.HOUR).toString()+
                    cal.get(Calendar.MINUTE).toString()+
                    cal.get(Calendar.SECOND).toString()+
                    cal.get(Calendar.MILLISECOND).toString()+".jpg"

            val storageRef = FirebaseStorage.getInstance()
                .reference.child("imagenes/${nombrarArchivo}")

            storageRef.putFile(imagen)
                .addOnSuccessListener {
                    Toast.makeText(this, "Se subio el archivo", Toast.LENGTH_SHORT)
                        .show()
                     cargarLista()
                    binding.imagen.setImageBitmap(null)
                }
                .addOnFailureListener {
                    AlertDialog.Builder(this)
                        .setMessage(it.message)
                        .show()
                }
        }
    }

    private fun cargarLista() {
      val storageRef = FirebaseStorage.getInstance()
          .reference.child("imagenes")

        storageRef.listAll()
            .addOnSuccessListener {
                listaArchivos.clear()
                it.items.forEach {
                    listaArchivos.add(it.name)
                }
                binding.lista.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, listaArchivos)
                cargarImagen(listaArchivos.get(it))
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 2){
            imagen = data!!.data!!
            binding.imagen.setImageURI(imagen)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menuoculto, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.acerca->{

            }
            R.id.sesion->{
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(this, MainActivity::class.java))
                finish()

            }
            R.id.salir->{

            }
        }
        return true
    }

    private fun cargarImagen(archivo: String){
        val storageRef = FirebaseStorage.getInstance()
            .reference.child("imagenes${archivo}")

        val archivoTemporal = File.createTempFile("imagenTemp","jpg")

        storageRef.getFile(archivoTemporal)
            .addOnSuccessListener {
                val mapBits = BitmapFactory.decodeFile(archivoTemporal.absolutePath)
                binding.imagen.setImageBitmap(mapBits)
            }
    }
}