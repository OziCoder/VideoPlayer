package com.silverorange.videoplayer.ui

import androidx.lifecycle.ViewModel
import com.silverorange.videoplayer.domain.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
}