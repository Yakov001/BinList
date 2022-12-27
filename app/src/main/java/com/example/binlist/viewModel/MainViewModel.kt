package com.example.binlist.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.binlist.model.CardResponse
import com.example.binlist.model.BinApi
import com.example.binlist.model.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    var inputCardNumber = mutableStateOf(TextFieldValue(text = ""))
        private set

    var cardResponse = MutableStateFlow<Resource<CardResponse>>(Resource.Idle())
        private set

    fun requestBin(bin: String) {
        cardResponse.value = Resource.Loading()
        viewModelScope.launch {
            val response = try {
                BinApi.getInstance().requestBin(bin)
            } catch (e: Exception) {
                Log.e("retrofit request", e.message.toString())
                Log.e("retrofit request trace", e.stackTraceToString())
                return@launch
            }
            if (response.isSuccessful) {
                cardResponse.value = Resource.Success(response.body()!!.copy(bin = bin))
            } else {
                Log.e("response error", response.errorBody().toString())
                cardResponse.value = Resource.Error(message = response.errorBody().toString())
            }

        }
    }
}