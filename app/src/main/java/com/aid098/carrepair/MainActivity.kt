package com.aid098.carrepair

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.PopupWindow
import androidx.core.content.ContextCompat
import com.aid098.carrepair.Fragments.MainFragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson

class MainActivity : AppCompatActivity(){

    data class Car(val name: String?, val mileage: Int)

    private var carList: MutableList<Car> = mutableListOf()

    private var carMenuPopup: PopupWindow? = null

    private lateinit var toolbar: MaterialToolbar
    private lateinit var sharedPreferences: SharedPreferences

    private var acceptButtonPressed: Boolean = false
    private var navigationIconDrawable: Drawable? = null

    private var selectedCarIndex: Int = 0

    private var newCarAdded: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.topAppBar)
        setSupportActionBar(toolbar)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val isDarkTheme = sharedPreferences.getBoolean("isDarkTheme", false) // Provide a default value

        if (isDarkTheme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        val isFirstRun = sharedPreferences.getBoolean("isFirstRun", true)

        if (isFirstRun) {
            val intent = Intent(this, IntroActivity::class.java)
            startActivity(intent)
            finish()
        }

        val carName = sharedPreferences.getString("carName", "")
        val mileage = sharedPreferences.getInt("mileage", 0)

        toolbar.title = carName
        carList.add(Car(carName, mileage))

        val bundle = Bundle()
        bundle.putInt("mileage", mileage)

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, MainFragment::class.java, bundle)
            .commit()

        navigationIconDrawable = null
        toolbar.navigationIcon = navigationIconDrawable

        selectedCarIndex = MyApplication.getSelectedCarIndex(this)


    }

    override fun onPause() {
        super.onPause()

        // Save car list
        val carListJson = Gson().toJson(carList)
        sharedPreferences.edit().putString("carList", carListJson).apply()

        // Save selected index
        sharedPreferences.edit().putInt("selectedIndex", selectedCarIndex).apply()

        // Save acceptButtonPressed value
        sharedPreferences.edit().putBoolean("acceptButtonPressed", acceptButtonPressed).apply()

        // Save newCarAdded value
        sharedPreferences.edit().putBoolean("newCarAdded", newCarAdded).apply()
    }



    override fun onResume() {
        super.onResume()

        // Get saved car list
        val carListJson = sharedPreferences.getString("carList", "")

        val editor = sharedPreferences.edit()

        if (!carListJson.isNullOrEmpty()) {
            carList = Gson().fromJson(carListJson, Array<Car>::class.java).toMutableList()
        }

        // Get selected index
        selectedCarIndex = sharedPreferences.getInt("selectedIndex", 0)

        // Restore toolbar title
        toolbar.title = carList[selectedCarIndex].name

        // Restore acceptButtonPressed value
        acceptButtonPressed = sharedPreferences.getBoolean("acceptButtonPressed", false)

        // Restore newCarAdded value
        newCarAdded = sharedPreferences.getBoolean("newCarAdded", false)

        val updatedJson = intent.getStringExtra("updatedCarList")
        if (updatedJson != null) {
            carList = Gson().fromJson(updatedJson, Array<Car>::class.java).toMutableList()

            // Save
            editor.putString("carList", updatedJson).apply()
        }
        // Update navigation icon based on acceptButtonPressed
        updateNavigationIcon()
    }




    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.nav_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.addingacar -> {
                showAddCarDialog()
                true
            }
            R.id.nav_settings_id -> {
                val intent = Intent(this, SettingsActivity::class.java)
                intent.putExtra("carList", Gson().toJson(carList))
                startActivity(intent)
                true
            }
            android.R.id.home -> { // Handle navigation icon click
                showCarDropdownMenu()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun showAddCarDialog() {
        val dialogView = layoutInflater.inflate(R.layout.add_car_dialog, null) // Inflate the custom dialog layout

        val carNameEditText = dialogView.findViewById<EditText>(R.id.CarName_dialog)
        val mileageEditText = dialogView.findViewById<EditText>(R.id.Mileage_dialog)

        // Save updated list
        val updatedJson = Gson().toJson(carList)
        val editor = sharedPreferences.edit()
        editor.putString("carList", updatedJson).apply()

        val alertDialog = MaterialAlertDialogBuilder(this, R.style.ThemeOverlay_App_MaterialAlertDialog)
            .setTitle(resources.getString(R.string.text_intro_info))
            .setView(dialogView)
            .setNegativeButton(resources.getString(R.string.dialog_cancel)) { dialog, which ->
                // Respond to neutral button press
            }
            .setPositiveButton(resources.getString(R.string.dialog_accept)) { dialog, which ->
                // Respond to positive button press
                val carName = carNameEditText.text.toString().trim()
                val mileageStr = mileageEditText.text.toString().trim()

                // Process the entered data
                val mileage = mileageStr.toIntOrNull() ?: 0

                if (carName.isEmpty() || mileageStr.isEmpty()) {
                    // Display a warning dialog
                    val emptyFieldDialog = MaterialAlertDialogBuilder(
                        this,
                        R.style.ThemeOverlay_App_MaterialAlertDialog
                    )
                        .setTitle(resources.getString(R.string.warning_dialog))
                        .setMessage(resources.getString(R.string.warning_description))
                        .setNegativeButton(resources.getString(R.string.dialog_cancel)) { dialog, which ->
                            // Respond to Cancel button press
                        }
                        .create()

                    emptyFieldDialog.show()
                } else {
                    // Process the entered data
                    carList.add(Car(carName, mileage))
                    newCarAdded = true

                    // Update UI to reflect the new car
                    // For example, you might need to update a RecyclerView or ListView here

                    acceptButtonPressed = true
                    updateNavigationIcon()
                }
            }

        alertDialog.show()
    }



    private fun updateNavigationIcon() {
        if (newCarAdded) {
            navigationIconDrawable = ContextCompat.getDrawable(this, R.drawable.down)
            toolbar.navigationIcon = navigationIconDrawable
        } else {
            navigationIconDrawable = null
            toolbar.navigationIcon = navigationIconDrawable
        }
    }

    private fun dismissCarMenuPopup() {
        carMenuPopup?.dismiss()
        carMenuPopup = null
    }

    private fun showCarDropdownMenu() {
        dismissCarMenuPopup() // Dismiss the previous popup if exists

        val carMenu = PopupMenu(this, toolbar)
        carList.forEachIndexed { index, car ->
            carMenu.menu.add(Menu.NONE, index, Menu.NONE, car.name ?: "Unnamed Car")
        }

        carMenu.setOnMenuItemClickListener { menuItem ->
            val selectedCar = carList[menuItem.itemId]
            MyApplication.setSelectedCarIndex(this, selectedCarIndex)
            toolbar.title = selectedCar.name ?: ""

            // Update mileage in the MainFragment
            val mainFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as MainFragment?
            mainFragment?.updateMileage(selectedCar.mileage)

            true
        }

        carMenu.show() // Show the popup menu directly
    }



}


