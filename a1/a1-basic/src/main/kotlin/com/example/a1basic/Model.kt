package com.example.a1basic

import javafx.beans.InvalidationListener
import javafx.beans.Observable

object Model : Observable {
    private val listeners = mutableListOf<InvalidationListener?>()
    private var notes : MutableList<Pair<String, Boolean>> = mutableListOf()

    init {
        notes.add(Pair("Note Simple", false))
        notes.add(Pair("Notelonggggggggggggggggggggggggggggggggggggggggggggggggggggggggggg" +
                "ggggggggggggggggggggggggggggggggggggggggggggggggg", false))
        notes.add(Pair("Note Archived", true))
        notes.add(Pair("Note high \na\na\na\na\na\na\na\n", false))
    }

    fun clearNotes() {
        notes.clear()
        listeners.forEach {it?.invalidated(this)}
    }

    fun addNotes(note: Pair<String, Boolean>){
        notes.add(note)
        listeners.forEach {it?.invalidated(this)}
    }

    fun getNotes(): MutableList<Pair<String, Boolean>> {
        return notes
    }

    override fun addListener(listener: InvalidationListener?) {
        listeners.add(listener)
    }

    override fun removeListener(listener: InvalidationListener?) {
        listeners.remove(listener)
    }

}