package app.itsyour.chakra.android.feature.login

import app.itsyour.chakra.android.app.base.Contract

interface LoginContract : Contract {

    sealed class Action {
        class Login(val email: String, val password: String) : Action()
    }

    sealed class UiModel {
        sealed class State : UiModel() {
            object Ready : State()
            object Loading : State()
            class Error(val message: String): State()
            object Success: State()
        }
    }
}

