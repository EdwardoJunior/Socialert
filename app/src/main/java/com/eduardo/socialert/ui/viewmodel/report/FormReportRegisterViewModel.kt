package com.eduardo.socialert.ui.viewmodel.report

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class FormReportRegisterViewModel : ViewModel(){

    var type_id by mutableStateOf<Int?>(null)
    var description by mutableStateOf("")
    var file by mutableStateOf<Uri?>(null)
    var selectedUriVideo by mutableStateOf<Uri?>(null)
    var selectedUriImage by mutableStateOf<Uri?>(null)
    var latitude by mutableStateOf<Double?>(null)
    var longitude by mutableStateOf<Double?>(null)
    var victim by mutableStateOf("SI")



    //VALIDATIONS
    var typeIdError by mutableStateOf("")
    var descriptionError by mutableStateOf("")

    fun formValidation() : Boolean {
        var isValid = true

        if(type_id == null || type_id == 0){
            typeIdError = "El tipo de reporte es obligatorio."
            isValid = false
        }else{
            typeIdError = ""
        }


        if(description.isNotBlank() && !description.all { it.isLetter()  || it.isWhitespace() }){
            descriptionError = "La descripción solo debe contener letras."
            isValid = false
        }else{
            descriptionError = ""
        }

        return isValid
    }

    fun clearFields(){
        type_id = null
        description = ""
        file = null
        selectedUriVideo = null
        selectedUriImage = null
        latitude = null
        longitude = null
        victim = "SI"
    }

}