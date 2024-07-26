package example.application.model.network.api

import example.application.model.network.response.authResponse.AuthResponse
import example.application.model.network.response.services.ServicesResponse
import example.application.utils.Resource
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ServicesApi {



    @GET("flights/list-by-airline?airline=AXM")
    suspend fun getUber()

    @GET("show/services")
    suspend fun fetchServices() : ServicesResponse

    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): AuthResponse


    @FormUrlEncoded
    @POST("user/signup")
    suspend fun signUp(
        @Field("first_name") firstName: String,
        @Field("second_name") lastName: String,
        @Field("email") email: String,
        @Field("phone") phone: String,
        @Field("password") password: String

    ): AuthResponse
}