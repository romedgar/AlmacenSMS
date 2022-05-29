package ittepic.edu.ladm_u2_ejercicio7b

import android.graphics.Canvas
import android.graphics.Color
import android.view.MotionEvent
import android.view.View
import androidx.constraintlayout.widget.ConstraintSet
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class Lienzo(este: MainActivity) : View(este) {
    val este = este
    var fondo = Color.BLACK
    val circulos = Array<Circulo>(50,{ Circulo(this) })
    var velocidad = 20L

    val corrutina = GlobalScope.launch {
        while (true){
            este.runOnUiThread {
                invalidate()
            }
            delay(velocidad)
        }
    }

    override fun onDraw(c: Canvas) {
        super.onDraw(c)
        c.drawColor(fondo)
        for(circ in circulos){
            circ.mover()
            circ.pintar(c)
        }
        /*(0..circulos.size-1).forEach {
            circulos[it].mover()
            circulos[it].pintar(c)
        }*/

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
       // event.action // Los 3 estados del toque
        event.x
        event.y

        when(event.action){
            MotionEvent.ACTION_DOWN ->{
                //Presionar

            }
            MotionEvent.ACTION_MOVE ->{
                //Arrastrar

            }
            MotionEvent.ACTION_UP ->{
                //Soltar
                if(event.y<=300){
                    for(circ in circulos){
                        circ.aumentarVel()
                    }
                }
                else if(event.y>=1400){
                    for(circ in circulos){
                        circ.disminVel()
                    }
                }
                else fondo = Color.rgb(Random.nextInt(255),Random.nextInt(255),Random.nextInt(255))

            }
        }

        return true
    }
}