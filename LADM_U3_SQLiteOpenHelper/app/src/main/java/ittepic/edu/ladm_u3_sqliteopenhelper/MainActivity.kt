package ittepic.edu.ladm_u3_sqliteopenhelper

import android.content.ContentValues
import android.database.sqlite.SQLiteException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import ittepic.edu.ladm_u3_sqliteopenhelper.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        mostrar()
        binding.insertar.setOnClickListener {
            val baseDatos = BaseDatos(this, "ejemplo1",null,1)
            try {
                var datos = ContentValues()
                val tablaPersona = baseDatos.writableDatabase

                datos.put("NOMBRE",binding.nom.text.toString())
                datos.put("DOMICILIO",binding.dom.text.toString())
                datos.put("TELEFONO",binding.tel.text.toString())

                val resultado = tablaPersona.insert("PERSONA","ID",datos)
                if(resultado==-1L){
                    AlertDialog.Builder(this)
                        .setTitle("Error")
                        .setMessage("No se insertó")
                        .show()
                }else{
                    AlertDialog.Builder(this)
                        .setTitle("Exito")
                        .setMessage("Se insertó correctamente")
                        .show()
                    mostrar()
                }


            }catch (err: SQLiteException){
                AlertDialog.Builder(this)
                    .setMessage(err.message)
                    .show()

            }finally {
                baseDatos.close()
            }

        }
    }
    fun mostrar(){
        val baseDatos = BaseDatos(this, "ejemplo1",null,1)
        var arreglo = ArrayList<String>()
        try{
            val tablaPersona = baseDatos.readableDatabase

            var cursor = tablaPersona.query("PERSONA", arrayOf("NOMBRE"),null,null,null,null,null)

            if(cursor.moveToFirst()){
                do{
                    arreglo.add(cursor.getString(0))
                }while (cursor.moveToNext())

            }else{
                arreglo.add("NO HAY RESULTADOS")
            }
        }catch (err:SQLiteException){
            AlertDialog.Builder(this)
                .setMessage(err.message)
                .show()
        }finally {
            baseDatos.close()
        }

        binding.lista.adapter = ArrayAdapter<String>(this,
            android.R.layout.simple_list_item_1,arreglo)
    }
}