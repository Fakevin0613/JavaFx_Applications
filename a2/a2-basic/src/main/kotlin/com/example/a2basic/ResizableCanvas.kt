package com.example.a2basic

import javafx.beans.InvalidationListener
import javafx.beans.Observable
import javafx.scene.canvas.Canvas
import javafx.scene.paint.Color


class ResizableCanvas() : Canvas(), InvalidationListener {
    init {
        Model.addListener(this)
        invalidated(null)
        widthProperty().addListener { _: Observable? -> draw() }
        heightProperty().addListener { _: Observable? -> draw() }
    }

    private fun draw() {
        val width = width
        val height = height
        val gc = graphicsContext2D
        gc.clearRect(0.0, 0.0, width, height)
        if(Model.getViewNumber() == 1){
            gc.stroke = Color.RED
            gc.strokeLine(0.0, 0.0, width, height)
            gc.strokeLine(0.0, height, width, 0.0)
        }
        else if(Model.getViewNumber() == 2){
            gc.stroke = Color.GREEN
            gc.strokeLine(0.0, 0.0, width, height)
            gc.strokeLine(0.0, height, width, 0.0)
        }
        else if(Model.getViewNumber() == 3){
            gc.stroke = Color.BLUE
            gc.strokeLine(0.0, 0.0, width, height)
            gc.strokeLine(0.0, height, width, 0.0)
        }
        else if(Model.getViewNumber() == 4){
            gc.stroke = Color.BLACK
            gc.strokeLine(0.0, 0.0, width, height)
            gc.strokeLine(0.0, height, width, 0.0)
        }

    }

    override fun isResizable(): Boolean {
        return true
    }

    override fun invalidated(observable: Observable?) {
        draw()
    }
}