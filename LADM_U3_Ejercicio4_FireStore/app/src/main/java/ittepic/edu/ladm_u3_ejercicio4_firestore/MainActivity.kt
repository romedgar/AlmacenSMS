package ittepic.edu.ladm_u3_ejercicio4_firestore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.FirebaseFirestore
import ittepic.edu.ladm_u3_ejercicio4_firestore.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    var listaID = ArrayList<String>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //EVENTO (SE DISPARA SOLO)
        FirebaseFirestore.getInstance()
            .collection("persona")
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                if(firebaseFirestoreException != null){
                    //Si hubo
                    AlertDialog.Builder(this)
                        .setMessage(firebaseFirestoreException.message)
                        .show()
                    return@addSnapshotListener
                }
                var arreglo = ArrayList<String>()
                listaID.clear()
                for(documneto in querySnapshot!!){
                    var cadena = "nombre: ${documneto.getString("nombre")}\n" +
                            "Edad: ${documneto.getLong("edad")}"
                    arreglo.add(cadena)
                    listaID.add(documneto.id)
                }
                binding.lista.adapter = ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,arreglo)

                binding.lista.setOnItemClickListener { adapterView, view, pos, l ->
                    val idSeleccionado = listaID.get(pos)
                    AlertDialog.Builder(this).setTitle("ATENCIÓN")
                        .setMessage("Qué deseas hacer con ID: ${idSeleccionado}?")
                        .setNeutralButton("ELIMINAR"){d, i->
                            eliminar(idSeleccionado)
                        }
                        .setPositiveButton("ACTUALIZAR"){d, i->
                            actualizar(idSeleccionado)
                        }
                        .setNegativeButton("SALIR"){d, i->

                        }
                        .show()

                }
            }

        binding.insertar.setOnClickListener {
            val baseRemota = FirebaseFirestore.getInstance()

            val datos = hashMapOf(
                "nombre" to binding.nombre.text.toString(),
                "domicilio" to binding.domicilio.text.toString(),
                "edad" to binding.edad.text.toString().toInt()
            )

            baseRemota.collection("persona")
                .add(datos)
                .addOnSuccessListener {
                    //Si se pudo
                    Toast.makeText(this,"Exito! Se inserto", Toast.LENGTH_LONG)
                        .show()
                }
                .addOnFailureListener {
                    //No se pudo
                    AlertDialog.Builder(this)
                        .setMessage(it.message)
                        .show()
                }
            binding.nombre.setText("")
            binding.edad.setText("")
            binding.domicilio.setText("")
        }
    }

    private fun actualizar(idSeleccionado: String) {
        var otraVentana = Intent(this, MainActivity2::class.java)

        otraVentana.putExtra("idseleccionado", idSeleccionado)

        startActivity(otraVentana)

    }


    private fun eliminar(idElegido: String) {
        val baseRemota = FirebaseFirestore.getInstance()
        baseRemota.collection("persona")
            .document(idElegido)
            .delete()
            .addOnSuccessListener {
                Toast.makeText(this,"Se elimino con éxito", Toast.LENGTH_LONG)
            }
            .addOnFailureListener {
                AlertDialog.Builder(this)
                    .setMessage(it.message)
                    .show()
            }
    }
}