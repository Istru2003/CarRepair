package com.aid098.carrepair

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.AdapterView
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
import java.util.Locale

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

        // Получение экземпляра FirebaseAuth
        auth = FirebaseAuth.getInstance()

        // Настройка параметров для аутентификации через Google
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.your_web_client_id))
            .requestEmail()
            .build()

        // Создание клиента для аутентификации через Google
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // Обработчик кнопки для входа через Google
        findViewById<Button>(R.id.gSignInBtn).setOnClickListener {
            signInGoogle()
        }

        // Инициализация кнопки и флага входа
        googleSignInButton = findViewById<Button>(R.id.gSignInBtn)
        isUserSignedIn = GoogleSignIn.getLastSignedInAccount(this) != null
        updateSignInButtonText()

        // Обработчик кнопки для входа/выхода через Google
        googleSignInButton.setOnClickListener {
            if (isUserSignedIn) {
                signOutGoogle()
            } else {
                signInGoogle()
            }
        }

        materialSwitch = findViewById(R.id.MaterialSwitch1)
        topAppBar = findViewById(R.id.topAppBar_settings)


        // To check a switch

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val isDarkTheme = sharedPreferences.getBoolean("isDarkTheme", false) // Provide a default value
        materialSwitch.isChecked = isDarkTheme
        displayName = sharedPreferences.getString("displayName", getString(R.string.settings_name))!!

        findViewById<TextView>(R.id.Nickname).text = displayName


        // To listen for a switch's checked/unchecked state changes
        materialSwitch.setOnCheckedChangeListener { _, isChecked ->
            val newTheme = if (isChecked) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }

            // Save the new theme preference to SharedPreferences
            val editor = sharedPreferences.edit()
            editor.putBoolean("isDarkTheme", isChecked)
            editor.apply()

            AppCompatDelegate.setDefaultNightMode(newTheme)

        }

        val savedLanguage = sharedPreferences.getString("selectedLanguage", "en") ?: "en"
        setLocale(savedLanguage)


        val items = listOf(
            getString(R.string.RU),
            getString(R.string.RO),
            getString(R.string.EN)
        )

        val autoComplete: AutoCompleteTextView = findViewById(R.id.auto_complete)

        val adapter = ArrayAdapter(this, R.layout.language_list, items)
        autoComplete.setAdapter(adapter)

        autoComplete.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l ->
            val selectedLanguage = adapterView.getItemAtPosition(i) as String
            when (selectedLanguage) {
                getString(R.string.RU) ?: "" -> {
                    MyApplication.setLocale(this, "ru")
                    MyApplication.restartActivity(this)
                }
                getString(R.string.RO) ?: "" -> {
                    MyApplication.setLocale(this, "ro")
                    MyApplication.restartActivity(this)
                }
                getString(R.string.EN) ?: "" -> {
                    MyApplication.setLocale(this, "en")
                    MyApplication.restartActivity(this)
                }
            }
        }




        topAppBar.setNavigationOnClickListener {
            // Handle navigation icon press
            finish()
        }

    }

    private fun updateSignInButtonText() {
        if (isUserSignedIn) {
            googleSignInButton.text = getString(R.string.text_button_out)
        } else {
            googleSignInButton.text = getString(R.string.text_button_in)
        }
    }
    // Функция для запуска процесса аутентификации через Google
    private fun signInGoogle(){
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    private fun signOutGoogle() {
        googleSignInClient.signOut().addOnCompleteListener(this) {
            // Очищаем имя пользователя и сохраняем в SharedPreferences
            displayName = getString(R.string.settings_name)
            val editor = sharedPreferences.edit()
            editor.putString("displayName", displayName)
            editor.apply()

            // Обновляем текст кнопки и флаг входа
            googleSignInButton.text = getString(R.string.text_button_in)
            isUserSignedIn = false
            findViewById<TextView>(R.id.Nickname).text = displayName
            updateSignInButtonText()
        }
    }

    // Обработчик результата аутентификации через Google
    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result ->
        if (result.resultCode == Activity.RESULT_OK){
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleResult(task)
        }
    }

    // Обработка результата аутентификации и вход в приложение
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

    // Обновление пользовательского интерфейса после успешной аутентификации
    private fun updateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                displayName = account.displayName ?: ""
                findViewById<TextView>(R.id.Nickname).text = displayName

                // Сохранение имени пользователя в SharedPreferences
                val editor = sharedPreferences.edit()
                editor.putString("displayName", displayName)
                editor.apply()

                // Обновляем текст кнопки и флаг входа
                isUserSignedIn = true
                updateSignInButtonText()
            } else {
                Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setLocale(languageCode: String) {
        val config = resources.configuration
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)

        // Сохраните выбранный язык в настройках
        val editor = sharedPreferences.edit()
        editor.putString("selectedLanguage", languageCode)
        editor.apply()
    }


}