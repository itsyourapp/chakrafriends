package app.itsyour.chakra.android.feature.login.models

import app.itsyour.chakra.android.app.network.CacheInterceptor
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface LoginApi {
    @POST("login")
    @Headers(CacheInterceptor.NO_CACHE_HEADER)
    fun login(@Body loginRequest: LoginRequest): Observable<LoginResponse>
}