package com.aid098.carrepair

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class SettingsActivity : AppCompatActivity() {

    // Инициализация объектов
    private lateinit var auth : FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var languageSpinner: Spinner
    private lateinit var languageBtn: ImageView
    private lateinit var russianLayout: LinearLayout
    private lateinit var romanianLayout: LinearLayout
    private lateinit var englishLayout: LinearLayout

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

        // Получение имени пользователя из предыдущей активности и отображение его
        val displayName = intent.getStringExtra("name")
        val account1 = GoogleSignIn.getLastSignedInAccount(this)
        if (account1 != null) {
            findViewById<TextView>(R.id.Nickname).text = displayName
        } else {
            findViewById<TextView>(R.id.Nickname).text = getString(R.string.settings_name)
        }

        // Инициализация элементов пользовательского интерфейса
        languageSpinner = findViewById(R.id.languageSpinner)
        languageBtn = findViewById(R.id.languageIc)
        russianLayout = findViewById(R.id.russianLayout)
        romanianLayout = findViewById(R.id.romanianLayout)
        englishLayout = findViewById(R.id.englishLayout)
    }

    // Функция для запуска процесса аутентификации через Google
    private fun signInGoogle(){
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
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
        val credential = GoogleAuthProvider.getCredential(account.idToken , null)
        auth.signInWithCredential(credential).addOnCompleteListener{
            if (it.isSuccessful){
                val intent : Intent = Intent(this , SettingsActivity::class.java)
                intent.putExtra("name", account.displayName)
                startActivity(intent)
            }else{
                Toast.makeText(this, it.exception.toString() , Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Обработчик нажатия кнопки "Назад"
    fun onBackClicked(view: View) {
        onBackPressed()
    }
}

