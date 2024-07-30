package example.application.model.network.response.guides

data class GuidesResponseItem(
    val about: String,
    val approved: Boolean,
    val business_doc: String,
    val county_id: String,
    val email: String,
    val guide_id: Int,
    val guide_name: String,
    val language: String,
    val number: Int,
    val picture: String
)