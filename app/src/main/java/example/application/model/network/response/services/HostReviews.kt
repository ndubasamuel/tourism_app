package example.application.model.network.response.services

data class HostReviews(
    val averageRating: Float,
    val totalReviews: Float,
    val reviews: List<Review> = emptyList()
)