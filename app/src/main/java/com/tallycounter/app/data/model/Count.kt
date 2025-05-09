package com.tallycounter.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "counts")
data class Count(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val count: Int,
    val timestamp: Date,
    val sessionId: String,
    val userId: String,
    val isGroupSession: Boolean
) 