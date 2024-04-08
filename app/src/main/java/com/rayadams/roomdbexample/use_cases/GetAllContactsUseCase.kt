package com.rayadams.roomdbexample.use_cases

import com.rayadams.roomdbexample.models.ContactModel
import com.rayadams.roomdbexample.services.DbService
import javax.inject.Inject

class GetAllContactsUseCase @Inject constructor(private val dbService: DbService) {
    operator fun invoke(): List<ContactModel> =
        dbService.db.contactDao().getAllContacts()
}
