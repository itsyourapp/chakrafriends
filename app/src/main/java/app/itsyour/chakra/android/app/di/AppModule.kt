package app.itsyour.chakra.android.app.di

import android.app.Application
import android.content.Context
import androidx.preference.PreferenceManager
import app.itsyour.chakra.android.BuildConfig
import app.itsyour.chakra.android.app.di.scope.AppScoped
import app.itsyour.chakra.android.app.network.CacheInterceptor
import app.itsyour.chakra.android.app.network.models.UserSessionState
import app.itsyour.chakra.android.feature.login.LoginActivity
import app.itsyour.chakra.android.feature.login.LoginModule
import app.itsyour.chakra.android.feature.login.LoginScope
import app.itsyour.chakra.android.feature.main.MainActivity
import app.itsyour.chakra.android.feature.main.MainModule
import app.itsyour.chakra.android.feature.main.MainScope
import com.f2prateek.rx.preferences2.RxSharedPreferences
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Named

@Module
abstract class AppModule {

    @Binds
    abstract fun bindContext(application: Application): Context

    @LoginScope
    @ContributesAndroidInjector(modules = [LoginModule::class])
    abstract fun loginActivity(): LoginActivity

    @MainScope
    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun mainActivity(): MainActivity

    @dagger.Module
    companion object {
        @AppScoped
        @Provides @JvmStatic
        fun rxSharedPreferences(context: Context): RxSharedPreferences
                = RxSharedPreferences.create(PreferenceManager.getDefaultSharedPreferences(context))

        @AppScoped
        @Provides @JvmStatic
        fun gson(): Gson
                = GsonBuilder().setDateFormat("yyyy-MM-dd").create()

        @AppScoped
        @Provides @JvmStatic
        fun rxJavaCallAdapterFactory(): RxJava2CallAdapterFactory
                = RxJava2CallAdapterFactory.createAsync()

        @AppScoped
        @Provides @JvmStatic
        fun gsonConverterFactory(gson: Gson): GsonConverterFactory
                = GsonConverterFactory.create(gson)

        @AppScoped
        @Provides @JvmStatic
        fun cache(context: Context): Cache {
            val httpCacheDirectory = File(context.cacheDir, "responses")
            val cacheSize = 10 * 1024 * 1024 // 10 MiB
            return Cache(httpCacheDirectory, cacheSize.toLong())
        }

        @AppScoped
        @Provides @JvmStatic
        fun retrofitBuilder(converterFactory: GsonConverterFactory, rxFactory: RxJava2CallAdapterFactory): Retrofit.Builder {
            return Retrofit.Builder()
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(rxFactory)
        }

        @AppScoped
        @Provides @JvmStatic
        fun httpClientBuilder(cache: Cache): OkHttpClient.Builder {
            val builder = OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .addNetworkInterceptor(CacheInterceptor())
                .cache(cache)

            if (BuildConfig.DEBUG) {
                builder.addNetworkInterceptor(StethoInterceptor())
                @Suppress("ConstantConditionIf")
                if ("debug" == BuildConfig.BUILD_TYPE) {
                    val httpLoggingInterceptor = HttpLoggingInterceptor()
                    builder.addNetworkInterceptor(
                        httpLoggingInterceptor.apply {
                            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                        })
                }
            }
            return builder
        }

        @AppScoped @Named("Unauthenticated")
        @Provides @JvmStatic
        fun httpClient(builder: OkHttpClient.Builder): OkHttpClient =
            builder.build()

        @AppScoped
        @Provides @JvmStatic
        fun userSessionState() = UserSessionState(null)

//        @Provides
//        @JvmStatic @AppScoped
//        fun loginApi(@Named("Unauthenticated") client: OkHttpClient, builder: Retrofit.Builder) : LoginApi =
//            builder
//                .client(client)
//                .baseUrl(StagingEnv.loginBase)
//                .build()
//                .create<LoginApi>(LoginApi::class.java)
    }
}