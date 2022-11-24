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
        const val USER = "USER"
        const val LEADER ="LEADER"
        const val ROLL = "ROLL"
    }

    object Default {
        val TEAM_NAME =""
        val USER_ROUTE= ""
        val TEAM_MEMBERS = ""
        val TOKEN = ""
        val USER =""
        val LEADER =""
        val ROLL = -1
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

    var user: String?
        get() = sharedPreferences.getString(
            Key.USER,
            Default.USER
        )
        set(value) = sharedPreferences.edit().putString(Key.USER,value).apply()


    var token : String?
        get() = sharedPreferences.getString(
            Key.TOKEN,
            Default.TOKEN
        )
        set(value) = sharedPreferences.edit().putString(Key.TOKEN,value).apply()

    var leaderName : String?
        get() = sharedPreferences.getString(
            Key.LEADER,
            Default.LEADER
        )
        set(value) = sharedPreferences.edit().putString(Key.LEADER,value).apply()

    var rollNo : Int
        get() = sharedPreferences.getInt(
            Key.ROLL,
            Default.ROLL
        )
        set(value) = sharedPreferences.edit().putInt(Key.ROLL,value).apply()
    fun clear() {
        sharedPreferences.edit().clear().apply()
    }
}

