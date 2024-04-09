package com.rayadams.roomdbexample.views.view_models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rayadams.roomdbexample.models.ContactModel
import com.rayadams.roomdbexample.navigation.CustomNavigator
import com.rayadams.roomdbexample.navigation.NavigationPath
import com.rayadams.roomdbexample.services.DbService
import com.rayadams.roomdbexample.use_cases.DeleteContactUseCase
import com.rayadams.roomdbexample.use_cases.GetAllContactsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(
    private val customNavigator: CustomNavigator,
    private val dbService: DbService,
    private val getAllContactsUseCase: GetAllContactsUseCase,
    private val deleteContactUseCase: DeleteContactUseCase
) : ViewModel() {

    val data = mutableStateListOf<ContactModel>()

    var searchString by mutableStateOf("")
        private set

    init {
        viewModelScope.launch {
            dbService.onDataChanged.collect {
                loadData()
            }
        }

        loadData()
    }

    fun updateSearchString(value: String) {
        searchString = value
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            data.clear()
            data.addAll(getAllContactsUseCase(searchString))
        }
    }

    fun editContact(contact: ContactModel) {
        customNavigator.navigate(NavigationPath.EDIT_CONTACT_VIEW + "/${contact.id}")
    }

    fun goToAddNewContact() {
        customNavigator.navigate(NavigationPath.ADD_CONTACTS_VIEW)
    }

    fun deleteContact(contact: ContactModel) {
        viewModelScope.launch {
            deleteContactUseCase(contact)
        }
    }
}