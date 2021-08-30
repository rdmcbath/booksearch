package com.mcbath.booksearch.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.mcbath.booksearch.R
import com.mcbath.booksearch.viewmodels.MainViewModel

/*MainActivity adds the MainFragment which handles the views.
* Configuration changes like rotation trigger the onDestroy method and will
* be recreated.*/

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(android.R.id.content, MainFragment.newInstance())
                .addToBackStack("mainFragment").commit()
        }
    }
}