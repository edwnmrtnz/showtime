package com.github.edwnmrtnz.showtime.app.helper.presenter

interface ScreenView<State> {
    fun render(state: State)
}
