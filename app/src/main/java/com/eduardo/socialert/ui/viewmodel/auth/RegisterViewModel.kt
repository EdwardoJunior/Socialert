package com.eduardo.socialert.ui.viewmodel.auth

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eduardo.socialert.data.network.RetrofitClient
import com.eduardo.socialert.data.model.request.UserRequest
import com.eduardo.socialert.data.model.response.RegisterResponse
import com.eduardo.socialert.data.repository.RegisterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterViewModel () : ViewModel(){

    private val repository : RegisterRepository = RegisterRepository(RetrofitClient.apiRegister)

    private val _registerResponse = MutableStateFlow<RegisterResponse?>(null)
    val registerResponse : StateFlow<RegisterResponse?> = _registerResponse

    private val _error = mutableStateOf("")
    val error : State<String> = _error
    val isLoading = mutableStateOf(false)

    fun registerUser(userRequest: UserRequest){
        viewModelScope.launch {
            isLoading.value = true
            try{
                _registerResponse.value = null
                val response = repository.registerUser(userRequest)
                _registerResponse.value = response
            }catch (e:Exception){
                Log.e("RegisterViewModel", "Error al registrar el usuario", e)
                _error.value = e.message ?: "Error desconocido"
            }finally {
                isLoading.value = false
            }
        }
    }
}