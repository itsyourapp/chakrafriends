package app.itsyour.chakra.android.feature.main.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.itsyour.chakra.android.feature.main.feed.domain.GetFeedUseCase
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch

class FeedViewModel(private val getFeedUseCase: GetFeedUseCase): ViewModel() {

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
            val response = getFeedUseCase()
        }
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.clear()
    }
}