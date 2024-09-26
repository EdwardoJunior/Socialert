package com.eduardo.socialert.ui.viewmodel.report

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.eduardo.socialert.data.model.ReportDetails
import com.eduardo.socialert.data.model.ReportsTypeDetails
import com.eduardo.socialert.data.network.RetrofitClient
import com.eduardo.socialert.data.repository.reports.ReportRepository
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Locale

class ReportViewModel : ViewModel() {
    private val repository : ReportRepository = ReportRepository(RetrofitClient.apiRegister)

    private val _reportsState = mutableStateOf<List<ReportDetails>>(emptyList())
    val reportsState : State<List<ReportDetails>> = _reportsState

    private val _errorMessage = mutableStateOf("")
    val  errorMessage : State<String> = _errorMessage

    private val _reportsTypeState = mutableStateOf<List<ReportsTypeDetails>>(emptyList())
    val reportsTypeState : State<List<ReportsTypeDetails>> = _reportsTypeState

    private val _getTypesErrorMessage = mutableStateOf("")
    val  getTypesErrorMessage : State<String> = _getTypesErrorMessage

    fun getAllReports(context: Context){
        viewModelScope.launch {

            val sharedPref = context.getSharedPreferences("MyApp", Context.MODE_PRIVATE)
            val token = sharedPref.getString("auth_token", null)

            token?.let {
                try {
                    _errorMessage.value = ""
                    val response = repository.getAllReports(it)
                    if(response.isSuccessful){
                        _reportsState.value = response.body()?.reports ?: emptyList()
                    }else{
                        val errorBody = response.errorBody()?.string()
                        val errorMsg = extractErrorMessage(errorBody)
                        _errorMessage.value = "Error: $errorMsg"
                        println(_errorMessage.value)
                    }
                }catch (e : Exception){
                    _errorMessage.value = "Error al obtener los reportes : ${e.message}"
                }
            }


        }
    }

    fun getAllReportsType(context: Context){
        viewModelScope.launch {
            val sharedPref = context.getSharedPreferences("MyApp", Context.MODE_PRIVATE)
            val token = sharedPref.getString("auth_token", null)

            token?.let {
                try {
                    _getTypesErrorMessage.value = ""
                    val response = repository.getAllReportsType(it)
                    if(response.isSuccessful){
                        _reportsTypeState.value = response.body()?.reports ?: emptyList()
                    }else{
                        val errorBody = response.errorBody()?.string()
                        val errorMsg = extractErrorMessage(errorBody)
                        _getTypesErrorMessage.value = "Error: $errorMsg"
                    }
                }catch (e : Exception){
                    _getTypesErrorMessage.value = "Error al obtener los reportes : ${e.message}"
                }
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

fun formatDate(inputDate : String) : String{
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd/MM", Locale.getDefault())
        val date = inputFormat.parse(inputDate)
        outputFormat.format(date)
    }catch (e : Exception){
        inputDate
    }
}

fun formatHour(inputDate : String) : String{
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val outputFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
        val date = inputFormat.parse(inputDate)
        outputFormat.format(date)
    }catch (e : Exception){
        inputDate
    }
}