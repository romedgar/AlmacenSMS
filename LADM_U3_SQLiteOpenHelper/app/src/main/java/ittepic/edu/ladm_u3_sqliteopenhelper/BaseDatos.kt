package ittepic.edu.ladm_u3_sqliteopenhelper

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
        /*Se invoca automáticamente tras la instalación de la APP en el CEL
        y constuye la estructura de la BASE DE DATOS (tablas y relaciones)
        */
        db.execSQL("CREATE TABLE PERSONA ( ID INTEGER PRIMARY KEY AUTOINCREMENT, NOMBRE VARCHAR(200), DOMICILIO VARCHAR(200), TELEFONO VARCHAR(50) )")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        /*Se invoca tras un cambio de versión de la BD
        En la versión de BD se usan números naturales, al haber dif
        */
    }
}