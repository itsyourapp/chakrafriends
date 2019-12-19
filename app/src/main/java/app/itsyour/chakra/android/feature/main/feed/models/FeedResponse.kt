package app.itsyour.chakra.android.feature.main.feed.models

import com.google.gson.annotations.Expose

data class FeedResponse(
    @Expose val items: List<FeedItem>)