package example.application.utils

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParseException
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ResourceSerializer : JsonSerializer<Resource<*>>, JsonDeserializer<Resource<*>> {

    override fun serialize(src: Resource<*>, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
        val jsonObject = JsonObject()
        when (src) {
            is Resource.Success -> {
                jsonObject.addProperty("status", "success")
                jsonObject.add("data", context.serialize(src.value))
            }
            is Resource.Failure -> {
                jsonObject.addProperty("status", "error")
                jsonObject.addProperty("message", src.errorMessage)
                jsonObject.add("data", context.serialize(src.errorMessage))
            }
            is Resource.Loading -> {
                jsonObject.addProperty("status", "loading")
            }
        }
        return jsonObject
    }

    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Resource<*> {
        val jsonObject = json.asJsonObject

        // Handle numeric and string status codes
        val statusElement = jsonObject.get("status")
        val status: String = when {
            statusElement.isJsonPrimitive && statusElement.asJsonPrimitive.isNumber -> statusElement.asInt.toString()
            statusElement.isJsonPrimitive && statusElement.asJsonPrimitive.isString -> statusElement.asString
            else -> throw JsonParseException("Unknown status: $statusElement")
        }

        return when (status) {
            "success", "200" -> {
                val dataType = (typeOfT as ParameterizedType).actualTypeArguments[0]
                val data = context.deserialize<Any>(jsonObject.get("data"), dataType)
                Resource.Success(data)
            }
            "error" -> {
                val message = jsonObject.get("message").asString
                val dataType = (typeOfT as ParameterizedType).actualTypeArguments[0]
                val data = context.deserialize<Any>(jsonObject.get("data"), dataType)
                Resource.Failure(false, null, null, "")
            }
            "loading" -> Resource.Loading
            else -> throw JsonParseException("Unknown status: $status")
        }
    }
}
