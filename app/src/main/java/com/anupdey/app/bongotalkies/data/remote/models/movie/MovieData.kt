package com.anupdey.app.bongotalkies.data.remote.models.movie


import com.google.gson.annotations.SerializedName

data class MovieData(
    @SerializedName("adult")
    var adult: Boolean = false,
    @SerializedName("backdrop_path")
    var backdropPath: String? = "",
    @SerializedName("genre_ids")
    var genreIds: List<Int>? = listOf(),
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("original_language")
    var originalLanguage: String? = "",
    @SerializedName("original_title")
    var originalTitle: String? = "",
    @SerializedName("overview")
    var overview: String? = "",
    @SerializedName("popularity")
    var popularity: Double = 0.0,
    @SerializedName("poster_path")
    var posterPath: String? = "",
    @SerializedName("release_date")
    var releaseDate: String? = "",
    @SerializedName("title")
    var title: String? = "",
    @SerializedName("video")
    var video: Boolean = false,
    @SerializedName("vote_average")
    var voteAverage: Double = 0.0,
    @SerializedName("vote_count")
    var voteCount: Int = 0
)