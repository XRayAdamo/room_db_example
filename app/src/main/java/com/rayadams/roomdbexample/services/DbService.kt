package com.rayadams.roomdbexample.services

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rayadams.roomdbexample.dao.ContactsDao
import com.rayadams.roomdbexample.helpers.EventStateFlow
import com.rayadams.roomdbexample.models.ContactModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DbService @Inject constructor(@ApplicationContext applicationContext: Context) {

    private var _onDataChanged = EventStateFlow()
    /**
     * Subscribe to this Event to get notified when data has changed
     */
    val onDataChanged = _onDataChanged.asStateFlow()

    companion object {
        const val CURRENT_VERSION = 2
    }

    fun notifyDataChanges() {
        _onDataChanged.notifyObservers()
    }

    val db = Room.databaseBuilder(
        applicationContext,
        AppDatabase::class.java, "contacts-db"
    ).build()
}

@Database(
    entities = [ContactModel::class], version = DbService.CURRENT_VERSION, autoMigrations = [
        AutoMigration(from = 1, to = 2)
    ]
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactsDao
}