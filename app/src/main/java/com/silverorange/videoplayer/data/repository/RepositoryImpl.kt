package com.silverorange.videoplayer.data.repository

import android.app.Application
import com.silverorange.videoplayer.data.remote.ApiInterface
import com.silverorange.videoplayer.domain.Repository
import com.silverorange.videoplayer.models.Videos
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val apiInterFace: ApiInterface,
    private val appContext: Application
) : Repository {
    override suspend fun getVideosFromServer(result: (listVideos: List<Videos>) -> Unit) {

    }
}