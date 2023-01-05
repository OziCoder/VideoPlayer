package com.silverorange.videoplayer.models

import com.google.gson.annotations.SerializedName

data class Author(
    @SerializedName("id") var id: String? = null,
    @SerializedName("name") var name: String? = null
)
