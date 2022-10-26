package com.example.a2basic

import javafx.scene.control.ScrollPane
import javafx.scene.control.SplitPane

class Middle(): SplitPane() {
    init {
        val left = ScrollPane().apply {
            vbarPolicy = ScrollPane.ScrollBarPolicy.ALWAYS
            hbarPolicy = ScrollPane.ScrollBarPolicy.NEVER
        }
        left.content = DataEntry(left.widthProperty().subtract(20))
        val right = ScrollPane().apply {
            vbarPolicy = ScrollPane.ScrollBarPolicy.ALWAYS
            hbarPolicy = ScrollPane.ScrollBarPolicy.NEVER
        }
        right.content = DataEntry(right.widthProperty().subtract(20))
        this.items.addAll(left, right)
    }
}