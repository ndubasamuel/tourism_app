package example.application.model.network.response.pay

data class PayResponse(
    val code: Int,
    val `file`: String,
    val line: Int,
    val message: String,
    val name: String,
//    val stack-val trace: List<String>,
    val type: String
)