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

    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var choiceFragment: IntroChoiceFragment

    private lateinit var mileageViewModel: MileageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mileageViewModel = ViewModelProvider(this).get(MileageViewModel::class.java)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        choiceFragment = IntroChoiceFragment.newInstance()
    }
    override fun onResume() {
        super.onResume()
        val isFirstRun = sharedPreferences.getBoolean("isFirstRun", true)
        if (isFirstRun) {
            showIntroSlides()
            sharedPreferences.edit().putBoolean("isFirstRun", false).apply()
        } else {
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

        // Check if the newFragment is the "Choice" slide by comparing tags

    }



    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)

        val mileage = choiceFragment.getMileage()
        mileageViewModel.updateMileage(mileage)

        val intent = Intent()
            .putExtra("mileage", choiceFragment.getMileage())
        setResult(RESULT_OK, intent)
        finish()
    }


    @ColorInt
    fun Context.getColorFromAttr(@AttrRes attrColor: Int
    ): Int {
        val typedArray = theme.obtainStyledAttributes(intArrayOf(attrColor))
        val textColor = typedArray.getColor(0, 0)
        typedArray.recycle()
        return textColor
    }

    companion object {
        // Constant for identifying the tag of the slide
        private const val ARG_TITLE = "arg_title"
    }

}


