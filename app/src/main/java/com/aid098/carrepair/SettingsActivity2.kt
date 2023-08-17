package com.aid098.carrepair

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.materialswitch.MaterialSwitch

class SettingsActivity2 : AppCompatActivity() {

    private lateinit var materialSwitch: MaterialSwitch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity_2)

        materialSwitch = findViewById(R.id.MaterialSwitch1)
        // To check a switch
        materialSwitch.isChecked = false

        // To listen for a switch's checked/unchecked state changes
        materialSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            // Responds to switch being checked/unchecked
        }
    }
}