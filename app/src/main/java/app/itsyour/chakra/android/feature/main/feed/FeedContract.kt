package app.itsyour.chakra.android.feature.main.feed

import app.itsyour.chakra.android.app.base.Contract

interface FeedContract : Contract {
    sealed class Action {
        object GetFeed : Action()
    }

    sealed class UiModel {
        class Feed(val items: List<FeedItem>) : UiModel()
        class Error(val errorMessage: String) : UiModel()
    }

    sealed class FeedItem(val id: Int) {
        data class UpcomingEvent(
            val date: String,
            val description: String) : FeedItem(0)

        data class FriendConvo(
            val message: String) : FeedItem(1)
    }
}