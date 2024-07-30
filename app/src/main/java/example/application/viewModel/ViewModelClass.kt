package example.application.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import example.application.model.network.response.authResponse.AuthResponse
import example.application.model.network.response.guides.GuidesResponse
import example.application.model.network.response.pay.PayResponse
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


    private val _makePayments: MutableLiveData<Resource<PayResponse>> = MutableLiveData()
    val makePayments: LiveData<Resource<PayResponse>>
        get() = _makePayments


    private val _tourGuides: MutableLiveData<Resource<GuidesResponse>> = MutableLiveData()
    val tourGuides: LiveData<Resource<GuidesResponse>>
        get() = _tourGuides

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

    }


    fun registerUser(firstName: String, secondName: String, email: String, phone: String, password: String){

        viewModelScope.launch {
            Log.d("ViewModel", "User Registration:  $firstName, $secondName, $email, $phone, $password")
            _registerData.value =
                repository.registerUser(firstName, secondName, email, phone, password)

        }
    }

    fun makePayments() {
        viewModelScope.launch {
            _makePayments.value = repository.makePayments()
        }
    }

    fun getTourGuides() {
        viewModelScope.launch {
            _tourGuides.value = repository.getTourGuides()
        }
    }


}