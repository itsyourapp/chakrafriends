package app.itsyour.chakra.android.app.di

import android.app.Application
import app.itsyour.chakra.android.ChakraFriendsApp
import app.itsyour.chakra.android.app.di.scope.AppScoped
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule

@AppScoped
@Component(modules = [
    AppModule::class,
    AndroidSupportInjectionModule::class])
interface AppComponent : AndroidInjector<DaggerApplication> {

    fun inject(application: ChakraFriendsApp)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }
}
