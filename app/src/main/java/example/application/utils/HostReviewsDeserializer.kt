//package example.application.utils
//
//import com.google.gson.JsonArray
//import com.google.gson.JsonDeserializationContext
//import com.google.gson.JsonDeserializer
//import com.google.gson.JsonElement
//import example.application.model.network.response.services.HostReviews
//import example.application.model.network.response.services.Review
//import java.lang.reflect.Type
//
//class HostReviewsDeserializer : JsonDeserializer<HostReviews> {
//    override fun deserialize(
//        json: JsonElement?,
//        typeOfT: Type?,
//        context: JsonDeserializationContext?
//    ): HostReviews {
//        val hostReviewsObject = json?.asJsonObject
//        val reviewsArray = hostReviewsObject?.getAsJsonArray("reviews") ?: JsonArray()
//
//        val reviews = mutableListOf<Review>()
//        reviewsArray.forEach {
//            // Deserialize each review and add to the list
//            reviews.add(context?.deserialize(it, Review::class.java)!!)
//        }
//
//        // Create and return HostReviews object
//        return HostReviews(
//            averageRating = hostReviewsObject?.get("averageRating")?.asFloat ?: 0.0f,
//            totalReviews = hostReviewsObject?.get("totalReviews")?.asFloat ?: 0f,
//            reviews = reviews
//        )
//    }
//}
//
