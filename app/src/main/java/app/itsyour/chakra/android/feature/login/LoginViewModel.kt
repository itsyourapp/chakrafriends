package app.itsyour.chakra.android.feature.login

import androidx.lifecycle.ViewModel
import app.itsyour.chakra.android.app.network.models.UserSessionState
import app.itsyour.chakra.android.feature.login.models.LoginInteractor
import app.itsyour.chakra.android.feature.login.models.LoginRequest
import app.itsyour.chakra.android.feature.login.models.LoginResponse
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class LoginViewModel
    @Inject constructor(
        private val interactor: LoginInteractor,
        private val userSessionState: UserSessionState)
            : ViewModel() {

    private val subscriptions = CompositeDisposable()

    private val relay = PublishRelay.create<LoginContract.UiModel>()
    val uiModels: Observable<LoginContract.UiModel>
            = relay.startWith(LoginContract.UiModel.State.Ready)

    fun onAction(action: LoginContract.Action) {
        when (action) {
            is LoginContract.Action.Login -> login(action)
        }
    }

    fun login(action: LoginContract.Action.Login) {
        subscriptions += interactor.login(LoginRequest(action.email, action.password))
            .subscribeOn(Schedulers.io())
            .delaySubscription(1500, TimeUnit.MILLISECONDS)
            .flatMap(::onResponse)
            .startWith(LoginContract.UiModel.State.Loading)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(relay::accept, this::onError)
    }

    private fun onResponse(response: LoginResponse): Observable<LoginContract.UiModel> {
        userSessionState.accessToken = response.accessToken
        return Observable.just(LoginContract.UiModel.State.Success)
    }

    private fun onError(error: Throwable) {
        relay.accept(LoginContract.UiModel.State.Error(error.localizedMessage?:""))
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.clear()
    }
}