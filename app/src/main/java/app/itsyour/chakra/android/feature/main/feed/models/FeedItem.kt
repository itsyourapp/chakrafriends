package app.itsyour.chakra.android.feature.main.feed.models

import com.google.gson.annotations.Expose

data class FeedItem(
    @Expose val id: String = "",
    @Expose val message: String = "")