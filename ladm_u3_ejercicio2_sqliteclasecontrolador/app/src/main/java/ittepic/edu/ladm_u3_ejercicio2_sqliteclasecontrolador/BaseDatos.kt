package ittepic.edu.ladm_u3_ejercicio2_sqliteclasecontrolador

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BaseDatos(
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE ALUMNO (NOCONTROL VARCHAR(10) PRIMARY KEY NOT NULL, NOMBRE VARCHAR(200), CARRERA VARCHAR(50))")
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
}