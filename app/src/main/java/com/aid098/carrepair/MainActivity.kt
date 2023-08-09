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
    private lateinit var mainFragment: MainFragment

    var mileage: Int = 0
    var name: String = ""

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

        val mileage = intent.getIntExtra("mileage", 0)
        val name = intent.getStringExtra("name")

        mainFragment = MainFragment()
        introChoiceFragment = IntroChoiceFragment.newInstance()
        introChoiceFragment.setMileage(mileage)


    }

    // Other overridden functions

    override fun setActionBarTitle(title: String) {
        supportActionBar?.title = name
    }

    // Implement the new function to get the car name
    override fun getCarName(): String {
        return introChoiceFragment.getName()
    }

    override fun onResume() {
        super.onResume()

    }
}


