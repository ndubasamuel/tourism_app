package example.application.utils

import com.google.gson.*
import example.application.model.network.response.services.Review
import example.application.model.network.response.services.ServiceReviews
import java.lang.reflect.Type


class ServiceReviewsDeserializer : JsonDeserializer<ServiceReviews> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): ServiceReviews {
        if (json == null || json.isJsonNull) {
            return ServiceReviews() // Return an empty ServiceReviews object if JSON is null
        }

        return if (json.isJsonObject) {
            // Deserialize the JSON object into a ServiceReviews object
            val jsonObject = json.asJsonObject
            val averageRating = jsonObject.get("averageRating")?.asInt ?: 0
            val averageCleanliness = jsonObject.get("averageCleanliness")?.asInt ?: 0
            val averageLocation = jsonObject.get("averageLocation")?.asInt ?: 0
            val averageCommunication = jsonObject.get("averageCommunication")?.asInt ?: 0
            val totalReviews = jsonObject.get("totalReviews")?.asInt ?: 0
            val reviewsJsonArray = jsonObject.getAsJsonArray("reviews") ?: JsonArray()

            // Deserialize the reviews array
            val reviews = reviewsJsonArray.mapNotNull { element ->
                context?.deserialize<Review>(element, ServiceReviews::class.java)
            }

            ServiceReviews(
                averageRating,
                averageCleanliness,
                averageLocation,
                averageCommunication,
                totalReviews,
                reviews
            )
        } else {
            // Handle case where serviceReviews is an empty array
            ServiceReviews()
        }
    }
}