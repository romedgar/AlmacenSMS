package ittepic.edu.ladm_u4_ejercicio3_realtimefirebase

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import ittepic.edu.ladm_u4_ejercicio3_realtimefirebase.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var  binding: ActivityMainBinding
    var listaIDs = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val consulta = FirebaseDatabase.getInstance().getReference().child("users")

        val postListener = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var datos = ArrayList<String>()
                listaIDs.clear()

                for(data in snapshot.children!!){
                    val id = data.key
                    listaIDs.add(id!!)
                    val usuario = data.getValue<User>()!!.username
                    val correo = data.getValue<User>()!!.email
                    datos.add("nombre ${usuario}\ncorreo: ${correo}")
                }
                mostrarLista(datos)
            }

            override fun onCancelled(error: DatabaseError) {

            }

        }

        consulta.addValueEventListener(postListener)

        binding.insertar.setOnClickListener {
            var basedatos = Firebase.database.reference

            val usuario = User(binding.nombre.text.toString(), binding.correo.text.toString()) //equivalente a hashMapOf en Firestore

            basedatos.child("users")
                .push().setValue(usuario)
                .addOnSuccessListener { setTitle("SE INSERTO")
                binding.nombre.setText("")
                }
                .addOnFailureListener {
                    AlertDialog.Builder(this)
                        .setMessage(it.message)
                        .setPositiveButton("Ok"){d, i ->}
                        .show()
                }
        }
    }

    private fun mostrarLista(datos: ArrayList<String>) {
        binding.lista.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,datos)

    }
}