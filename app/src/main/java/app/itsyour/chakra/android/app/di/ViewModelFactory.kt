/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package app.itsyour.chakra.android.app.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.itsyour.chakra.android.app.di.scope.AppScoped
import app.itsyour.chakra.android.feature.login.LoginViewModel
import dagger.Binds
import dagger.MapKey
import dagger.multibindings.IntoMap
import javax.inject.Inject
import javax.inject.Provider
import kotlin.reflect.KClass

/**
 * @author https://android.jlelse.eu/
 */
@Suppress("UNCHECKED_CAST")
@AppScoped
class ViewModelFactory
    @Inject constructor(private val viewModels: MutableMap<Class<out ViewModel>,
            Provider<ViewModel>>): ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T
                      = viewModels[modelClass]?.get() as T
}

@MapKey
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)

@dagger.Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    internal abstract fun postListViewModel(viewModel: LoginViewModel): ViewModel
}