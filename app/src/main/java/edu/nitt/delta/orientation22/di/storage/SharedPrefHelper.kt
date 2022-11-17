package edu.nitt.delta.orientation22.di.storage

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class SharedPrefHelper@Inject constructor(context: Context) {

    private val sharedPreferences: SharedPreferences

    private val storageName = "Orientation22"

    init {
        sharedPreferences = context.getSharedPreferences(
            storageName,
            Context.MODE_PRIVATE
        )
    }

    object Key {

    }

    object Default {

    }


}

