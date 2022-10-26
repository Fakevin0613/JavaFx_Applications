package com.example.a2basic

import javafx.beans.InvalidationListener
import javafx.beans.Observable
import javafx.scene.Scene
import javafx.scene.layout.BorderPane
import javafx.stage.Stage

class View(stage: Stage) : BorderPane(), InvalidationListener {
    var thetop: ToolBars = ToolBars()
    var thecenter: Middle = Middle()

    init {
        stage.scene = Scene(this, 800.0, 600.0)
        Model.addListener(this)
        invalidated(null)
    }

    override fun invalidated(observable: Observable?) {
        this.center = thecenter
        this.top = thetop
    }

}