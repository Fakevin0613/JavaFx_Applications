package com.example.a2basic

import javafx.beans.InvalidationListener
import javafx.beans.Observable
import javafx.scene.canvas.Canvas
import javafx.scene.paint.Color
import javafx.scene.shape.ArcType
import javafx.scene.shape.StrokeLineCap
import javafx.scene.text.Font
import kotlin.math.min
import kotlin.math.sqrt


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
        val theData = Model.getAlls()[Model.getDataNum()].second
        val hgap = width / (theData.size + 1)
        var vMax = 0.0
        var vMin = 0.0
        for(value in theData){
            if(value > vMax){
                vMax = value
            }
        }
        for(value in theData){
            if(value < vMin){
                vMin = value
            }
        }
        val vgap = vMax - vMin
        val gc = graphicsContext2D
        gc.clearRect(0.0, 0.0, width, height)
        if(Model.getViewNumber() == 1){
            gc.stroke = Color.BLACK
            for(nums in 0..theData.size-2){
                gc.strokeLine((nums+1)*hgap, (vMax - theData[nums]) / vgap * height, (nums+2)*hgap, (vMax - theData[nums+ 1]) / vgap * height)
            }
            gc.stroke = Color.RED
            for(nums in 0 until theData.size){
                gc.strokeRect((nums+1)*hgap - 1.0, (vMax - theData[nums]) / vgap * height - 1.0, 2.0, 2.0)
            }
        }
        else if(Model.getViewNumber() == 2){
            gc.stroke = Color.BLACK
            gc.strokeLine(0.0, (vMax - 0.0) / vgap * height, width, (vMax - 0.0) / vgap * height)

            val barDif = hgap / 4
            for(nums in 0 until theData.size){
                gc.fill = Color.rgb((255 - 200 * (nums+1)*hgap / width).toInt(),
                    (255 * (nums+1)*hgap / width).toInt(),
                    (255 - 255 * (nums+1)*hgap / width).toInt())
                if(theData[nums] > 0.0){
                    gc.fillRect((nums+1)*hgap - barDif, (vMax - theData[nums]) / vgap * height, 2 * barDif, (theData[nums] - 0.0) / vgap * height)
                }
                else{
                    gc.fillRect((nums+1)*hgap - barDif, (vMax - 0.0) / vgap * height, 2 * barDif, (0.0 - theData[nums]) / vgap * height)
                }

            }

        }
        else if(Model.getViewNumber() == 3){
            gc.stroke = Color.BLACK
            gc.strokeLine(0.0, (vMax - 0.0) / vgap * height, width, (vMax - 0.0) / vgap * height)

            var total = 0.0

            val barDif = hgap / 4
            for(nums in 0 until theData.size){
                gc.fill = Color.rgb((255 - 200 * (nums+1)*hgap / width).toInt(),
                    (255 * (nums+1)*hgap / width).toInt(),
                    (255 - 255 * (nums+1)*hgap / width).toInt())
                if(theData[nums] > 0.0){
                    gc.fillRect((nums+1)*hgap - barDif, (vMax - theData[nums]) / vgap * height, 2 * barDif, (theData[nums] - 0.0) / vgap * height)
                }
                else{
                    gc.fillRect((nums+1)*hgap - barDif, (vMax - 0.0) / vgap * height, 2 * barDif, (0.0 - theData[nums]) / vgap * height)
                }
                total += theData[nums]
            }

            gc.stroke = Color.BLACK
            val mean = total / theData.size
            gc.lineWidth = 3.0
            gc.strokeLine(0.0, (vMax - mean) / vgap * height, width, (vMax - mean) / vgap * height)

            var sd = 0.0
            for(nums in 0 until theData.size){
                sd += (theData[nums] - mean) * (theData[nums] - mean)
            }
            sd = sqrt(sd / theData.size)
            val sem = sd / sqrt(theData.size.toDouble()) * 2

            val plus = mean + sem / 2
            val minus = mean - sem / 2
            gc.lineWidth = 2.0
            gc.lineCap = StrokeLineCap.ROUND
            gc.setLineDashes(25.0)
            gc.strokeLine(0.0, (vMax - plus) / vgap * height, width, (vMax - plus) / vgap * height)
            gc.strokeLine(0.0, (vMax - minus) / vgap * height, width, (vMax - minus) / vgap * height)
            gc.setLineDashes(0.0)

            gc.fill = Color.BLACK
            gc.lineWidth = 1.0
            gc.font = Font.font("Console", 20.0)
            gc.fillText("mean: " + String.format("%.2g%n", mean), 20.0, 20.0, 200.0)
            gc.fillText("SEM: " + String.format("%.2g%n", sem), 20.0, 50.0, 200.0)

        }
        else if(Model.getViewNumber() == 4){
            gc.stroke = Color.BLACK
            val minHeightWidth = Math.min(height, width)
            var startAngle = 0.0
            var total = 0.0
            for(nums in 0 until theData.size){
                total += theData[nums]
            }
            for(nums in 0 until theData.size){
                val theAngle = theData[nums] / total * 360.0
                gc.fill = Color.rgb((255 * (nums+1)*hgap).toInt() % 255,
                    (255 * (nums+1)*hgap).toInt() % 255,
                    (255 * (nums+1)*hgap).toInt() % 255)
                gc.fillArc(width / 2 - (minHeightWidth - 40) / 2, height / 2 - (minHeightWidth - 40) / 2,
                    minHeightWidth - 40, minHeightWidth - 40, startAngle, theAngle, ArcType.ROUND)
                gc.strokeArc(width / 2 - (minHeightWidth - 40) / 2, height / 2 - (minHeightWidth - 40) / 2,
                    minHeightWidth - 40, minHeightWidth - 40, startAngle, theAngle, ArcType.ROUND)
                startAngle += theAngle
            }
        }

    }

    override fun isResizable(): Boolean {
        return true
    }

    override fun invalidated(observable: Observable?) {
        draw()
    }
}