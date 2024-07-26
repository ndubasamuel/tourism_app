package example.application.model.network.response

data class FlightsResponse(
    val aircraft: List<List<Any>>,
    val full_count: Int,
    val stats: Stats,
    val version: Int
)