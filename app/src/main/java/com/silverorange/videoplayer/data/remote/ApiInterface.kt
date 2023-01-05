package com.silverorange.videoplayer.data.remote

import com.silverorange.videoplayer.models.Videos
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiInterface {
    @GET
    suspend fun getVideos(
        @Url url: String): Response<List<Videos>>
}