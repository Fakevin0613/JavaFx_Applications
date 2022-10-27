package com.example.a2basic

import javafx.beans.InvalidationListener
import javafx.beans.Observable

object Model: Observable {
    private val listeners = mutableListOf<InvalidationListener?>()
    private var viewNumber = 1
    private var dataNumber = 0
    private var lastFocus = 0
    private var datas : MutableList<Pair<String, MutableList<Double>>> = mutableListOf()

    init {
        datas.add(Pair("test1", mutableListOf(1.0,2.0,3.0,4.0)  ))
        datas.add(Pair("test2", mutableListOf(10.0,4.0,3.9,4.3,2.2)  ))
    }

    fun getViewNumber(): Int {
        return viewNumber
    }

    fun changeViewNumber(num: Int){
        viewNumber = num
        listeners.forEach {it?.invalidated(this)}
    }

    fun getLastFocus(): Int {
        return lastFocus
    }

    fun getDataNum(): Int {
        return dataNumber
    }

    fun changeDataNum(str: String){
            val length = datas.size - 1
            for(i in 0..length){
                if(str == datas[i].first){
                    dataNumber = i
                }
            }
            listeners.forEach {it?.invalidated(this)}
    }

    fun getAlls(): MutableList<Pair<String, MutableList<Double>>> {
        return datas
    }

    fun addSet(set : Pair<String, MutableList<Double>>) {
        datas.add(set)
        listeners.forEach {it?.invalidated(this)}
    }

    fun addData(name:String, number: Double) {
        var list : MutableList<Double>
        for(data in datas){
            if(data.first == name){
                list = data.second
                list.add(number)
                lastFocus = list.size - 1
            }
        }

        listeners.forEach {it?.invalidated(this)}
    }

    fun deleteData(name:String, number: Int) {
        var list : MutableList<Double>?
        for(data in datas) {
            if (data.first == name) {
                list = data.second
                list.removeAt(number)
            }
        }
        listeners.forEach {it?.invalidated(this)}
    }

    fun modifyData(name:String, number: Int, thedata: Double) {
        var list : MutableList<Double>?
        for(data in datas) {
            if (data.first == name) {
                list = data.second
                list[number] = thedata
            }
        }
        lastFocus = number
        listeners.forEach {it?.invalidated(this)}
    }

    override fun addListener(listener: InvalidationListener?) {
        listeners.add(listener)
    }

    override fun removeListener(listener: InvalidationListener?) {
        listeners.remove(listener)
    }
}