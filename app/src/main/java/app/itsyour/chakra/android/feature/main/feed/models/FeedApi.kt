package app.itsyour.chakra.android.feature.main.feed.models

import app.itsyour.chakra.android.app.network.CacheInterceptor
import app.itsyour.chakra.android.feature.main.feed.models.response.FeedResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface FeedApi {
    @GET("feed")
    @Headers(CacheInterceptor.NO_CACHE_HEADER)
    suspend fun get(): Response<FeedResponse>
}