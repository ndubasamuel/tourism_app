package example.application.model.network.response.services

data class Service(
    val service_id: Int,
    val host_id: Int,
    val price: Int,
    val images: List<Image>,
    val pricing_criteria: String,
    val type_id: Int,
    val county_id: String,
    val description: String,
    val start_date: String,
    val end_date: String,
    val approved: Boolean,
    val service_name: String,
    val guests: Int,
    val bedroom: Int,
    val beds: Int?,
    val bath: Int,
    val cancellation_policy: String,
    val county: County,
    val hosts: Hosts,
    val roles: List<Role>,
    val amenities: List<Amenity>,
//    val serviceReviews: ServiceReviews
)