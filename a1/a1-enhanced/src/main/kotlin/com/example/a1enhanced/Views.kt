package com.example.a1enhanced

import javafx.beans.InvalidationListener
import javafx.beans.Observable
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.control.ScrollPane
import javafx.scene.layout.BorderPane
import javafx.stage.Stage

class Views(stage: Stage) : BorderPane(), InvalidationListener {
    private var thetop: ToolBars = ToolBars()
    private var center1: ScrollPane
    private var center2: ScrollPane
    private lateinit var bottomView: Label
    init {
        stage.scene = Scene(this, 800.0, 600.0)
        val listView = LView(thetop.widthProperty().subtract(135))
        val gridView = GView(thetop.widthProperty().subtract(10))

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
        val note = if(Model.getNotes().size == 1) "note" else "notes"
        val noteLength = Model.getNotes().size

        var activeNum = 0
        for(thenote in Model.getNotes()){
            if(thenote.second == 0 || thenote.second == 2){
                activeNum++
            }
        }
        val isare = if(activeNum == 1) "is" else "are"
        bottomView = Label("$noteLength $note, $activeNum of which $isare active")
        bottomView.padding = Insets(5.0)
        this.top = thetop
        this.bottom = bottomView
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