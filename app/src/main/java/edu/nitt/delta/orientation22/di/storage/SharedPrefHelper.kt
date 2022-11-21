package edu.nitt.delta.orientation22.di.storage

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class SharedPrefHelper @Inject constructor(context: Context) {

    private val sharedPreferences: SharedPreferences

    private val storageName = "Orientation22"

    init {
        sharedPreferences = context.getSharedPreferences(
            storageName,
            Context.MODE_PRIVATE
        )
    }

    object Key {
        const val TEAM ="TEAM"
        const val USER_ROUTE = "ROUTE_NAME"
        const val TOKEN = "TOKEN"
    }

    object Default {
        val TEAM_NAME =""
        val USER_ROUTE= ""
        val TEAM_MEMBERS = ""
        val TOKEN = ""
    }

    var team : String?
        get() = sharedPreferences.getString(
            Key.TEAM,
            Default.TEAM_NAME
        )
        set(value) = sharedPreferences.edit().putString(Key.TEAM,value).apply()

    var currentUserRoute : String?
        get() = sharedPreferences.getString(
            Key.USER_ROUTE,
            Default.USER_ROUTE
        )
        set(value) = sharedPreferences.edit().putString(Key.USER_ROUTE,value).apply()


    var token : String?
        get() = sharedPreferences.getString(
            Key.TOKEN,
            Default.TOKEN
        )
        set(value) = sharedPreferences.edit().putString(Key.TOKEN,value).apply()
    fun clear() {
        sharedPreferences.edit().clear().apply()
    }
}

