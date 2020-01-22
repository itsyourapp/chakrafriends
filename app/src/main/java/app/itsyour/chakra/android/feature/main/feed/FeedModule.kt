package app.itsyour.chakra.android.feature.main.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.itsyour.chakra.android.app.network.models.StagingEnv
import app.itsyour.chakra.android.feature.main.feed.domain.GetFeedUseCase
import app.itsyour.chakra.android.feature.main.feed.model.FeedApi
import dagger.Binds
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named

@dagger.Module
abstract class FeedModule {

    @dagger.Module
    companion object {
        @Provides
        @JvmStatic @FeedScope
        fun feedApi(@Named("Unauthenticated") client: OkHttpClient, builder: Retrofit.Builder) : FeedApi =
            builder
                .client(client)
                .baseUrl(StagingEnv.baseUrl)
                .build()
                .create<FeedApi>(
                    FeedApi::class.java)
    }

    @Binds
    internal abstract fun bindViewModelFactory(factory: FeedViewModelFactory): ViewModelProvider.Factory

    class FeedViewModelFactory
    @Inject constructor(
        private val getFeedUseCase: GetFeedUseCase
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T
                = FeedViewModel(getFeedUseCase) as T }
}