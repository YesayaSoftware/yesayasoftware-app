package com.yesayasoftware.learning.internal

class AccessToken {
    lateinit var token_type: String
    var expires_in: Int = 0
    var access_token: String? = null
    var refresh_token: String? = null
}