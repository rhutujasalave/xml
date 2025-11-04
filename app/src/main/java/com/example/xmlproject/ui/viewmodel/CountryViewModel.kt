//package com.example.xmlproject.ui.viewmodel
//
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.xmlproject.data.api.RetrofitClient
//import com.example.xmlproject.data.model.response.DialCode
//import kotlinx.coroutines.launch
//
//class CountryViewModel : ViewModel() {
//
//    val countryListLiveData = MutableLiveData<List<DialCode>>()
//    val errorLiveData = MutableLiveData<String>()
//
//    fun fetchCountryList() {
//        viewModelScope.launch {
//            try {
//                val response = RetrofitClient.api.getDialCodes(
//                    page = TODO(),
//                    take = TODO(),
//                    sortColumn = TODO(),
//                    sortOrder = TODO()
//                )
//                if (response.isSuccessful) {
//                    val list = response.body()?.data ?: emptyList()
//                    countryListLiveData.postValue(list)
//                } else {
//                    errorLiveData.postValue("Failed to fetch country codes")
//                }
//            } catch (e: Exception) {
//                errorLiveData.postValue(e.message ?: "Something went wrong")
//            }
//        }
//    }
//}
package com.example.xmlproject.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.xmlproject.data.model.response.DialCode
import com.example.xmlproject.repository.AuthRepository
import kotlinx.coroutines.launch

class CountryViewModel : ViewModel() {

    private val repository = AuthRepository()
    val countryListLiveData = MutableLiveData<List<DialCode>>()
    val errorLiveData = MutableLiveData<String>()

    fun fetchCountryList() {
        viewModelScope.launch {
            try {
                val response = repository.getDialCodes()
                if (response.isSuccessful && response.body() != null) {
                    val apiList = response.body()?.data ?: emptyList()
                    countryListLiveData.postValue(apiList)
                } else {
                    errorLiveData.postValue("Failed to fetch countries: ${response.code()}")
                }
            } catch (e: Exception) {
                errorLiveData.postValue(e.message ?: "Something went wrong")
            }
        }
    }
}
