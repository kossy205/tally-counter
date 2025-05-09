package com.tallycounter.app.data.local.dao

import androidx.room.*
import com.tallycounter.app.data.model.Count
import kotlinx.coroutines.flow.Flow

@Dao
interface CountDao {
    @Query("SELECT * FROM counts ORDER BY timestamp DESC")
    fun getAllCounts(): Flow<List<Count>>

    @Query("SELECT * FROM counts WHERE sessionId = :sessionId")
    fun getCountsBySession(sessionId: String): Flow<List<Count>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(count: Count)

    @Delete
    suspend fun delete(count: Count)

    @Query("DELETE FROM counts")
    suspend fun deleteAll()
} 