package ui.assignments.connectfour

import javafx.application.Application
import javafx.scene.Scene
import javafx.stage.Stage
import ui.assignments.connectfour.ui.Main

class ConnectFourApp : Application() {
    override fun start(stage: Stage) {
        Main(stage)
        stage.title = "CS349 - A3 Connect Four - q4ke"
        stage.isResizable = false
        stage.show()
    }
}