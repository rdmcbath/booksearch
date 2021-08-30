package com.mcbath.booksearch.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mcbath.booksearch.R

/*MainActivity adds the MainFragment which handles the views.
* Configuration changes like rotation trigger the onDestroy method and will
* be recreated.*/

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(android.R.id.content, MainFragment.newInstance())
                .addToBackStack("mainFragment").commit()
        }
    }
}