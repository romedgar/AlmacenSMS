package ittepic.edu.ladm_u2_ejercicio7b

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import kotlin.random.Random

class Circulo(lienzo : Lienzo) {
    val lienzo = lienzo
    var x = 0f
    var y = 0f
    var movX = 0f
    var movY = 0f
    var color = Color.BLACK

    init {
        x = rand(1000)
        y = rand(1900)
        movX = rand(6)+2
        movY = rand(6)+2
        if(rand(100)<50f){
            movX *=-1
        }
        if(rand(100)<50f){
            movY *=-1
        }

        color = Color.rgb(rand(255).toInt(),rand(255).toInt(),rand(255).toInt())
    }

    private fun rand(hasta:Int) : Float {
        return Random.nextInt(hasta).toFloat()
    }

    fun mover(){
        x+=movX
        y+=movY

        if(x<0||x>lienzo.width){
            movX *=-1
        }
        if(y<0||y>lienzo.height){
            movY *=-1
        }
    }

    fun aumentarVel(){
        if(movY<0){
            movY-=1
        }else movY+=1

        if(movX<0){
            movX-=1
        }else movX+=1
    }
    fun disminVel(){
        if(movY<0){
            movY+=1
        }else movY-=1
        if(movX<0){
            movX+=1
        }else movX-=1
    }

    fun pintar(canvas : Canvas){
        var p = Paint()
        p.color= color
        canvas.drawCircle(x,y,50f,p)
    }
}