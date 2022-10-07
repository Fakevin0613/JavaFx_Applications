package com.example.a1enhanced

import javafx.beans.InvalidationListener
import javafx.beans.Observable

object Model : Observable {
    private val listeners = mutableListOf<InvalidationListener?>()
    private var notes : MutableList<Pair<String, Int>> = mutableListOf()
    private var showArchived: Boolean = false
    private var ascend: String = "Length (asc)"
    private var viewNumber = 1

    init {
        notes.add(Pair("Note Simple", 0))
        notes.add(Pair("Note adfadsfadfasdfadsfadsfadsfasdf", 0))
        notes.add(Pair("Notelonggggggggggggggggafdadfdafadfadsfadfadfadsfadsfadsfad" +
                "faasdfadfasdfadsfadsfadsfadfadfadsfadfadsfdafad", 2))
        notes.add(Pair("Note Archiveadfadfdafdafdadadfadfadfadfadfadfadsfa" +
                "adfadfadfasdfadsfadsfasfasfas", 1))
        notes.add(Pair("Note high \na\na\na\na\na\na\na", 0))
        updateAscend()
    }

    fun getViewNumber(): Int {
        return viewNumber
    }

    fun changeViewNumber(num: Int){
        viewNumber = num
        listeners.forEach {it?.invalidated(this)}
    }

    private fun updateAscend(){
        if(ascend == "Length (asc)"){
            notes.sortBy { it.first.length }
        }
        else if(ascend == "Length (desc)"){
            notes.sortByDescending { it.first.length }
        }
    }

    fun clearNotes() {
        notes.clear()
        updateAscend()
        listeners.forEach {it?.invalidated(this)}
    }

    fun addNotes(note: Pair<String, Int>){
        notes.add(note)
        updateAscend()
        listeners.forEach {it?.invalidated(this)}
    }

    fun changeArchived(index: Int, note: Pair<String, Int>){
        notes[index] = note
        updateAscend()
        listeners.forEach {it?.invalidated(this)}
    }

    fun getNotes(): MutableList<Pair<String, Int>> {
        return notes
    }

    fun getShowArchived(): Boolean {
        return showArchived
    }

    fun changeShowArchived(boolean: Boolean){
        showArchived = boolean
        updateAscend()
        listeners.forEach {it?.invalidated(this)}
    }

    fun changeAscend(string: String){
        ascend = string
        updateAscend()
        listeners.forEach {it?.invalidated(this)}
    }

    override fun addListener(listener: InvalidationListener?) {
        listeners.add(listener)
    }

    override fun removeListener(listener: InvalidationListener?) {
        listeners.remove(listener)
    }

}