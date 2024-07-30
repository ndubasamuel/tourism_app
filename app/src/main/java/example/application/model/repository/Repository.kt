package example.application.model.repository

import android.util.Log
import example.application.model.network.api.ServicesApi
import example.application.model.network.response.services.ServicesResponse
import example.application.utils.Resource


class Repository(private val api: ServicesApi): BaseRepository()  {

    suspend fun getServices(): Resource<ServicesResponse> = safeApiCall{
        Log.d("Repository", "Repository call")
        api.fetchServices()
    }


    suspend fun login(email: String, password: String) = safeApiCall {
        Log.d("Repo", "$email, $password")
        api.login(email, password)
    }



    suspend fun registerUser(firstName: String, secondName: String, email: String, phone: String,
                             password: String) = safeApiCall {
        Log.d("Repo", "$firstName, $secondName, $password")
        api.signUp(firstName, secondName, email, phone, password)
    }


    suspend fun makePayments() = safeApiCall{
        api.makePayments("")
    }

    suspend fun getTourGuides() = safeApiCall {
        api.getGuides("")
    }



    companion object {
        @Volatile
        private var INSTANCE: Repository? = null

        fun getInstance(api: ServicesApi): Repository {
            return INSTANCE ?: synchronized(this) {
                val instance = Repository(api)
                INSTANCE = instance
                instance
            }
        }
    }
}