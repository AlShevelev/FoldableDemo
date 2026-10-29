package com.shevelev.foldable

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FoldableApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
