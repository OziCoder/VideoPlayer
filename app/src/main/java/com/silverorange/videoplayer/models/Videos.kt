package com.silverorange.videoplayer.models

import com.google.gson.annotations.SerializedName

data class Videos(
    @SerializedName("id") var id: String? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("hlsURL") var hlsURL: String? = null,
    @SerializedName("fullURL") var fullURL: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("publishedAt") var publishedAt: String? = null,
    @SerializedName("author") var author: Author? = Author()
)
