package com.yesayasoftware.learning.ui.auth.login

import android.content.Intent
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.transition.TransitionManager
import com.basgeekball.awesomevalidation.AwesomeValidation
import com.basgeekball.awesomevalidation.ValidationStyle
import com.yesayasoftware.learning.R
import com.yesayasoftware.learning.data.network.ApiService
import com.yesayasoftware.learning.data.network.RetrofitBuilder
import com.yesayasoftware.learning.internal.AccessToken
import com.yesayasoftware.learning.internal.TokenManager
import com.yesayasoftware.learning.internal.Utils
import com.yesayasoftware.learning.ui.MainActivity
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {
    private val TAG = "LoginViewModel"
    private var service: ApiService? = null
    var call: Call<AccessToken>? = null

    private var validator: AwesomeValidation? = null
    private var formContainer: LinearLayout? = null

    init {
        service = RetrofitBuilder.createService(ApiService::class.java)
        validator = AwesomeValidation(ValidationStyle.TEXT_INPUT_LAYOUT)
    }

    fun login(loginActivity: LoginActivity, tokenManager: TokenManager?, email: String, password: String) {
        setupRules(loginActivity)

        validator?.clear()

        if (validator?.validate()!!) {
            showLoading(loginActivity)

            call = service!!.login(email, password)

            call!!.enqueue(object : Callback<AccessToken> {
                override fun onResponse(call: Call<AccessToken>, response: Response<AccessToken>) {

                    Log.w(TAG, "onResponse: $response")

                    if (response.isSuccessful) {
                        Log.w(TAG, "onResponse: " + response.body())
                        Log.w(TAG, "onResponse String: " + response.body()!!.access_token)

                        tokenManager?.saveToken(response.body()!!)

                        if (tokenManager?.getTokens()?.access_token != null) {
                            loginActivity.startActivity(Intent(loginActivity, MainActivity::class.java))
                            loginActivity.finish()
                        } else {
                            Toast.makeText(loginActivity, "An error occurred, please try again.", Toast.LENGTH_LONG).show()

                            showForm(loginActivity)
                        }
                    } else {
                        showForm(loginActivity)

                        handleErrors(loginActivity, response.errorBody()!!, response.code())
                    }
                }

                override fun onFailure(call: Call<AccessToken>, t: Throwable) {
                    showForm(loginActivity)

                    Log.w(TAG, "onFailure: " + t.message)
                }
            })
        }
    }

    private fun setupRules(loginActivity: LoginActivity) {
        validator?.addValidation(loginActivity, R.id.til_email, Patterns.EMAIL_ADDRESS, R.string.err_email)
        validator?.addValidation(loginActivity, R.id.til_password, "[a-zA-Z0-9]{6,}", R.string.err_password)
    }

    private fun showLoading(loginActivity: LoginActivity) {
        TransitionManager.beginDelayedTransition(loginActivity.container)
        formContainer?.visibility = View.GONE
        loginActivity.loader.visibility = View.VISIBLE
    }

    private fun showForm(loginActivity: LoginActivity) {
        TransitionManager.beginDelayedTransition(loginActivity.container)
        formContainer?.visibility = View.VISIBLE
        loginActivity.loader.visibility = View.GONE
    }

    private fun handleErrors(loginActivity: LoginActivity, response: ResponseBody, code : Int) {
        val apiError = Utils.convertErrors(response)

        if (code == 422) {
            for (error in apiError?.errors?.entries!!) {
                if (error.key == "email") {
                    loginActivity.til_email?.error = error.value[0]
                }
                if (error.key == "password") {
                    loginActivity.til_password?.error = error.value[0]
                }
            }
        } else if (code == 401) {
            loginActivity.til_email?.error = apiError?.message
        }
    }
}