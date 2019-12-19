package app.itsyour.chakra.android.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.itsyour.chakra.android.app.network.models.StagingEnv
import app.itsyour.chakra.android.app.network.models.UserSessionState
import app.itsyour.chakra.android.feature.login.models.LoginApi
import app.itsyour.chakra.android.feature.login.models.LoginInteractor
import dagger.Binds
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named

@dagger.Module
abstract class LoginModule {

    @dagger.Module
    companion object {
        @Provides
        @JvmStatic @LoginScope
        fun loginApi(@Named("Unauthenticated") client: OkHttpClient, builder: Retrofit.Builder) : LoginApi =
            builder
                .client(client)
                .baseUrl(StagingEnv.baseUrl)
                .build()
                .create<LoginApi>(LoginApi::class.java)

        @Provides
        @JvmStatic @LoginScope
        fun loginInteractor(loginApi: LoginApi) : LoginInteractor = LoginInteractor(loginApi)
    }

    @Binds
    internal abstract fun bindViewModelFactory(factory: LoginViewModelFactory): ViewModelProvider.Factory

    class LoginViewModelFactory
    @Inject constructor(
        private val loginUseCase: LoginInteractor,
        private val userSessionState: UserSessionState) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T
                = LoginViewModel(loginUseCase,userSessionState) as T }
}

