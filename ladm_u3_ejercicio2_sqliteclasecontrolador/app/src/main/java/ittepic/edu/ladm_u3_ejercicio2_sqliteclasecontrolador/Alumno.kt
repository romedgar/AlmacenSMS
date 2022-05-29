package ittepic.edu.ladm_u3_ejercicio2_sqliteclasecontrolador

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteException

class Alumno(este: Context) {
    var noControl = ""
    var nombre =""
    var carrera = ""
    private var este = este
    private var err = ""

    fun insertar(): Boolean{
        var baseDatos = BaseDatos(este, "escuela",null,1)
        err = ""
        try {
            val tabla = baseDatos.writableDatabase

            var datos = ContentValues()
            datos.put("NOCONTROL",noControl)
            datos.put("NOMBRE",nombre)
            datos.put("CARRERA",carrera)

            var resultado = tabla.insert("ALUMNO",null,datos)

            if (resultado == -1L){
                return false
            }

        }catch (err: SQLiteException){
            this.err = err.message!!
            return false
        }finally {
            baseDatos.close()
        }
        return true
    }

    fun mostrarTodos(): ArrayList<Alumno>{
        val baseDatos = BaseDatos(este, "escuela",null,1)
        val arreglo = ArrayList<Alumno>()
        err = ""
        try {
            var tabla = baseDatos.readableDatabase
            var SQLSELECT = "SELECT * FROM ALUMNO"


            var cursor = tabla.rawQuery(SQLSELECT,null)
            if(cursor.moveToFirst()){
                do {
                    val alumno = Alumno(este)
                    alumno.noControl = cursor.getString(0)
                    alumno.nombre = cursor.getString(1)
                    alumno.carrera = cursor.getString(2)
                    arreglo.add(alumno)
                }while (cursor.moveToNext())
            }

        }catch (err: SQLiteException){
            this.err = err.message!!

        }finally {
            baseDatos.close()
        }
        return arreglo
    }

    fun mostrarUno(id: String): Alumno{
        val baseDatos = BaseDatos(este, "escuela",null,1)
        val alumno = Alumno(este)
        err = ""
        try {
            val tabla = baseDatos.readableDatabase
            val SQL_SELECT = "SELECT * FROM ALUMNO WHERE NOCONTROL = ?"


            var cursor = tabla.rawQuery(SQL_SELECT, arrayOf(id))
            if(cursor.moveToFirst()){

                    alumno.noControl = cursor.getString(0)
                    alumno.nombre = cursor.getString(1)
                    alumno.carrera = cursor.getString(2)
            }
        }catch (err: SQLiteException){
            this.err = err.message!!

        }finally {
            baseDatos.close()
        }
        return alumno
    }

    fun actualizar(): Boolean {
        val basedatos = BaseDatos(este, "escuela",null,1)
        err = ""
        try{
            var tabla = basedatos.writableDatabase
            val datosActualizados = ContentValues()

            datosActualizados.put("NOMBRE",nombre)
            datosActualizados.put("CARRERA",carrera)

            val respuesta = tabla.update("ALUMNO", datosActualizados, "NOCONTROL=?", arrayOf(noControl))

            if(respuesta==0){
                return false
            }
        }catch (err: SQLiteException){
            this.err = err.message!!
            return false
        }finally {
            basedatos.close()
        }
        return true
    }

    fun eliminar(noControlEliminar: String): Boolean {
        val baseDatos = BaseDatos(este,"escuela",null,1)
        err = ""
        try {
            var tabla = baseDatos.writableDatabase
            val resultado = tabla.delete("ALUMNO","NOCONTROL=?", arrayOf(noControlEliminar))
            if(resultado == 0){
                return false
            }
        } catch (err: SQLiteException){
            this.err = err.message!!
            return false
        }finally {
            baseDatos.close()
        }
        return true
    }

    fun eliminar(): Boolean {
        val baseDatos = BaseDatos(este,"escuela",null,1)
        err = ""
        try {
            var tabla = baseDatos.writableDatabase
            val resultado = tabla.delete("ALUMNO","NOCONTROL=?", arrayOf(noControl))
            if(resultado == 0){
                return false
            }
        } catch (err: SQLiteException){
            this.err = err.message!!
            return false
        }finally {
            baseDatos.close()
        }
        return true
    }
}

/*
var baseDatos = BaseDatos(este, "Escuela",null,1)
try {

}catch (err: SQLiteException){

}finally {
    baseDatos.close()
}*/