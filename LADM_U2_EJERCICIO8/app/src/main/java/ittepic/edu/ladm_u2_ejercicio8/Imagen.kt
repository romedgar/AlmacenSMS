package ittepic.edu.ladm_u2_ejercicio8

import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint


class Imagen(lienzo : Lienzo, img : String) {
    val lienzo = lienzo
    var x = 0f
    var y = 0f
    var img = img
    var ruta =""
    //var ruta = BitmapFactory.decodeResource(lienzo.resources,R.drawable.pika)

    init {
        ruta = BitmapFactory.decodeResource(lienzo.resources,R.drawable.img)

    }

    fun pintar(canvas : Canvas){
        var p = Paint()
        canvas.drawBitmap(BitmapFactory.decodeResource(lienzo.resources,R.drawable.pika))
    }
}