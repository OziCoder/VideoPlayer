package com.silverorange.videoplayer.data.repository

import android.app.Application
import com.silverorange.videoplayer.data.remote.ApiInterface
import com.silverorange.videoplayer.domain.Repository
import com.silverorange.videoplayer.models.OutResult
import com.silverorange.videoplayer.utils.AppConstants
import com.silverorange.videoplayer.utils.AppUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val apiInterFace: ApiInterface,
    private val appContext: Application
) : Repository {
    override fun getVideosFromServer(result: (listVideos: OutResult) -> Unit) {
        if (AppUtils.isNetworkConnected(appContext.applicationContext)) {
            CoroutineScope(Dispatchers.IO).launch {
                val videos = apiInterFace.getVideos(AppConstants.BASE_URL)
                if (videos.isSuccessful && videos.code() == 200 && videos.body() != null) {
                    result(OutResult(videos.body(), AppConstants.MSG_SUCCESS))
                } else {
                    result(OutResult(null, AppConstants.MSG_WRONG))
                }
            }
        } else {
            result(OutResult(null, AppConstants.MSG_NO_INTERNET))
        }
    }
}