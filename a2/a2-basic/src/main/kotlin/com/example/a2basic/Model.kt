package com.example.a2basic

import javafx.beans.InvalidationListener
import javafx.beans.Observable

object Model: Observable {
    private val listeners = mutableListOf<InvalidationListener?>()
    private var viewNumber = 1
    private var dataNumber = 0
    private var lastFocus = 0
    private var hasNegative = true
    private var datas : MutableList<Pair<String, MutableList<Double>>> = mutableListOf()

    init {
        datas.add(Pair("test1", mutableListOf(-1.0, 2.0, -3.0, 4.0, -5.0, 6.0, -7.0, 8.0, -9.0)  ))
        datas.add(Pair("test2", mutableListOf(10.0,4.0,3.9,4.3,2.2)  ))
        datas.add(Pair("quadratic", mutableListOf(0.1, 1.0, 4.0, 9.0, 16.0)  ))
        datas.add(Pair("negative quadratic", mutableListOf(-0.1, -1.0, -4.0, -9.0, -16.0)  ))
        datas.add(Pair("inflation '90-'22",
            mutableListOf(4.8, 5.6, 1.5, 1.9, 0.2, 2.1, 1.6, 1.6, 1.0, 1.7, 2.7, 2.5, 2.3,
                2.8, 1.9, 2.2, 2.0, 2.1, 2.4, 0.3, 1.8, 2.9, 1.5, 0.9, 1.9, 1.1, 1.4, 1.6, 2.3, 1.9, 0.7, 3.4, 6.8)  ))
    }
    private fun toTrueHasNegative(){
        hasNegative = true
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
        if(!hasNegative){
            viewNumber = 1
        }
        hasNegative = false
        for(nums in datas[dataNumber].second){
            if(nums < 0.0){
                toTrueHasNegative()
            }
        }

        listeners.forEach {it?.invalidated(this)}
    }

    fun getHasNegative(): Boolean {
        return hasNegative
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
        if(!hasNegative){
            viewNumber = 1
        }
        if(number < 0.0){
            toTrueHasNegative()
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
        if(!hasNegative){
            viewNumber = 1
        }
        hasNegative = false
        for(nums in datas[dataNumber].second){
            if(nums < 0.0){
                toTrueHasNegative()
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
        if(!hasNegative){
            viewNumber = 1
        }
        hasNegative = false
        for(nums in datas[dataNumber].second){
            if(nums < 0.0){
                toTrueHasNegative()
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