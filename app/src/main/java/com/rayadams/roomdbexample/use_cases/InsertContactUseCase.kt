package com.rayadams.roomdbexample.use_cases

import com.rayadams.roomdbexample.models.ContactModel
import com.rayadams.roomdbexample.services.DbService
import javax.inject.Inject

class InsertContactUseCase @Inject constructor(private val dbService: DbService) {
    operator fun invoke(contact: ContactModel) {
        dbService.db.contactDao().insert(contact)
        dbService.notifyDataChanges()
    }
}