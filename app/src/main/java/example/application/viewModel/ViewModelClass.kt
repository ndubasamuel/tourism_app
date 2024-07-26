package example.application.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import example.application.model.network.response.authResponse.AuthResponse
import example.application.model.network.response.services.ServicesResponse
import example.application.model.repository.Repository
import example.application.utils.Resource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ViewModelClass(private val repository: Repository): ViewModel() {

    private val _services: MutableLiveData<Resource<ServicesResponse>> = MutableLiveData()
    val services: LiveData<Resource<ServicesResponse>>
        get() = _services

    private val _loginData: MutableLiveData<Resource<AuthResponse>> = MutableLiveData()
    val loginData: LiveData<Resource<AuthResponse>>
        get() = _loginData


    private val _registerData: MutableLiveData<Resource<AuthResponse>> = MutableLiveData()
    val registerData: LiveData<Resource<AuthResponse>>
        get() = _registerData

    fun getServices() {
        viewModelScope.launch {
                withContext(Dispatchers.Main) {
                        val response = repository.getServices()
                    if (response is Resource.Success){
                        _services.postValue(response)
                    }

                }
        }
    }

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            Log.e("ViewModel", "Repo Data ${email}, $password")
            _loginData.value = repository.login(email, password)
        }
        throw Exception("Login Failed")

    }


    fun registerUser(firstName: String, secondName: String, email: String, phone: String, password: String){

        viewModelScope.launch {
            Log.d("ViewModel", "User Registration:  $firstName, $secondName, $email, $phone, $password")
            _registerData.value =
                repository.registerUser(firstName, secondName, email, phone, password)

        }
    }


}