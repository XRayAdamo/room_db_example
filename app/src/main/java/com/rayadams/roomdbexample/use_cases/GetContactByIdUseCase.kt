package com.rayadams.roomdbexample.use_cases

import com.rayadams.roomdbexample.models.ContactModel
import com.rayadams.roomdbexample.services.DbService
import javax.inject.Inject

class GetContactByIdUseCase @Inject constructor(private val dbService: DbService) {
    operator fun invoke(contactId: Int): ContactModel? =
        dbService.db.contactDao().getContactById(contactId)
}
