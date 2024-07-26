package example.application.model.network.response.services

data class Hosts(
    val host_id: Int,
    val about: String,
    val host_name: String,
    val language: String,
    val email: String,
    val number: Long,
    val picture: String,
    val county_id: Int,
    val approved: Boolean,
    val business_doc: String,
    val business_name: String,
    val hostReviews: HostReviews
)