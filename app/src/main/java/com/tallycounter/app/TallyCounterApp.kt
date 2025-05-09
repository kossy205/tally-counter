package com.tallycounter.app

import android.app.Application
import com.google.firebase.FirebaseApp

class TallyCounterApp : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
} 