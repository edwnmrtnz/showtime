package com.github.edwnmrtnz.showtime.app.helper.presenter

import java.util.LinkedList

abstract class TestView<State> : ScreenView<State> {
    private val states: LinkedList<State> = LinkedList()

    override fun render(state: State) {
        states.add(state)
    }

    fun state() = states.peekLast()

    fun first() = states.peekFirst()

    fun statesAsList() = states.toList()

    fun count() = states.size
}
