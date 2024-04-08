package com.rayadams.roomdbexample.use_cases

import com.rayadams.roomdbexample.models.ContactModel
import com.rayadams.roomdbexample.services.DbService
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetContactByIdUseCase @Inject constructor(private val dbService: DbService) : BaseUseCase() {
    suspend operator fun invoke(contactId: Int): ContactModel? =
        withContext(coroutineScope.coroutineContext) {
            dbService.db.contactDao().getContactById(contactId)
        }
}
