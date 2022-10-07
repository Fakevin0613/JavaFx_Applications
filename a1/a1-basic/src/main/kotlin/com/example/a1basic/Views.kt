package com.example.a1basic

import javafx.beans.InvalidationListener
import javafx.beans.Observable
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.control.ScrollPane
import javafx.scene.layout.BorderPane
import javafx.stage.Stage

class Views(stage: Stage) : BorderPane(), InvalidationListener {
    var thetop: ToolBars = ToolBars()
    var center1: ScrollPane
    var center2: ScrollPane
    lateinit var bottomView: Label
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
        val note = if(Model.getNotes().size == 1) "note" else "notes"
        val noteLength = Model.getNotes().size

        var archivedNum = 0
        for(thenote in Model.getNotes()){
            if(thenote.second){
                archivedNum++
            }
        }
        val isare = if(archivedNum == 1) "is" else "are"
        bottomView = Label("$noteLength $note, $archivedNum of which $isare active")
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