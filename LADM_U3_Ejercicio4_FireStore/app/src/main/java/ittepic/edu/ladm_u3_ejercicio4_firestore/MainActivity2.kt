package ittepic.edu.ladm_u3_ejercicio4_firestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.FirebaseFirestore
import ittepic.edu.ladm_u3_ejercicio4_firestore.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {
    var idSeleccionado = ""
    lateinit var binding: ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        idSeleccionado = intent.extras!!.getString("idseleccionado")!!

        val baseRemota = FirebaseFirestore.getInstance()
        baseRemota.collection("persona")
            .document(idSeleccionado)
            .get()
            .addOnSuccessListener {
                binding.nombre.setText(it.getString("nombre"))
                binding.domicilio.setText(it.getString("domicilio"))
                binding.edad.setText(it.getLong("edad").toString())
            }
            .addOnFailureListener {
                AlertDialog.Builder(this)
                    .setMessage(it.message)
                    .show()
            }
        binding.regresar.setOnClickListener {
            finish()
        }
        binding.actualizar.setOnClickListener {
            val baseRemota = FirebaseFirestore.getInstance()
            baseRemota.collection("persona")
                .document(idSeleccionado)
                .update("nombre",binding.nombre.text.toString(),
                    "domicilio", binding.domicilio.text.toString(),
                    "edad", binding.edad.text.toString().toInt())
                .addOnSuccessListener {
                    Toast.makeText(this,"Se actualizó con éxito", Toast.LENGTH_LONG)
                        .show()
                    binding.nombre.setText("")
                    binding.domicilio.setText("")
                    binding.edad.setText("")
                }
                .addOnFailureListener {
                    AlertDialog.Builder(this)
                        .setMessage(it.message)
                        .show()
                }
        }
    }
}