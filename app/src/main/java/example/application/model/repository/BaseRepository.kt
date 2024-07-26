package example.application.model.repository

import android.util.Log
import example.application.utils.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

abstract class BaseRepository {

    suspend fun <T> safeApiCall(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        apiCall: suspend () -> T
    ): Resource<T> {
        Log.d("Base Repo", "Safe Api Call Invoked")
        return withContext(dispatcher) {
            try {
                val result = apiCall.invoke()
                Log.d("Base Repo", "Successful Result: $result")
                Resource.Success(result)
            } catch (throwable: Throwable) {
                val errorMessage = when (throwable) {
                    is IOException -> "Network error occurred. Please check your internet connection and try again."
                    is HttpException -> {
                        when (throwable.code()) {
                            401 -> "Unauthorized access. Please log in again."
                            404 -> "Resource not found."
                            500 -> "Internal server error. Please try again later."
                            else -> "An error occurred. Please try again."
                        }
                    }
                    else -> "An unexpected error occurred. Please try again."
                }
                Log.e("Base Repo", "Error: ${throwable.message}", throwable)
                Resource.Failure(
                    isNetworkError = throwable is IOException,
                    errorCode = (throwable as? HttpException)?.code(),
                    errorBody = (throwable as? HttpException)?.response()?.errorBody(),
                    errorMessage = errorMessage
                )
            }
        }
    }
}
