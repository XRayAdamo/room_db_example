package com.rayadams.roomdbexample.use_cases

import com.rayadams.roomdbexample.models.ContactModel
import com.rayadams.roomdbexample.services.DbService
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateContactUseCase @Inject constructor(private val dbService: DbService) : BaseUseCase() {
    suspend operator fun invoke (contact:ContactModel) {
        withContext(coroutineScope.coroutineContext) {
            dbService.db.contactDao().update(contact)
            dbService.notifyDataChanges()
        }
    }
}