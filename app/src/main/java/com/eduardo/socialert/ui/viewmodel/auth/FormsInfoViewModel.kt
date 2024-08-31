package com.eduardo.socialert.ui.viewmodel.auth

import android.view.View
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class FormsInfoViewModel : ViewModel() {
    var name by mutableStateOf("")
    var lastname by mutableStateOf("")
    var curp by mutableStateOf("")
    var phoneNumber by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var repeatPassword by mutableStateOf("")

    //VALIDATIONS
    var nameError by mutableStateOf("")
    var lastNameError by mutableStateOf("")
    var phoneNumberError by mutableStateOf("")
    var curpError by mutableStateOf("")
    var emailError by mutableStateOf("")
    var passwordError by mutableStateOf("")
    var repeatPasswordError by mutableStateOf("")

    fun validateRegisterFormPt1(): Boolean {
        var isValid = true

        if (!name.all { it.isLetter() || it.isWhitespace() }) {
            nameError = "Los nombres deben contener solo letras."
            isValid = false
        } else if (name.isNotBlank() && name.length < 3) {
            nameError = "El nombre es muy corto."
            isValid = false
        } else {
            nameError = ""
        }

        if (!lastname.all { it.isLetter() || it.isWhitespace() }) {
            lastNameError = "Los apellidos debe contener solo letras."
            isValid = false
        } else if (lastname.isNotBlank() && lastname.length < 3) {
            lastNameError = "Los apellidos son muy cortos."
            isValid = false
        } else {
            lastNameError = ""
        }

        val trimmedPhoneNumber = phoneNumber.replace(" ", "")

        if (!phoneNumber.all { it.isDigit() || it.isWhitespace() }) {
            phoneNumberError = "Solo se aceptan números."
            isValid = false
        } else if (trimmedPhoneNumber.isNotBlank() && trimmedPhoneNumber.length != 10) {
            phoneNumberError = "Longitud no válida, solo 10 números."
            isValid = false
        } else {
            phoneNumberError = ""
        }

        if (!curp.all { it.isLetter() || it.isDigit() }) {
            curpError = "La CURP solo debe contener letras y números."
            isValid = false
        } else if (curp.isNotBlank() && curp.length != 18) {
            curpError = "La CURP debe contener 18 caracteres."
            isValid = false
        } else {
            curpError = ""
        }

        return isValid
    }

    fun validateRegisterFormPt2():Boolean{

        var isValid = true

        if(email.isNotBlank() && !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailError = "Correo electrónico no válido, usa @ y ."
            isValid = false
        } else{
            emailError = ""
        }

        when{
            password.isNotBlank() && password.length < 8 -> {
                passwordError = "La contraseña debe tener al menos 8 caracteres"
                isValid = false
            }
            password.isNotBlank() && !password.any { it.isDigit() } -> {
                passwordError = "La contraseña debe contener al menos un número"
                isValid = false
            }
            password.isNotBlank() && !password.any { it.isLetter() } -> {
                passwordError = "La contraseña debe contener al menos una letra"
                isValid = false
            }
            else ->{
                passwordError =""
            }
        }

        if(repeatPassword.isNotBlank() && repeatPassword != password){
            repeatPasswordError = "Las contraseñas no coinciden"
            isValid = false
        }else{
            repeatPasswordError = ""
        }

        return isValid
    }
}


class LoginFormViewModel : ViewModel() {
    var email by mutableStateOf("")
    var password by mutableStateOf("")


    //VALIDATIONS
    var emailError by mutableStateOf("")
    var passwordError by mutableStateOf("")

    fun validateLoginForm():Boolean{
        var isValid = true

        if (email.isBlank()){
            emailError = "El campo no puede quedar vacío"
            isValid = false
        }else if(email.isNotBlank() && !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailError = "Correo electrónico no válido, usa @ y ."
            isValid = false
        }else{
            emailError = ""
        }

        if (password.isBlank()){
            passwordError = "El campo no puede quedar vacío"
            isValid = false
        }else if(password.length<8){
            passwordError = "Contraseña muy corta"
            isValid = false
        }else{
            passwordError = ""
        }

        return isValid
    }

}
