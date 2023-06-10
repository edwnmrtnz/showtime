package com.github.edwnmrtnz.showtime.ui.helpers.presenter

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

abstract class StatefulPresenter<State, View : ScreenView<State>> {

    internal object Config {
        var scope = MainScope()
    }

    companion object {
        fun setScope(scope: CoroutineScope) {
            Config.scope = scope
        }
    }

    protected val scope = Config.scope
    private var hasBind = false
    private val firstState by lazy { initialState() }
    private var latestState = firstState
    private var view: View? = null

    fun bind(view: View) {
        this.view = view
        view.render(latestState)

        val isFirstTime = !hasBind
        hasBind = true

        onStarted(latestState)
        if (isFirstTime) onCreated(latestState)
    }

    fun unbind() {
        view = null
        onStopped()
    }

    fun getCurrentState() = this.latestState

    abstract fun initialState(): State

    protected fun render(newState: (current: State) -> State) {
        this.latestState = newState(latestState)
        view?.render(latestState)
    }

    protected open fun onStarted(state: State) {}
    protected open fun onCreated(state: State) {}
    protected open fun onStopped() {}
}
