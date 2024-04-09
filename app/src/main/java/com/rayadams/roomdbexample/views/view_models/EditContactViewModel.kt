package com.rayadams.roomdbexample.views.view_models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rayadams.roomdbexample.models.ContactModel
import com.rayadams.roomdbexample.navigation.CustomNavigator
import com.rayadams.roomdbexample.navigation.NavigationParams
import com.rayadams.roomdbexample.use_cases.DataValidUseCase
import com.rayadams.roomdbexample.use_cases.GetContactByIdUseCase
import com.rayadams.roomdbexample.use_cases.UpdateContactUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditContactViewModel @Inject constructor(
    state: SavedStateHandle,
    getContactByIdUseCase: GetContactByIdUseCase,
    private val dataValidUseCase: DataValidUseCase,
    private val updateContactUseCase: UpdateContactUseCase,
    private val customNavigator: CustomNavigator
) : ViewModel() {
    var firstName by mutableStateOf("")
        private set
    var lastName by mutableStateOf("")
        private set
    var phoneNumber by mutableStateOf("")
        private set

    var canBeSaved by mutableStateOf(false)
        private set

    private var contactToEdit: ContactModel? = null

    init {
        // Usually parameters and data from repository
        // must be validated here, but we will assume the good path

        val contactId = state[NavigationParams.CONTACT_ID] ?: -1

        viewModelScope.launch {
            contactToEdit = getContactByIdUseCase(contactId)

            firstName = contactToEdit!!.firstName
            lastName = contactToEdit!!.lastName
            phoneNumber = contactToEdit!!.phoneNumber
        }
    }

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

    fun save() {
        if (!canBeSaved) {
            return
        }

        viewModelScope.launch {
            val updatedContact = contactToEdit!!.copy(
                id = contactToEdit!!.id,
                firstName = firstName,
                lastName = lastName,
                phoneNumber = phoneNumber
            )

            updateContactUseCase(updatedContact)

            goBack()
        }
    }

    fun goBack() {
        customNavigator.goBack()
    }
}