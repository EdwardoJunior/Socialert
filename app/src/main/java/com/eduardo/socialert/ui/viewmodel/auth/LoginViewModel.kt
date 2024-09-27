package com.eduardo.socialert.ui.viewmodel.auth

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eduardo.socialert.data.network.RetrofitClient
import com.eduardo.socialert.data.model.response.LoginResponse
import com.eduardo.socialert.data.model.response.LogoutResponse
import com.eduardo.socialert.data.repository.AuthRepository
import kotlinx.coroutines.launch
import org.json.JSONObject

class LoginViewModel : ViewModel(){

    private val repository : AuthRepository = AuthRepository(RetrofitClient.apiRegister)

    private val _loginResponse = mutableStateOf<LoginResponse?>(null)
    val loginResponse : State<LoginResponse?> = _loginResponse

    private val _logoutResponse = mutableStateOf<LogoutResponse?>(null)
    val logoutResponse : State<LogoutResponse?> = _logoutResponse

    val errorMessage = mutableStateOf<String?>(null)
    val isLoading = mutableStateOf(false)

    fun loginUser(email : String, password : String, context : Context){
        viewModelScope.launch {
            isLoading.value = true
            try{
                errorMessage.value = null
                _logoutResponse.value = null

                val response = repository.loginUser(email, password)

                if(response.isSuccessful){
                    _loginResponse.value = response.body()

                    val sharedPref = context.getSharedPreferences("MyApp", Context.MODE_PRIVATE)
                    with(sharedPref.edit()){
                        putString("auth_token", response.body()?.access_token)
                        apply()
                    }

                }else{
                    val errorBody = response.errorBody()?.string()
                    val errorMsg = extractErrorMessage(errorBody)
                    errorMessage.value = errorMsg
                }
            }catch (e : Exception){
                errorMessage.value = e.message
            } finally {
                isLoading.value = false
            }
        }
    }

    fun logoutUser(context: Context){
        viewModelScope.launch {
            isLoading.value = true
            try{
                _loginResponse.value = null
                errorMessage.value = null

                val sharedPref = context.getSharedPreferences("MyApp", Context.MODE_PRIVATE)
                var token = sharedPref.getString("auth_token", null)

                token?.let {
                    val response = repository.logoutUser(it)
                    if(response.isSuccessful){
                        _logoutResponse.value = response.body()

                        with(sharedPref.edit()){
                            remove("auth_token")
                            apply()
                        }
                        token = sharedPref.getString("auth_token", null)
                    }else{
                        errorMessage.value = "Error en el cierre de sesión: ${response.message()}"
                    }
                }
            }catch (e : Exception){
                errorMessage.value = e.message
            }finally {
                isLoading.value = false
            }
        }
    }

    private fun extractErrorMessage(errorBody: String?): String? {
        return try {
            errorBody?.let {
                val jsonObject = JSONObject(it)
                jsonObject.getString("message")
            }
        } catch (e: Exception) {
            "Error al procesar la respuesta del servidor"
        }
    }

}


