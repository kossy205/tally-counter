package com.tallycounter.app.ui.counter

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.FirebaseDatabase
import com.tallycounter.app.data.local.AppDatabase
import com.tallycounter.app.data.model.Count
import kotlinx.coroutines.launch
import java.util.*

class CounterViewModel(application: Application) : AndroidViewModel(application) {
    private val database = AppDatabase.getDatabase(application)
    private val firebaseDatabase = FirebaseDatabase.getInstance()
    
    private val _currentCount = MutableLiveData<Int>(0)
    val currentCount: LiveData<Int> = _currentCount
    
    private val _isGroupSession = MutableLiveData<Boolean>(false)
    val isGroupSession: LiveData<Boolean> = _isGroupSession
    
    private var sessionId: String? = null
    private var userId: String = UUID.randomUUID().toString()

    fun incrementCount() {
        _currentCount.value = (_currentCount.value ?: 0) + 1
        if (_isGroupSession.value == true) {
            updateGroupCount()
        }
    }

    fun decrementCount() {
        val current = _currentCount.value ?: 0
        if (current > 0) {
            _currentCount.value = current - 1
            if (_isGroupSession.value == true) {
                updateGroupCount()
            }
        }
    }

    fun startGroupSession() {
        sessionId = UUID.randomUUID().toString()
        _isGroupSession.value = true
        firebaseDatabase.getReference("sessions")
            .child(sessionId!!)
            .child("counts")
            .child(userId)
            .setValue(0)
    }

    fun joinGroupSession() {
        // In a real app, this would show a dialog to enter session ID
        // For now, we'll just simulate joining a session
        sessionId = "test-session"
        _isGroupSession.value = true
        firebaseDatabase.getReference("sessions")
            .child(sessionId!!)
            .child("counts")
            .child(userId)
            .setValue(0)
    }

    private fun updateGroupCount() {
        sessionId?.let { sid ->
            firebaseDatabase.getReference("sessions")
                .child(sid)
                .child("counts")
                .child(userId)
                .setValue(_currentCount.value)
        }
    }

    fun saveCurrentCount() {
        viewModelScope.launch {
            val count = Count(
                count = _currentCount.value ?: 0,
                timestamp = Date(),
                sessionId = sessionId ?: "solo",
                userId = userId,
                isGroupSession = _isGroupSession.value ?: false
            )
            database.countDao().insert(count)
        }
    }
} 