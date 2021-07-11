package com.udacity.asteroidradar.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.api.MarsApi
import com.udacity.asteroidradar.api.MarsProperty
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    // The internal MutableLiveData String that stores the most recent response status
    private val _status = MutableLiveData<String>()

    // The external immutable LiveData for the status String
    val status: LiveData<String>
        get() = _status

    // Internally, we use a MutableLiveData, because we will be updating the List of MarsProperty
    // with new values
    private val _properties = MutableLiveData<List<MarsProperty>>()

    // The external LiveData interface to the property is immutable, so only this class can modify
    val properties: LiveData<List<MarsProperty>>
        get() = _properties

    init {
        getMarsRealEstateProperties()
    }

    /**
     * Gets Mars real estate property information from the Mars API Retrofit service and updates the
     * [MarsProperty] [List] [LiveData]. The Retrofit service returns a coroutine Deferred, which we
     * await to get the result of the transaction.
     */
    private fun getMarsRealEstateProperties() {
        viewModelScope.launch {
            try {
                var listResult = MarsApi.retrofitService.getProperties()
                Log.i("RETROFIT", listResult.toString())
                _status.value = "Success: ${listResult.size}"
                if (listResult.size > 0) {
                    _properties.value = listResult
                }
            } catch (e: Exception) {
                _status.value = "Failure: ${e.message}"
            }
        }
    }
}