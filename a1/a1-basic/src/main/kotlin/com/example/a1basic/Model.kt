package com.example.a1basic

import javafx.beans.InvalidationListener
import javafx.beans.Observable

object Model : Observable {
    private val listeners = mutableListOf<InvalidationListener?>()
    private var notes : MutableList<Pair<String, Boolean>> = mutableListOf()
    private var showArchived: Boolean = false
    private var ascend: String = "Length (asc)"

    init {
        notes.add(Pair("Note Simple", false))
        notes.add(Pair("Notelonggggggggggggggggggggggggggggggggggggggggggggggggggggggggggg" +
                "ggggggggggggggggggggggggggggggggggggggggggggggggg", false))
        notes.add(Pair("Note Archiveadfadfdafdafdad", true))
        notes.add(Pair("Note high \na\na\na\na\na\na\na\n", false))
        updateAscend()
    }

    fun updateAscend(){
        if(ascend == "Length (asc)"){
            println(ascend)
            notes.sortBy { it.first.length }
        }
        else if(ascend == "Length (desc)"){
            println(ascend)
            notes.sortByDescending { it.first.length }
        }
    }

    fun clearNotes() {
        notes.clear()
        updateAscend()
        listeners.forEach {it?.invalidated(this)}
    }

    fun addNotes(note: Pair<String, Boolean>){
        notes.add(note)
        updateAscend()
        listeners.forEach {it?.invalidated(this)}
    }

    fun changeArchived(index: Int, note: Pair<String, Boolean>){
        notes.set(index, note)
        updateAscend()
        listeners.forEach {it?.invalidated(this)}
    }

    fun getNotes(): MutableList<Pair<String, Boolean>> {
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