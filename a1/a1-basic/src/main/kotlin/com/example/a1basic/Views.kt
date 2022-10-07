package com.example.a1basic

import javafx.beans.InvalidationListener
import javafx.beans.Observable
import javafx.scene.Scene
import javafx.scene.control.ScrollPane
import javafx.scene.layout.BorderPane
import javafx.stage.Stage

class Views(stage: Stage) : BorderPane(), InvalidationListener {
    var thetop: ToolBars = ToolBars()
    var center1: ScrollPane
    var center2: ScrollPane
    init {
        stage.scene = Scene(this, 800.0, 600.0)
        val listView = LView(thetop.widthProperty().subtract(125))
        val gridView = GView(thetop.widthProperty())

        center1 = ScrollPane(listView).apply {
            hbarPolicy = ScrollPane.ScrollBarPolicy.NEVER
        }
        center2 = ScrollPane(gridView).apply {
            hbarPolicy = ScrollPane.ScrollBarPolicy.NEVER
        }
        Model.addListener(this)
        invalidated(null)
    }

    override fun invalidated(observable: Observable?) {
        this.top = thetop
        when (Model.getViewNumber()) {
            1 -> {
                this.center = center1
            }
            2 -> {
                this.center = center2
            }
        }
    }
}