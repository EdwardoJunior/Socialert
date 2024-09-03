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
import kotlinx.coroutines.launch

class RegisterViewModel () : ViewModel(){

    private val repository : RegisterRepository = RegisterRepository(RetrofitClient.apiRegister)

    private val _registerResponse = mutableStateOf<RegisterResponse?>(null)
    val registerResponse : State<RegisterResponse?> = _registerResponse

    private val _error = mutableStateOf("")
    val error : State<String> = _error

    fun registerUser(userRequest: UserRequest){
        viewModelScope.launch {
            try{
                val response = repository.registerUser(userRequest)
                _registerResponse.value = response
            }catch (e:Exception){
                Log.e("RegisterViewModel", "Error al registrar el usuario", e)
                _error.value = e.message ?: "Error desconocido"
            }
        }
    }
}