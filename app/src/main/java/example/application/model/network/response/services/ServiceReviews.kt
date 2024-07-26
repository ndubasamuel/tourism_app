package example.application.model.network.response.services

data class ServiceReviews(
    val averageRating: Int = 0,
    val averageCleanliness: Int = 0,
    val averageLocation: Int = 0,
    val averageCommunication: Int = 0,
    val totalReviews: Int = 0,
    val reviews: List<Review> = emptyList()
)