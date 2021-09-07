package com.mcbath.booksearch.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.mcbath.booksearch.R
import com.mcbath.booksearch.viewmodels.MainViewModel

/*MainActivity adds the MainFragment which handles the views.
* Configuration changes like rotation trigger the onDestroy method and will
* be recreated.*/

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var errorTextView: TextView
    private lateinit var frameLayout: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        errorTextView = findViewById(R.id.network_error_tv)
        frameLayout = findViewById(R.id.main_content)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.main_content, MainFragment.newInstance()).commit()
        }

        checkInternetConnection()
    }

    fun checkInternetConnection() {
        viewModel.checkInternetConnection()
        viewModel.connection.observe(this) { hasInternet ->
            if(!hasInternet) {
                //no network connection, show error message
                errorTextView.visibility = View.VISIBLE
                frameLayout.visibility = View.INVISIBLE
            }
            else {
                //we have a network connection, show content
                errorTextView.visibility = View.GONE
                frameLayout.visibility = View.VISIBLE
            }
        }
    }
}