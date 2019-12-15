package app.itsyour.chakra.android.feature.login.cases

import app.itsyour.chakra.android.feature.login.models.LoginApi
import app.itsyour.chakra.android.feature.login.models.LoginRequest
import app.itsyour.chakra.android.feature.login.models.LoginResponse
import io.reactivex.Observable
import javax.inject.Inject

class LoginUseCase
    @Inject constructor(
        private val loginApi: LoginApi) {

    fun login(request: LoginRequest): Observable<LoginResponse>
            = loginApi.login(request)
}
