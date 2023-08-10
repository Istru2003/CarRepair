package com.aid098.carrepair

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.aid098.carrepair.Fragments.IntroInfoFragment
import com.aid098.carrepair.Fragments.MainFragment
import com.github.appintro.AppIntro
import com.github.appintro.AppIntroCustomLayoutFragment
import com.github.appintro.AppIntroFragment

class IntroActivity : AppIntro(), Communicator {

    // Переменные для работы с SharedPreferences и фрагментом выбора
    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var introinfo : IntroInfoFragment

    private lateinit var communicator: Communicator


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Получение экземпляра SharedPreferences для проверки первого запуска
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        introinfo = IntroInfoFragment()


        communicator = this
    }

    override fun onResume() {
        super.onResume()
        // Проверка, первый ли это запуск приложения
        val isFirstRun = sharedPreferences.getBoolean("isFirstRun", true)
        if (isFirstRun) {
            // Если первый запуск, показываем слайды интро
            showIntroSlides()
            // После показа интро, устанавливаем флаг, что приложение больше не первый раз запущено
            sharedPreferences.edit().putBoolean("isFirstRun", false).apply()
        } else {
            // Если это не первый запуск, переходим на главный экран
            goToMainActivity()
        }
    }

    private fun showIntroSlides() {
        // Добавляем слайды интро
        addSlide(
            AppIntroFragment.newInstance(
                title = getString(R.string.hello_start),
                description = getString(R.string.hello_start_description),
                backgroundColor = getColorFromAttr(androidx.appcompat.R.attr.colorPrimary),
                titleColor = Color.BLACK,
                descriptionColor = Color.BLACK
            )
        )
        addSlide(
            AppIntroCustomLayoutFragment.newInstance(R.layout.intro_disclaimer_page)
        )
        addSlide(
            AppIntroCustomLayoutFragment.newInstance(R.layout.intro_privacy_police_page)
        )
        addSlide(introinfo)
    }

    private fun goToMainActivity() {
        // Переход на главный экран
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        val introFragment = currentFragment as IntroInfoFragment
        val mileage = introinfo.editTextMileage.text.toString()
        Log.d("IntroActivity", "Mileage: $mileage")

        communicator.passData(mileage)
        finish()
    }

    override fun passData(editTextData: String) {
        val bundle = Bundle()
        bundle.putString("message", editTextData)

        val transaction = this.supportFragmentManager.beginTransaction()
        val fragmentB = MainFragment()

        fragmentB.arguments = bundle

        transaction.replace(R.id.fragment_container,fragmentB).commit()
    }


    // Функция для получения цвета из атрибутов темы
    @ColorInt
    fun Context.getColorFromAttr(@AttrRes attrColor: Int): Int {
        val typedArray = theme.obtainStyledAttributes(intArrayOf(attrColor))
        val textColor = typedArray.getColor(0, 0)
        typedArray.recycle()
        return textColor
    }

}



