package app.itsyour.chakra.android.feature.main.feed.domain

import app.itsyour.chakra.android.app.network.Result
import app.itsyour.chakra.android.feature.main.feed.models.FeedApi
import app.itsyour.chakra.android.feature.main.feed.models.FeedResponse
import javax.inject.Inject

class GetFeedUseCase
    @Inject constructor(private val api: FeedApi) {

    suspend operator fun invoke(): Result<FeedResponse> {
        try {
            val result = api.get()
            return if (result.isSuccessful) Result.Success(result.body()!!)
            else return Result.Error(Exception("Server error"))
        } catch (e: Exception) {
            return Result.Error(Exception("Network error"))
        }
    }
}
