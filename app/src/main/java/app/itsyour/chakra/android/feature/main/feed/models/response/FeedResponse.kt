package app.itsyour.chakra.android.feature.main.feed.models.response

import com.google.gson.annotations.Expose

data class FeedResponse(
    @Expose val convos: MutableList<FriendConvosResponse>,
    @Expose val events: MutableList<UpcomingEventResponse>)