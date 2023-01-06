package com.silverorange.videoplayer.domain

import com.silverorange.videoplayer.models.OutResult
import com.silverorange.videoplayer.models.Videos

interface Repository {
    fun getVideosFromServer(result: (listVideos: OutResult) -> Unit)
}