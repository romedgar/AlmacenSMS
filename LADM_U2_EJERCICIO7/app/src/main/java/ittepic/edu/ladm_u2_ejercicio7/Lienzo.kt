package ittepic.edu.ladm_u2_ejercicio7

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View

class Lienzo(este: MainActivity) : View(este) {
    val este = este
    val circulos = Array<Circulos>(20, {Circulos(this@Lienzo)})

    override fun onDraw(c: Canvas) {
        super.onDraw(c)
        var p = Paint()

        circulos.forEach {
            when (it.color) {
                1 -> p.color = Color.BLUE
                2 -> p.color = Color.GRAY
                3 -> p.color = Color.GREEN
                4 -> p.color = Color.RED
                5 -> p.color = Color.YELLOW
                6 -> p.color = Color.BLACK
            }
            c.drawCircle(it.x.toFloat(),it.y.toFloat(),30f,p)
        }

    }
}