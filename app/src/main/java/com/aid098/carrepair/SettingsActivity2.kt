package com.aid098.carrepair

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingsActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity_2)

        val materialSwitch = findViewById<SwitchMaterial>(R.id.NightModeSwitch)

        // To check a switch
        materialSwitch.isChecked = true

        // To listen for a switch's checked/unchecked state changes
        materialSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            // Responds to switch being checked/unchecked
            if(isChecked) {
                //Switch is checked
            } else {
                //Switch is uncheked
            }
        }


    }
}