package com.anupdey.app.bongotalkies.data.remote.models.movie_details


import com.google.gson.annotations.SerializedName

data class BelongsToCollection(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("name")
    var name: String? = "",
    @SerializedName("poster_path")
    var posterPath: String? = "",
    @SerializedName("backdrop_path")
    var backdropPath: String? = ""
)