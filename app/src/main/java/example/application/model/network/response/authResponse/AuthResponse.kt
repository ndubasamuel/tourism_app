package example.application.model.network.response.authResponse

data class AuthResponse(
    val first_name: String,
    val second_name: String,
    val email: String,
    val password: String,
    val phone: String,
    val access_token: String,
    val Message: String,
    val `data`: Data,
    val status: Int

)

data class Data(
    val token: String
)