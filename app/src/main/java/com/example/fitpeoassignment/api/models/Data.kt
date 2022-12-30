package com.example.fitpeoassignment.api.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import javax.inject.Inject

 data class Data (
    @SerializedName("albumId")
    var albumId : Int?    = null,

    @SerializedName("id")
    var id : Int?    = null,

    @SerializedName("title")
    var title : String? = null,

    @SerializedName("url")
    var url : String? = null,

    @SerializedName("thumbnailUrl")
    var thumbnailUrl : String? = null

) : Serializable