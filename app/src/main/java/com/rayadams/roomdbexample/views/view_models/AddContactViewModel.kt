package com.rayadams.roomdbexample.views.view_models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rayadams.roomdbexample.models.ContactModel
import com.rayadams.roomdbexample.navigation.CustomNavigator
import com.rayadams.roomdbexample.use_cases.DataValidUseCase
import com.rayadams.roomdbexample.use_cases.InsertContactUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddContactViewModel @Inject constructor(
    private val customNavigator: CustomNavigator,
    private val insertContactUseCase: InsertContactUseCase,
    private val dataValidUseCase: DataValidUseCase
) : ViewModel() {

    var firstName by mutableStateOf("")
        private set
    var lastName by mutableStateOf("")
        private set
    var phoneNumber by mutableStateOf("")
        private set
    var email by mutableStateOf<String?>(null)
        private set

    var canBeSaved by mutableStateOf(false)
        private set


    private fun updateCanBeSaved() {
        canBeSaved = dataValidUseCase(firstName, lastName, phoneNumber)
    }

    fun updateFirstName(value: String) {
        firstName = value

        updateCanBeSaved()
    }

    fun updateLastName(value: String) {
        lastName = value

        updateCanBeSaved()
    }

    fun updatePhoneNumber(value: String) {
        phoneNumber = value

        updateCanBeSaved()
    }

    fun updateEmail(it: String) {
        email = it
        // no need to call updateCanBeSaved since em,ail is not mandatory field.
    }

    fun save() {
        if (!canBeSaved) {
            return
        }

        viewModelScope.launch {
            insertContactUseCase(
                ContactModel(
                    firstName = firstName,
                    lastName = lastName,
                    phoneNumber = phoneNumber,
                    email = email
                )
            )

            goBack()
        }
    }

    fun goBack() {
        customNavigator.goBack()
    }
}