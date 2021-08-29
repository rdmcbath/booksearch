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

    /* pass the details of the selected book to a new instance of DetailFragment, passing a data model volume at the specified position from the adapter. Then get them
     as args in the DetailFragment */
    fun openDetailFragment(volume: Volume) {
        supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, DetailFragment.newInstance(volume))
            .addToBackStack("detailFragment").commit()
    }
}