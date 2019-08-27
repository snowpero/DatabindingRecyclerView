package com.ninis.databindingrecyclerview.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AlbumItemModel(
    @SerializedName("albumId") @Expose
    val albumId: Int,
    @SerializedName("id") @Expose
    val id: Int,
    @SerializedName("title") @Expose
    val title: String,
    @SerializedName("url") @Expose
    val url: String,
    @SerializedName("thumbnailUrl") @Expose
    val thumbnailUrl: String
)