package com.silverorange.videoplayer.domain

import com.silverorange.videoplayer.models.Videos

interface Repository {
    suspend fun getVideosFromServer(result: (listVideos: List<Videos>) -> Unit)
}