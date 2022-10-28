package com.example.a2enhanced

import javafx.application.Application
import javafx.stage.Stage

class Main : Application() {
    override fun start(stage: Stage) {
        View(stage)
        stage.title = "CS349 - A2 Graphs - q4ke"
        stage.show()
        stage.apply { minWidth = 640.0; minHeight = 480.0}
    }
}

fun main() {
    Application.launch(Main::class.java)
}