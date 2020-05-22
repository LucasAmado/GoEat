package com.lucasamado.goeatapp.common

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesManager {

    fun getSharedPreferences(): SharedPreferences {
        return MyApp.instance.getSharedPreferences(
            Constantes.SHARED_PREFS_FILE, Context.MODE_PRIVATE
        )
    }

    fun setStringValue(label: String, value: String) {
        var editor: SharedPreferences.Editor = getSharedPreferences().edit()
        editor.putString(label, value)
        editor.commit()
    }


    fun getSomeStringValue(label: String?): String? {
        return getSharedPreferences().getString(label, null)
    }

}