package com.yesayasoftware.learning.internal

import android.content.SharedPreferences
import android.util.Log
import com.yesayasoftware.learning.ui.MainActivity

class TokenManager private constructor(private val prefs: SharedPreferences) {
    private val editor: SharedPreferences.Editor = prefs.edit()

    val token: AccessToken
        get() {
            val token = AccessToken()
            token.access_token = prefs.getString("ACCESS_TOKEN", null)
            token.refresh_token = prefs.getString("REFRESH_TOKEN", null)
            return token
        }

    fun saveToken(token: AccessToken) {
        editor.putString("ACCESS_TOKEN", token.access_token).commit()
        editor.putString("REFRESH_TOKEN", token.refresh_token).commit()
    }

    fun deleteToken() {
        editor.remove("ACCESS_TOKEN").commit()
        editor.remove("REFRESH_TOKEN").commit()
    }

    companion object {

        private var INSTANCE: TokenManager? = null

        @Synchronized
        internal fun getInstance(prefs: SharedPreferences): TokenManager {
            if (INSTANCE == null) {
                INSTANCE = TokenManager(prefs)
            }
            return INSTANCE as TokenManager
        }
    }

    fun getTokens() : AccessToken {
        val token = AccessToken()
        token.access_token = prefs.getString("ACCESS_TOKEN", null)
        token.refresh_token = prefs.getString("REFRESH_TOKEN", null)
        return token
    }
}