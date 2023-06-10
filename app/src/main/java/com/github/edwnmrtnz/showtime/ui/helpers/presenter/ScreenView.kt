package com.github.edwnmrtnz.showtime.ui.helpers.presenter

interface ScreenView<State> {
    fun render(state: State)
}
