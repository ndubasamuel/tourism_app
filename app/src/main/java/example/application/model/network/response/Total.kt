package example.application.model.network.response

data class Total(
    val estimated: Int,
    val faa: Int,
    val flarm: Int,
    val mlat: Int,
    val other: Int,
    val satellite: Int,
    val uat: Int
)