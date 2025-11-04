package com.example.xmlproject.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.xmlproject.data.model.request.SignUpRequest
import com.example.xmlproject.data.model.response.SignUpResponse
import com.example.xmlproject.repository.AuthRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class SignUpViewModel : ViewModel() {

    private val repository = AuthRepository()

    val signUpResponse = MutableLiveData<Response<SignUpResponse>>()
    val errorMessage = MutableLiveData<String>()

    fun signUp(request: SignUpRequest) {
        viewModelScope.launch {
            try {
                val response = repository.signUpUser(request)
                signUpResponse.postValue(response)
            } catch (e: Exception) {
                errorMessage.postValue(e.message ?: "Something went wrong")
            }
        }
    }
}
