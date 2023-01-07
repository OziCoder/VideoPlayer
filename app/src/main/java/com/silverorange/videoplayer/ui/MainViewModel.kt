package com.silverorange.videoplayer.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.silverorange.videoplayer.domain.Repository
import com.silverorange.videoplayer.models.OutResult
import com.silverorange.videoplayer.models.Videos
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    var resultSet: MutableLiveData<OutResult> = MutableLiveData()

    fun getVideos(outResult: OutResult) {
        resultSet.postValue(outResult)
        repository.getVideosFromServer { outResult ->
            resultSet.postValue(outResult)
        }
    }

     fun setPosition(videos: ArrayList<Videos>, position: Int, isIncrement: Boolean = true): Int {
        var pos = position
        if (isIncrement) {
            if (videos.size - 1 == pos)
                pos = 0
            else ++pos
        } else {
            if (pos == 0)
                pos = videos.size - 1
            else --pos
        }
        return pos
    }
}