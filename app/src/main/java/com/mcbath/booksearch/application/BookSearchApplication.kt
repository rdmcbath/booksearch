package com.mcbath.booksearch.application

import android.app.Application

class BookSearchApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: BookSearchApplication
            private set
    }
}