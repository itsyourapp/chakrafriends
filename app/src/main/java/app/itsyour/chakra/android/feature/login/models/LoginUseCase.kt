package app.itsyour.chakra.android.feature.login.models

import io.reactivex.Observable
import javax.inject.Inject

class LoginUseCase
    @Inject constructor(
        private val loginApi: LoginApi) {

    fun login(request: LoginRequest): Observable<LoginResponse>
            = loginApi.login(request)
}
