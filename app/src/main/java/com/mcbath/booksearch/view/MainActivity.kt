package com.mcbath.booksearch.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mcbath.booksearch.R
import com.mcbath.booksearch.models.Volume

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(android.R.id.content, MainFragment.newInstance()).commit()
        }
    }
}