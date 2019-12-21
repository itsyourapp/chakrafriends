package app.itsyour.chakra.android.feature.main.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.itsyour.chakra.android.app.network.Result
import app.itsyour.chakra.android.feature.main.feed.domain.GetFeedUseCase
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch

class FeedViewModel(private val getFeedUseCase: GetFeedUseCase) : ViewModel() {

    private val subscriptions = CompositeDisposable()

    private val relay = PublishRelay.create<FeedContract.UiModel>()
    val uiModels: Observable<FeedContract.UiModel> = relay

    fun onAction(action: FeedContract.Action) {
        when (action) {
            is FeedContract.Action.GetFeed -> getFeed()
        }
    }

    private fun getFeed() {
        viewModelScope.launch {
            when (val response = getFeedUseCase()) {
                is Result.Success -> relay.accept(
                    FeedContract.UiModel.Feed(response.value))
                is Result.Error -> relay.accept(
                    FeedContract.UiModel.Error(response.exception.localizedMessage?:""))
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.clear()
    }
}