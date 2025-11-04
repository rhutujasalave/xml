//package com.example.xmlproject.ui.viewmodel
//
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.xmlproject.usecase.LoginUseCase
//import com.example.xmlproject.data.model.response.ApiResponse
//import com.example.xmlproject.repository.AuthRepository
//import com.example.xmlproject.network.ApiClient
//import kotlinx.coroutines.launch
//
//class SignInViewModel() : ViewModel() {
//
//    private val loginUseCase by lazy {
//        LoginUseCase(
//            AuthRepository(ApiClient.getApiService())
//        )
//    }
//
//    private val _loginState = MutableLiveData<LoginState>()         //LiveData holding the current login state
//    val loginState: LiveData<LoginState> = _loginState           //UI can observe changes but cannot modify it.This protects internal state.
//
//    private val _isLoading = MutableLiveData<Boolean>()
//    val isLoading: LiveData<Boolean> = _isLoading
//
//    sealed class LoginState {                // represents all possible login states
//        object Idle : LoginState()
//        object Loading : LoginState()
//        data class Success(val response: ApiResponse) : LoginState()
//        data class Error(val message: String) : LoginState()
//    }
//
//    fun loginUser(diaCode: String, phone: String, password: String) {
//        viewModelScope.launch {             // coroutines used to handle background tasks without freezing or blocking the UI.
//            try {
//                _isLoading.value = true
//                _loginState.value = LoginState.Loading
//
//                loginUseCase(diaCode, phone, password)
//                    .onSuccess { response ->
//                        if (response.statusCode == 201) {
//                            _loginState.value = LoginState.Success(response)
//                        } else {
//                            val errorMessage = if (!response.errors.isNullOrEmpty()) {
//                                response.errors.first()         //first element from the list.
//                            } else {
//                                response.message
//                            }
//                            _loginState.value = LoginState.Error(errorMessage)
//                        }
//                    }
//                    .onFailure { exception ->
//                        _loginState.value = LoginState.Error(exception.message ?: "Login failed")
//                    }
//            } catch (e: Exception) {
//                _loginState.value = LoginState.Error("Unexpected error: ${e.message}")
//            } finally {
//                _isLoading.value = false
//            }
//        }
//    }
//
//////    fun resetState() {
//////        _loginState.value = LoginState.Idle
//////        _isLoading.value = false
//////    }
//}



package com.example.xmlproject.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.xmlproject.data.model.request.LoginRequest
import com.example.xmlproject.repository.AuthRepository
import kotlinx.coroutines.launch
import com.example.xmlproject.data.model.response.LoginResponse

class SignInViewModel : ViewModel() {

    private val repository = AuthRepository()

    sealed class LoginState {
        object Idle : LoginState()
        object Loading : LoginState()
        data class Success(val response: LoginResponse) : LoginState()
        data class Error(val message: String) : LoginState()
    }

    private val _loginState = MutableLiveData<LoginState>(LoginState.Idle)
    val loginState: LiveData<LoginState> = _loginState

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun loginUser(diaCode: String, phone: String, password: String) {
        viewModelScope.launch {
            _isLoading.postValue(true)
            _loginState.postValue(LoginState.Loading)
            try {
                val request = LoginRequest(diaCode, phone, password)
                val response = repository.loginUser(request)

                if (response.isSuccessful && response.body() != null) {
                    _loginState.postValue(LoginState.Success(response.body()!!))
                } else {
                    val errorMsg = response.errorBody()?.string() ?: "Login failed"
                    _loginState.postValue(LoginState.Error(errorMsg))
                }
            } catch (e: Exception) {
                _loginState.postValue(LoginState.Error("Error: ${e.localizedMessage}"))
            } finally {
                _isLoading.postValue(false)
            }
        }
    }
}
