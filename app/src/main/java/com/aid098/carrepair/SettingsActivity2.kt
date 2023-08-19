package com.aid098.carrepair

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.materialswitch.MaterialSwitch

class SettingsActivity2 : AppCompatActivity() {

    private lateinit var materialSwitch: MaterialSwitch
    private lateinit var topAppBar: MaterialToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity_2)

        materialSwitch = findViewById(R.id.MaterialSwitch1)
        topAppBar = findViewById(R.id.topAppBar_settings)


        // To check a switch

        materialSwitch.isChecked = false



        // To listen for a switch's checked/unchecked state changes
        materialSwitch.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                materialSwitch.text = getString(R.string.night_mode)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                materialSwitch.text = getString(R.string.light_mode)
                recreate()
            }

        }

        topAppBar.setNavigationOnClickListener {
            // Handle navigation icon press
            finish()
        }


    }

}