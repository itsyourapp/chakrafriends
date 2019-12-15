package app.itsyour.chakra.android.feature.login

import androidx.lifecycle.ViewModel
import app.itsyour.chakra.android.app.network.models.UserSessionState
import app.itsyour.chakra.android.feature.login.models.LoginRequest
import app.itsyour.chakra.android.feature.login.models.LoginResponse
import app.itsyour.chakra.android.feature.login.models.LoginUseCase
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
        private val useCase: LoginUseCase,
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
        subscriptions += useCase.login(LoginRequest(action.email, action.password))
            .flatMap(::onResponse)
            .onErrorResumeNext(::onError)
            .delay(1000, TimeUnit.MILLISECONDS)
            .startWith(LoginContract.UiModel.State.Loading)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(relay::accept)
    }

    private fun onResponse(response: LoginResponse): Observable<LoginContract.UiModel> {
        userSessionState.accessToken = response.access_token
        return Observable.just(LoginContract.UiModel.State.Success)
    }

    private fun onError(error: Throwable): Observable<LoginContract.UiModel>
//          = Observable.just(LoginContract.UiModel.State.Error(error.localizedMessage?:""))
            = Observable.just(LoginContract.UiModel.State.Success)
}