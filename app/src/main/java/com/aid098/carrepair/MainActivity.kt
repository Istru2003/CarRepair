package com.aid098.carrepair

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.os.Bundle


class MainActivity : AppCompatActivity(),  IntroChoiceFragment.ActionBarTitleListener {

    private lateinit var toolbar: Toolbar
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var introChoiceFragment: IntroChoiceFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val isFirstRun = sharedPreferences.getBoolean("isFirstRun", true)

        if (isFirstRun) {
            val intent = Intent(this, IntroActivity::class.java)
            startActivity(intent)
            finish()
        }

        introChoiceFragment = IntroChoiceFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .replace(R.id.textViewMileage, introChoiceFragment) // Use the initialized fragment
            .commit()

    }

    // Other overridden functions

    override fun setActionBarTitle(title: String) {
        supportActionBar?.title = title
    }

    // Implement the new function to get the car name
    override fun getCarName(): String {
        return introChoiceFragment.getName()
    }

    override fun onResume() {
        super.onResume()

    }
}


