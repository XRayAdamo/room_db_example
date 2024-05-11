package com.rayadams.roomdbexample.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rayadams.roomdbexample.R
import com.rayadams.roomdbexample.views.view_models.EditContactViewModel


@Composable
fun EditContactView(viewModel: EditContactViewModel = hiltViewModel()) {
    EditContactViewContent(
        firstName = viewModel.firstName,
        lastName = viewModel.lastName,
        phoneNumber = viewModel.phoneNumber,
        email = viewModel.email,
        goBack = { viewModel.goBack() },
        save = { viewModel.save() },
        canBeSaved = viewModel.canBeSaved,
        updateFirstName = { viewModel.updateFirstName(it) },
        updateLastName = { viewModel.updateLastName(it) },
        updatePhoneNumber = { viewModel.updatePhoneNumber(it) },
        updateEmail = { viewModel.updateEmail(it) }

    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditContactViewContent(
    firstName: String,
    lastName: String,
    phoneNumber: String,
    email: String?,
    goBack: () -> Unit,
    save: () -> Unit,
    canBeSaved: Boolean,
    updateFirstName: (String) -> Unit,
    updateLastName: (String) -> Unit,
    updatePhoneNumber: (String) -> Unit,
    updateEmail: (String) -> Unit,

    ) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Column(
            modifier = Modifier
                .widthIn(max = 620.dp)
                .fillMaxSize()
        ) {
            TopAppBar(title = { Text(stringResource(R.string.txt_edit_contact)) },
                navigationIcon = {
                    IconButton(onClick = { goBack() }) {
                        Icon(
                            Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = stringResource(R.string.txt_back)
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { save() },
                        enabled = canBeSaved
                    ) {
                        Icon(Icons.Default.Check, contentDescription = stringResource(R.string.txt_add_contact))
                    }
                }
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                TextField(value = firstName,
                    modifier = Modifier
                        .focusTarget()
                        .focusRequester(focusRequester)
                        .fillMaxWidth()
                        .padding(bottom = 10.dp),
                    label = { Text(stringResource(R.string.first_name)) },
                    onValueChange = {
                        updateFirstName(it)
                    })
                TextField(value = lastName,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp),
                    label = { Text(stringResource(R.string.last_name)) },
                    onValueChange = {
                        updateLastName(it)
                    })
                TextField(value = phoneNumber,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp),
                    label = { Text(stringResource(R.string.phone_number)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    onValueChange = {
                        updatePhoneNumber(it)
                    })
                TextField(value = email ?: "",
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = { Text(stringResource(R.string.txt_email)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    onValueChange = {
                        updateEmail(it)
                    })
            }
        }
    }
}

@Preview(name = "en LTR", locale = "en", showBackground = true, backgroundColor = 0xFFFFFFFF)
@Preview(name = "Tablet", locale = "en", showBackground = true, backgroundColor = 0xFFFFFFFF, widthDp = 1280, heightDp = 768)
@Preview(name = "en LTR 2f", locale = "en", fontScale = 2f, showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun Preview() {
    EditContactViewContent(
        firstName = "Rayan",
        lastName = "Adams",
        phoneNumber = "01012345678",
        email = "william.henry.moody@my-own-personal-domain.com",
        goBack = {},
        save = {},
        canBeSaved = true,
        updateFirstName = {},
        updateLastName = {},
        updatePhoneNumber = {},
        updateEmail = {}
    )
}
