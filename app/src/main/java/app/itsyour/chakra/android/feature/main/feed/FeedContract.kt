package app.itsyour.chakra.android.feature.main.feed

import app.itsyour.chakra.android.app.base.Contract

interface FeedContract : Contract {
    sealed class Action {
        object GetFeed : Action()
    }

    sealed class UiModel
}