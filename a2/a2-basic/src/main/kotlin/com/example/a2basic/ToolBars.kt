package com.example.a2basic

import javafx.beans.InvalidationListener
import javafx.beans.Observable
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.*
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority


class ToolBars() : HBox() , InvalidationListener {
    init {
        Model.addListener(this)
        invalidated(null)
    }

    override fun invalidated(observable: Observable?) {
        children.clear()
        val keySet: ObservableList<String> = FXCollections.observableArrayList()
        for(key in Model.getAlls()){
            keySet.add(key.first)
        }
        val myDrop = ChoiceBox(FXCollections.observableArrayList(keySet))
        myDrop.prefWidth = 150.0
        myDrop.selectionModel.select(Model.getDataNum())
        myDrop.selectionModel.selectedItemProperty().addListener {
                _, _, newValue ->
            Model.changeDataNum(newValue)
        }

        val divider1 = Separator()

        val myText = TextField()
        myText.promptText = "Data set name"
        myText.textProperty().addListener {_, _, newValue ->
            myText.text = newValue
        }

        val inputBox = HBox(myText)

        val buttonSubmit = Button("Create")
        buttonSubmit.onAction = EventHandler {
            if(myText.text != "") {
                Model.addSet(Pair(myText.text, mutableListOf(0.0)))
                Model.changeDataNum(myText.text)
            }
        }

        val divider2 = Separator()

        val buttonLine = Button("Line")
        buttonLine.maxWidth = 75.0
        setHgrow(buttonLine, Priority.ALWAYS)
        buttonLine.onAction = EventHandler {
            Model.changeViewNumber(1)
        }

        val buttonBar = Button("Bar")
        buttonBar.maxWidth = 75.0
        setHgrow(buttonBar, Priority.ALWAYS)
        buttonBar.onAction = EventHandler {
            Model.changeViewNumber(2)
        }

        val buttonSEM = Button("Bar(SEM)")
        buttonSEM.maxWidth = 75.0
        setHgrow(buttonSEM, Priority.ALWAYS)
        buttonSEM.onAction = EventHandler {
            Model.changeViewNumber(3)
        }

        val buttonPie = Button("Pie")
        buttonPie.maxWidth = 75.0
        setHgrow(buttonPie, Priority.ALWAYS)
        buttonPie.onAction = EventHandler {
            Model.changeViewNumber(4)
        }


        val toolLeft = HBox()
        toolLeft.apply {
            padding = Insets(0.0,15.0,0.0,0.0)
            alignment = Pos.CENTER
        }

        val toolCenter = HBox()
        toolCenter.apply {
            padding = Insets(0.0,15.0,0.0,15.0)
            alignment = Pos.CENTER
        }

        val toolRight = HBox()
        toolRight.apply {
            padding = Insets(0.0,0.0,0.0,15.0)
            alignment = Pos.CENTER_LEFT
        }



        toolCenter.children.addAll(inputBox, buttonSubmit)
        toolLeft.children.addAll(myDrop)
        toolRight.children.addAll(buttonLine, buttonBar, buttonSEM, buttonPie)
        val toolPane = ToolBar()
        toolPane.items.addAll(toolLeft,
            divider1,
            toolCenter,
            divider2,
            toolRight)
        toolPane.apply {
            padding = Insets(0.0,0.0,1.5,0.0)
            setHgrow(this, Priority.ALWAYS)
        }
        toolRight.apply {
            setHgrow(this, Priority.ALWAYS)
        }

        children.addAll(toolPane)
    }
}