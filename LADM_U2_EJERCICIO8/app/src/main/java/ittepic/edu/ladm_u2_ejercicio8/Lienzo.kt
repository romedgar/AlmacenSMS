package ittepic.edu.ladm_u2_ejercicio8

import android.content.res.Resources
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.view.View

class Lienzo (este: MainActivity) : View(este) {
    override fun onDraw(c: Canvas) {
        super.onDraw(c)
        var p = Paint()
        c.drawBitmap(BitmapFactory.decodeResource(this.resources,R.drawable.pika),400f,400f,p)
    }

}