package com.anupdey.app.bongotalkies.data.remote.models.movie_details


import com.google.gson.annotations.SerializedName

data class ProductionCompany(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("logo_path")
    var logoPath: String? = "",
    @SerializedName("name")
    var name: String? = "",
    @SerializedName("origin_country")
    var originCountry: String? = ""
)