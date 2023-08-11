package com.aid098.carrepair

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.os.Bundle
import com.aid098.carrepair.Fragments.MainFragment


class MainActivity : AppCompatActivity(){

    private lateinit var toolbar: Toolbar
    private lateinit var sharedPreferences: SharedPreferences

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

        val carName = intent.getStringExtra("carName")
        val mileage = intent.getStringExtra("mileage")

        val bundle = Bundle()
        bundle.putString("carName", carName)
        bundle.putString("mileage", mileage)

        supportFragmentManager.beginTransaction().add(R.id.fragment_container,MainFragment::class.java, bundle).commit()
    }

}


