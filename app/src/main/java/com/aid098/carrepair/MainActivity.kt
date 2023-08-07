package com.aid098.carrepair

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar // Переменная для хранения панели инструментов (Toolbar)
    private lateinit var sharedPreferences: SharedPreferences // Переменная для работы с SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Устанавливаем содержимое активити из layout-ресурса activity_main

        toolbar = findViewById(R.id.toolbar) // Находим панель инструментов по идентификатору
        setSupportActionBar(toolbar) // Устанавливаем найденную панель инструментов как панель действий

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE) // Получаем доступ к SharedPreferences

        val isFirstRun = sharedPreferences.getBoolean("isFirstRun", true) // Получаем значение флага первого запуска

        if (isFirstRun) {
            val intent = Intent(this, IntroActivity::class.java) // Создаем намерение для перехода к IntroActivity
            startActivity(intent) // Запускаем IntroActivity
            finish() // Закрываем текущую активити (MainActivity)
        }

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val carName = IntroChoiceFragment.newInstance().getName()
        supportActionBar?.title = carName


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu) // Заполняем меню пунктами из ресурса nav_menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.nav_settings_id -> {
                val intent = Intent(this, SettingsActivity::class.java) // Создаем намерение для перехода к SettingsActivity
                startActivity(intent) // Запускаем SettingsActivity
                true
            }
            R.id.nav_about_id -> {
                // Обработка выбора пункта "About" здесь
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}


