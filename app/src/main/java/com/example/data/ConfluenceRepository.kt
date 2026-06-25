package com.example.data

import kotlinx.coroutines.flow.Flow

class ConfluenceRepository(private val confluenceDao: ConfluenceDao) {
    val allSetups: Flow<List<ConfluenceSetup>> = confluenceDao.getAllSetups()

    suspend fun insert(setup: ConfluenceSetup) {
        confluenceDao.insertSetup(setup)
    }

    suspend fun deleteById(id: Int) {
        confluenceDao.deleteSetupById(id)
    }

    suspend fun deleteAll() {
        confluenceDao.deleteAllSetups()
    }
}
