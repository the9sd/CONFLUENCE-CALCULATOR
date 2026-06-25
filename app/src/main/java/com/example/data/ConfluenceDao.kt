package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ConfluenceDao {
    @Query("SELECT * FROM confluence_setups ORDER BY timestamp DESC")
    fun getAllSetups(): Flow<List<ConfluenceSetup>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSetup(setup: ConfluenceSetup)

    @Query("DELETE FROM confluence_setups WHERE id = :id")
    suspend fun deleteSetupById(id: Int)

    @Query("DELETE FROM confluence_setups")
    suspend fun deleteAllSetups()
}
