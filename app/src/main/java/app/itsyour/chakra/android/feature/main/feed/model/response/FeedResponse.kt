package app.itsyour.chakra.android.feature.main.feed.model.response

import com.google.gson.annotations.Expose

data class FeedResponse(
    @Expose val convos: MutableList<FriendConvosResponse>,
    @Expose val events: MutableList<UpcomingEventResponse>)