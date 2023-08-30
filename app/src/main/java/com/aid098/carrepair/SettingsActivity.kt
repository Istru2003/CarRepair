package com.aid098.carrepair

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.materialswitch.MaterialSwitch
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class SettingsActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    private lateinit var materialSwitch: MaterialSwitch
    private lateinit var topAppBar: MaterialToolbar
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var displayName: String

    private lateinit var googleSignInButton: Button
    private var isUserSignedIn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)

        // Obtain an instance of FirebaseAuth
        auth = FirebaseAuth.getInstance()

        // Configure Google authentication options
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.your_web_client_id))
            .requestEmail()
            .build()

        // Create a GoogleSignInClient
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // Set up the Google Sign-In button click listener
        googleSignInButton = findViewById<Button>(R.id.gSignInBtn)
        isUserSignedIn = GoogleSignIn.getLastSignedInAccount(this) != null
        updateSignInButtonText()

        googleSignInButton.setOnClickListener {
            if (isUserSignedIn) {
                signOutGoogle()
            } else {
                signInGoogle()
            }
        }

        materialSwitch = findViewById(R.id.MaterialSwitch1)
        topAppBar = findViewById(R.id.topAppBar_settings)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val isDarkTheme = sharedPreferences.getBoolean("isDarkTheme", false)
        materialSwitch.isChecked = isDarkTheme
        displayName = sharedPreferences.getString("displayName", getString(R.string.settings_name))!!

        findViewById<TextView>(R.id.Nickname).text = displayName

        materialSwitch.setOnCheckedChangeListener { _, isChecked ->
            val newTheme = if (isChecked) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
            val editor = sharedPreferences.edit()
            editor.putBoolean("isDarkTheme", isChecked)
            editor.apply()
            AppCompatDelegate.setDefaultNightMode(newTheme)
        }

        topAppBar.setNavigationOnClickListener {
            finish()
        }


        val items = listOf(
            getString(R.string.RU),
            getString(R.string.RO),
            getString(R.string.EN)
        )
        val adapter = ArrayAdapter(this, R.layout.language_list, items)
        (findViewById<AutoCompleteTextView>(R.id.auto_complete)).setAdapter(adapter)

    }


    private fun updateSignInButtonText() {
        googleSignInButton.text = if (isUserSignedIn) {
            getString(R.string.text_button_out)
        } else {
            getString(R.string.text_button_in)
        }
    }

    private fun signInGoogle(){
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    private fun signOutGoogle() {
        googleSignInClient.signOut().addOnCompleteListener(this) {
            displayName = getString(R.string.settings_name)
            val editor = sharedPreferences.edit()
            editor.putString("displayName", displayName)
            editor.apply()
            googleSignInButton.text = getString(R.string.text_button_in)
            isUserSignedIn = false
            findViewById<TextView>(R.id.Nickname).text = displayName
            updateSignInButtonText()
        }
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result ->
        if (result.resultCode == Activity.RESULT_OK){
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleResult(task)
        }
    }

    private fun handleResult(task: Task<GoogleSignInAccount>) {
        if(task.isSuccessful){
            val account : GoogleSignInAccount? = task.result
            if(account != null){
                updateUI(account)
            }
        }else{
            Toast.makeText(this, task.exception.toString() , Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                displayName = account.displayName ?: ""
                findViewById<TextView>(R.id.Nickname).text = displayName
                val editor = sharedPreferences.edit()
                editor.putString("displayName", displayName)
                editor.apply()
                isUserSignedIn = true
                updateSignInButtonText()
            } else {
                Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }



}
