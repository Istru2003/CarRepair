package com.aid098.carrepair

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.os.Bundle
import com.aid098.carrepair.Fragments.MainFragment


class MainActivity : AppCompatActivity(), Communicator{

    private lateinit var toolbar: Toolbar
    private lateinit var sharedPreferences: SharedPreferences

    private var carName: String? = null
    private var mileage: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


        supportFragmentManager.beginTransaction().replace(R.id.fragment_container,MainFragment()).commit()

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val isFirstRun = sharedPreferences.getBoolean("isFirstRun", true)

        if (isFirstRun) {
            val intent = Intent(this, IntroActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    fun getCarName(): String? {
        return carName
    }

    fun getMileage(): Int? {
        return mileage
    }

    override fun passData(carName: String, mileage: Int) {
        this.carName = carName
        this.mileage = mileage
    }



}


