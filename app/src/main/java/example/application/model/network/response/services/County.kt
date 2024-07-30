package example.application.model.network.response.services

import com.google.gson.annotations.SerializedName

data class County(
    val county_code: Int,
    @SerializedName("county_id")
    val countyId: String,
    @SerializedName("county_name")
    val countyName: String,
    val county_url: String
)