package app.itsyour.chakra.android.feature.main.feed.domain

import app.itsyour.chakra.android.app.network.Result
import app.itsyour.chakra.android.feature.main.feed.FeedContract
import app.itsyour.chakra.android.feature.main.feed.model.FeedApi
import app.itsyour.chakra.android.feature.main.feed.model.response.FeedResponse
import javax.inject.Inject

class GetFeedUseCase
    @Inject constructor(private val api: FeedApi) {

    suspend operator fun invoke(): Result<List<FeedContract.FeedItem>> {
        try {
            val result = api.get()
            return if (result.isSuccessful) response(result.body()!!)
            else return Result.Error(Exception("Server error"))
        } catch (e: Exception) {
            return Result.Error(Exception("Network error"))
        }
    }

    private fun response(response: FeedResponse): Result<List<FeedContract.FeedItem>> {
        return Result.Success(
            response.convos.map { FeedContract.FeedItem.FriendConvo(it.convo) } +
                response.events.map { FeedContract.FeedItem.UpcomingEvent(it.date, it.description) })
    }
}
