package example.application.model.network.response.services

data class Review(
    val review_id: Int,
    val review_date: String,
    val content: String,
    val rating: Int,
    val userPic: String?,
    val userName: String,
    val user_registerDate: String
)