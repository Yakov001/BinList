package com.example.binlist.viewModel

import android.app.Application
import android.util.Log
import android.util.MalformedJsonException
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
import java.net.SocketTimeoutException
import java.util.*

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repo: BinRepository
    init {
        val binDao = BinDatabase.getInstance(application).binDao()
        repo = BinRepository(binDao)
    }

    var memory = mutableStateOf(emptyList<CardResponse>())
        private set
    var cardResponse = MutableStateFlow<Resource<CardResponse>>(Resource.Idle())
        private set

    fun requestBin(bin: String) {
        cardResponse.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            val response = try {
                BinApi.getInstance().requestBin(bin)
            } catch (e: MalformedJsonException) {
                Log.e("retrofit exception", e.message.toString())
                cardResponse.value = Resource.Error(message = "Incorrect input")
                return@launch
            } catch (e: SocketTimeoutException) {
                Log.e("retrofit exception", e.message.toString())
                cardResponse.value = Resource.Error(message = "Internet error")
                return@launch
            } catch (e: Exception) {
                Log.e("retrofit exception", e.stackTraceToString())
                cardResponse.value = Resource.Error(message = "Error")
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
                if (response.code() == 404) {
                    Log.d("response", response.code().toString())
                    cardResponse.value = Resource.Error(message = "Error 404: BIN not found")
                } else {
                    Log.d("response", response.errorBody().toString())
                    cardResponse.value = Resource.Error(message = "Incorrect Input")
                }
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