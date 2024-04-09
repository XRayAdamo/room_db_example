package com.rayadams.roomdbexample.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.rayadams.roomdbexample.models.ContactModel

@Dao
interface ContactsDao {
    @Query("SELECT * FROM contacts ORDER BY contacts.firstName, contacts.lastName")
    fun getAllContacts(): List<ContactModel>

    @Query("SELECT * FROM contacts WHERE firstName LIKE :searchString || '%' " +
            "OR lastName LIKE :searchString || '%' " +
            "ORDER BY contacts.firstName, contacts.lastName")
    fun searchContacts(searchString:String): List<ContactModel>

    @Insert
    fun insert(contactModel: ContactModel)

    @Delete
    fun delete(contactModel: ContactModel)

    @Update
    fun update(contactModel: ContactModel)

    @Query("SELECT * FROM contacts WHERE id = :contactId")
    fun getContactById(contactId:Int): ContactModel?
}
