package com.rayadams.roomdbexample.use_cases

import com.rayadams.roomdbexample.models.ContactModel
import com.rayadams.roomdbexample.services.DbService
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetAllContactsUseCase @Inject constructor(private val dbService: DbService) : BaseUseCase() {
    suspend operator fun invoke(searchString: String): List<ContactModel> =
        withContext(coroutineScope.coroutineContext) {
            if (searchString.isBlank()) {
                dbService.db.contactDao().getAllContacts()
            } else {
                dbService.db.contactDao().searchContacts(searchString)
            }
        }
}
