package com.rayadams.roomdbexample.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rayadams.roomdbexample.R
import com.rayadams.roomdbexample.models.ContactModel
import com.rayadams.roomdbexample.ui.theme.RoomDBExampleTheme
import com.rayadams.roomdbexample.views.view_models.ContactsViewModel

@Composable
fun ContactsView(viewModel: ContactsViewModel = hiltViewModel()) {
    ContactsViewContent(
        goToAddNewContact = { viewModel.goToAddNewContact() },
        searchString = viewModel.searchString,
        updateSearchString = { viewModel.updateSearchString(it) },
        data = viewModel.data,
        editContact = { viewModel.editContact(it) },
        deleteContact = { viewModel.deleteContact(it) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ContactsViewContent(
    goToAddNewContact: () -> Unit,
    searchString: String,
    updateSearchString: (String) -> Unit,
    data: List<ContactModel>,
    editContact: (ContactModel) -> Unit,
    deleteContact: (ContactModel) -> Unit
) {
    val (askDelete, setAskDelete) = remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(title = { Text(stringResource(R.string.txt_contacts)) },
            actions = {
                IconButton(onClick = { goToAddNewContact() }) {
                    Icon(Icons.Default.Add, contentDescription = stringResource(R.string.txt_add_contact))
                }
            }
        )
        TextField(value = searchString,
            modifier = Modifier
                .widthIn(max = 620.dp)
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .padding(bottom = 10.dp, start = 8.dp, end = 8.dp),
            label = { Text(stringResource(R.string.txt_search)) },
            trailingIcon = {
                IconButton(
                    enabled = searchString.isNotBlank(),
                    onClick = { updateSearchString("") }) {
                    Icon(Icons.Default.Clear, contentDescription = stringResource(R.string.txt_clear))
                }
            },
            onValueChange = {
                updateSearchString(it)
            })
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(8.dp),
            modifier = Modifier
                .widthIn(max = 620.dp)
                .align(Alignment.CenterHorizontally)
                .fillMaxSize()
        ) {
            items(items = data, key = { it.id!! }) { contact ->
                Card(
                    elevation = CardDefaults.cardElevation(3.dp),
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth(),
                    onClick = {
                        editContact(contact)
                    }) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(5.dp)
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("${contact.firstName} ${contact.lastName}")
                            Text(
                                stringResource(R.string.phone_number_formatted, contact.phoneNumber)
                            )
                            contact.email?.let {
                                Text(stringResource(R.string.email_formatted, contact.email))
                            }
                        }
                        IconButton(modifier = Modifier.padding(start = 5.dp, end = 5.dp),
                            onClick = { setAskDelete(true) }) {
                            Icon(Icons.Default.Delete, contentDescription = stringResource(R.string.txt_delete))
                        }
                    }
                    if (askDelete) {
                        AskDeleteDialog(onCancel = { setAskDelete(false) }) {
                            setAskDelete(false)
                            deleteContact(contact)
                        }
                    }
                }
            }
        }
    }
}

@Preview(name = "en LTR", locale = "en", showBackground = true, backgroundColor = 0xFFFFFFFF)
@Preview(name = "Tablet", locale = "en", showBackground = true, backgroundColor = 0xFFFFFFFF, widthDp = 1280, heightDp = 768)
@Preview(name = "en LTR 2f", locale = "en", fontScale = 2f, showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun Preview() {
    val list = listOf(
        ContactModel(
            id = 1,
            firstName = "John",
            lastName = "Doe",
            phoneNumber = "1234567890",
            email = "john.doe@example.com"
        ),
        ContactModel(
            id = 2,
            firstName = "Jane",
            lastName = "Doe",
            phoneNumber = "0987654321",
            email = "jane.doe@example.com"
        ),
        ContactModel(
            id = 3,
            firstName = "Michael",
            lastName = "Doe",
            phoneNumber = "9876543210",
            email = "michael.doe@example.com"
        ),
        ContactModel(
            id = 4,
            firstName = "Emily",
            lastName = "Doe",
            phoneNumber = "8765432109",
            email = "emily.doe@example.com"
        )
    )
    RoomDBExampleTheme {
        ContactsViewContent(
            goToAddNewContact = {},
            searchString = "",
            updateSearchString = {},
            data = list,
            editContact = {},
            deleteContact = {}
        )
    }
}
