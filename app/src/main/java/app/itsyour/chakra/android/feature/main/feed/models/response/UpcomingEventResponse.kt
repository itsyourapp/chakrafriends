package app.itsyour.chakra.android.feature.main.feed.models.response

import com.google.gson.annotations.Expose

data class UpcomingEventResponse(
    @Expose val eventId: String = "",
    @Expose val description: String = "",
    @Expose val date: String = "")