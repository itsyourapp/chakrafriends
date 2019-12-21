package app.itsyour.chakra.android.feature.main.feed.models.response

import com.google.gson.annotations.Expose

data class FriendConvosResponse(
    @Expose val friendId: String = "",
    @Expose val convo: String = "")