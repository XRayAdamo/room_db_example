package com.rayadams.roomdbexample.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.rayadams.roomdbexample.models.ContactModel

@Dao
interface ContactsDao {
    @Query("SELECT * FROM contacts order by contacts.firstName, contacts.lastName")
    fun getAllContacts(): List<ContactModel>

    @Insert
    fun insert(contactModel: ContactModel)

    @Delete
    fun delete(contactModel: ContactModel)

    @Update
    fun update(contactModel: ContactModel)

    @Query("SELECT * FROM contacts where id = :contactId")
    fun getContactById(contactId:Int): ContactModel?
}
