package com.tallycounter.app.ui.history

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import com.tallycounter.app.data.local.AppDatabase

class HistoryViewModel(application: Application) : AndroidViewModel(application) {
    private val database = AppDatabase.getDatabase(application)
    val counts = database.countDao().getAllCounts().asLiveData()
} 