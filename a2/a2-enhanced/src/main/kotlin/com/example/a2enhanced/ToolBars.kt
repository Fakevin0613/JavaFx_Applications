package com.example.a2enhanced

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
        myDrop.prefWidth = 100.0
        myDrop.selectionModel.select(Model.getDataNum())
        myDrop.selectionModel.selectedItemProperty().addListener {
                _, _, newValue ->
            Model.changeDataNum(newValue)
        }

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


        val buttonLine = Button("Line")
        buttonLine.maxWidth = 60.0
        setHgrow(buttonLine, Priority.ALWAYS)
        buttonLine.onAction = EventHandler {
            Model.changeViewNumber(1)
        }
        if(Model.getViewNumber() == 1){
            buttonLine.isDisable
        }

        val buttonBar = Button("Bar")
        buttonBar.maxWidth = 65.0
        setHgrow(buttonBar, Priority.ALWAYS)
        buttonBar.onAction = EventHandler {
            Model.changeViewNumber(2)
        }

        val buttonSEM = Button("BarSEM")
        buttonSEM.maxWidth = 65.0
        setHgrow(buttonSEM, Priority.ALWAYS)
        buttonSEM.onAction = EventHandler {
            Model.changeViewNumber(3)
        }

        val buttonPie = Button("Pie")
        buttonPie.maxWidth = 65.0
        setHgrow(buttonPie, Priority.ALWAYS)
        buttonPie.onAction = EventHandler {
            Model.changeViewNumber(4)
        }

        val buttonAll = Button("ALL")
        buttonAll.maxWidth = 65.0
        setHgrow(buttonAll, Priority.ALWAYS)
        buttonAll.onAction = EventHandler {
            Model.changeViewNumber(5)
        }

        if(Model.getHasNegative()){
            buttonSEM.isDisable = true
            buttonPie.isDisable = true
        }
        else{
            buttonSEM.isDisable = false
            buttonPie.isDisable = false
        }

        if(Model.getViewNumber() == 1){
            buttonLine.isDisable = true
        }
        else if(Model.getViewNumber() == 2){
            buttonBar.isDisable = true
        }
        else if(Model.getViewNumber() == 3){
            buttonSEM.isDisable = true
        }
        else if(Model.getViewNumber() == 4){
            buttonPie.isDisable = true
        }
        else if(Model.getViewNumber() == 5){
            buttonAll.isDisable = true
        }


        val colorScheme = ChoiceBox(
            FXCollections.observableArrayList("BlackWhite", "Colorful1", "Colorful2"))
        colorScheme.prefWidth = 100.0
        colorScheme.selectionModel.select(Model.getColor())
        colorScheme.selectionModel.selectedItemProperty().addListener {
                _, _, newValue ->
            if(newValue == "BlackWhite"){
                Model.changeColor(0)
            }
            else if(newValue == "Colorful1"){
                Model.changeColor(1)
            }
            else if(newValue == "Colorful2"){
                Model.changeColor(2)
            }
        }


        val toolLeft = HBox()
        toolLeft.apply {
            padding = Insets(0.0,10.0,0.0,0.0)
            alignment = Pos.CENTER
        }

        val toolCenter = HBox()
        toolCenter.apply {
            padding = Insets(0.0,10.0,0.0,10.0)
            alignment = Pos.CENTER
        }

        val toolCenter2 = HBox()
        toolCenter2.apply {
            padding = Insets(0.0,10.0,0.0,10.0)
            alignment = Pos.CENTER
        }

        val toolRight = HBox()
        toolRight.apply {
            padding = Insets(0.0,0.0,0.0,10.0)
            alignment = Pos.CENTER_LEFT
        }



        toolCenter.children.addAll(inputBox, buttonSubmit)
        toolLeft.children.addAll(myDrop)
        toolCenter2.children.addAll(colorScheme)
        toolRight.children.addAll(buttonLine, buttonBar, buttonSEM, buttonPie, buttonAll)
        val toolPane = ToolBar()
        toolPane.items.addAll(toolLeft,
            toolCenter,
            toolCenter2,
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