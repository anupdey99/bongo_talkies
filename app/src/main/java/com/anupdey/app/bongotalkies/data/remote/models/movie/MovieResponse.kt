package com.anupdey.app.bongotalkies.data.remote.models.movie


import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("page")
    var page: Int = 0,
    @SerializedName("total_pages")
    var totalPages: Int = 0,
    @SerializedName("total_results")
    var totalResults: Int = 0,
    @SerializedName("results")
    var movieData: List<MovieData> = listOf()
)