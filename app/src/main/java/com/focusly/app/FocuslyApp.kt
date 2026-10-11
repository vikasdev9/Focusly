package com.focusly.app

import android.app.Application
import com.focusly.app.data.local.AppDatabase
import com.focusly.app.data.local.DemoDataInitializer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FocuslyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        CoroutineScope(Dispatchers.IO).launch {
            val db = AppDatabase.getDatabase(this@FocuslyApp)
            DemoDataInitializer.populateInitialData(db)
        }
    }
}
