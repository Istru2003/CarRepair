package com.aid098.carrepair

import android.app.Application
import android.content.Context
import android.content.Intent
import java.util.Locale

class MyApplication : Application() {

    companion object {
        fun setLocale(context: Context, languageCode: String) {
            val config = context.resources.configuration
            val locale = Locale(languageCode)
            Locale.setDefault(locale)
            config.setLocale(locale)
            context.resources.updateConfiguration(config, context.resources.displayMetrics)
        }

        fun restartActivity(context: Context) {
            val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)
            intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            context.startActivity(intent)
        }
    }
}
