package com.aid098.carrepair

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.appintro.AppIntro
import com.github.appintro.AppIntroCustomLayoutFragment
import com.github.appintro.AppIntroFragment

class IntroActivity : AppIntro() {

    // Переменные для работы с SharedPreferences и фрагментом выбора
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var choiceFragment: IntroChoiceFragment

    // ViewModel для управления данными о пробеге
    private lateinit var mileageViewModel: MileageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Инициализация ViewModel для управления данными о пробеге
        mileageViewModel = ViewModelProvider(this).get(MileageViewModel::class.java)

        // Получение экземпляра SharedPreferences для проверки первого запуска
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        // Создание фрагмента выбора
        choiceFragment = IntroChoiceFragment.newInstance()
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
        addSlide(choiceFragment)
    }

    private fun goToMainActivity() {
        // Переход на главный экран
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onSlideChanged(oldFragment: Fragment?, newFragment: Fragment?) {
        super.onSlideChanged(oldFragment, newFragment)

        // Проверяем, является ли новый фрагмент слайдом "Choice" по сравнению тегов
        if (newFragment is IntroChoiceFragment) {
            // Делаем необходимые действия при смене на слайд "Choice"
        }
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)

        // Создаем интент с данными о пробеге и завершаем активити
        val intent = Intent()
            .putExtra("mileage", choiceFragment.getMileage())
        setResult(RESULT_OK, intent)
        finish()
    }

    // Функция для получения цвета из атрибутов темы
    @ColorInt
    fun Context.getColorFromAttr(@AttrRes attrColor: Int): Int {
        val typedArray = theme.obtainStyledAttributes(intArrayOf(attrColor))
        val textColor = typedArray.getColor(0, 0)
        typedArray.recycle()
        return textColor
    }

    companion object {
        // Константа для идентификации тега слайда
        private const val ARG_TITLE = "arg_title"
    }

}



