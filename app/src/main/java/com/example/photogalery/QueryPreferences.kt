package com.example.photogalery

import android.content.Context

private const val PREF_SEARCH_QUERY = "searchQuery"

object QueryPreferences {

    fun getStoredQuery(context: Context): String? {
        val prefs = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getString(PREF_SEARCH_QUERY, "")
    }

    fun setStoredQuery(context: Context, query: String) {
        androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
            .edit()
            .putString(PREF_SEARCH_QUERY, query)
            .apply()
    }

}