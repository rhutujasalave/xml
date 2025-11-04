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
