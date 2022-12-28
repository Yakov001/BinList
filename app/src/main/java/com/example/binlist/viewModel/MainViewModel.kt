package com.example.binlist.viewModel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.binlist.model.CardResponse
import com.example.binlist.model.BinApi
import com.example.binlist.model.Resource
import com.example.binlist.model.room.BinDatabase
import com.example.binlist.model.room.BinRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repo: BinRepository
    init {
        val binDao = BinDatabase.getInstance(application).binDao()
        repo = BinRepository(binDao)
    }

    var memory = mutableStateOf(emptyList<CardResponse>())
        private set
    var inputCardNumber = mutableStateOf(TextFieldValue(text = ""))
        private set
    var cardResponse = MutableStateFlow<Resource<CardResponse>>(Resource.Idle())
        private set

    fun requestBin(bin: String) {
        cardResponse.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            val response = try {
                BinApi.getInstance().requestBin(bin)
            } catch (e: Exception) {
                Log.e("retrofit request", e.message.toString())
                Log.e("retrofit request trace", e.stackTraceToString())
                return@launch
            }
            if (response.isSuccessful) {
                Resource.Success(response.body()!!.copy(
                    bin = bin,
                    requestTimeMillis = System.currentTimeMillis())
                ).also {
                    cardResponse.value = it
                    repo.insert(it.data!!)
                }
            } else {
                Log.e("response error", response.errorBody().toString())
                cardResponse.value = Resource.Error(message = response.errorBody().toString())
            }

        }
    }

    fun getAllCards() {
        viewModelScope.launch(Dispatchers.IO) {
            memory.value = repo.getAll()
        }
    }

    fun getCardById(id: UUID) : CardResponse {
        return runBlocking(Dispatchers.IO) {
            repo.getById(id)
        }
    }
}