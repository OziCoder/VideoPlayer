package com.silverorange.videoplayer.domain

import com.silverorange.videoplayer.models.OutResult

interface Repository {
    fun getVideosFromServer(result: (listVideos: OutResult) -> Unit)
}