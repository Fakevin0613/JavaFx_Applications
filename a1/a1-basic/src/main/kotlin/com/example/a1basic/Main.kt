package com.example.a1basic

import javafx.application.Application
import javafx.scene.Scene
import javafx.stage.Stage


class Main : Application(){

    override fun start(stage: Stage) {
        Views(stage)


        stage.title = "CS349 - A1 Notes - q4ke"
        stage.show()
        stage.apply { minWidth = 640.0; minHeight = 480.0}
    }

}

fun main() {
    Application.launch(Main::class.java)
}
