package com.lucasamado.goeatapp.common

import android.content.Context
import android.content.SharedPreferences
import com.lucasamado.goeatapp.models.bar.BarDto
import javax.inject.Inject

open class SharedPreferencesManager {

    fun getSharedPreferences(): SharedPreferences {
        val sharedPref = MyApp.instance.getSharedPreferences(
            Constantes.SHARED_PREFS_FILE, Context.MODE_PRIVATE)
        return sharedPref
    }


    fun setSomeStringValue(label: String, value: String) {
        var editor: SharedPreferences.Editor = getSharedPreferences().edit()
        editor.putString(label, value)
        editor.commit()
    }


    fun getSomeStringValue(label: String?): String? {
        return getSharedPreferences().getString(label, null)
    }

    fun deleteStringValue(label: String) {
        var editor: SharedPreferences.Editor = getSharedPreferences().edit()
        editor.remove(label)
        editor.commit()
    }

}