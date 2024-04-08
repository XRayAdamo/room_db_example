package com.rayadams.roomdbexample.use_cases

import com.rayadams.roomdbexample.models.ContactModel
import com.rayadams.roomdbexample.services.DbService
import javax.inject.Inject

class DeleteContactUseCase @Inject constructor(private val dbService: DbService) {
    operator fun invoke(contact: ContactModel) {
        dbService.db.contactDao().delete(contact)
        dbService.notifyDataChanges()
    }
}