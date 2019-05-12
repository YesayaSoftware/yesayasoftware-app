package com.yesayasoftware.learning.ui.auth.register

import android.content.Intent
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.LinearLayout
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
import kotlinx.android.synthetic.main.activity_login.til_email
import kotlinx.android.synthetic.main.activity_login.til_password
import kotlinx.android.synthetic.main.activity_register.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel: ViewModel() {
    private val TAG = "RegisterViewModel"
    private var service: ApiService? = null
    var call: Call<AccessToken>? = null

    private var validator: AwesomeValidation? = null
    private var formContainer: LinearLayout? = null

    init {
        service = RetrofitBuilder.createService(ApiService::class.java)
        validator = AwesomeValidation(ValidationStyle.TEXT_INPUT_LAYOUT)
    }

    fun register(registerActivity: RegisterActivity, tokenManager: TokenManager?, name: String, email: String, password: String) {
        setupRules(registerActivity)

        validator?.clear()

        if (validator?.validate()!!) {
            showLoading(registerActivity)

            call = service!!.register(name, email, password)

            call!!.enqueue(object : Callback<AccessToken> {
                override fun onResponse(call: Call<AccessToken>, response: Response<AccessToken>) {

                    Log.w(TAG, "onResponse: $response")

                    if (response.isSuccessful) {
                        Log.w(TAG, "onResponse: " + response.body())
                        tokenManager?.saveToken(response.body()!!)
                        registerActivity.startActivity(Intent(registerActivity, MainActivity::class.java))
                        registerActivity.finish()
                    } else {
                        showForm(registerActivity)

                        handleErrors(registerActivity, response.errorBody()!!, response.code())
                    }
                }

                override fun onFailure(call: Call<AccessToken>, t: Throwable) {
                    showForm(registerActivity)

                    Log.w(TAG, "onFailure: " + t.message)
                }
            })
        }
    }

    private fun setupRules(registerActivity: RegisterActivity) {
        validator?.addValidation(registerActivity, R.id.til_email, Patterns.EMAIL_ADDRESS, R.string.err_email)
        validator?.addValidation(registerActivity, R.id.til_password, "[a-zA-Z0-9]{6,}", R.string.err_password)
    }

    private fun showLoading(registerActivity: RegisterActivity) {
        TransitionManager.beginDelayedTransition(registerActivity.containerRegister)
        formContainer?.visibility = View.GONE
        registerActivity.loaderRegister.visibility = View.VISIBLE
    }

    private fun showForm(registerActivity: RegisterActivity) {
        TransitionManager.beginDelayedTransition(registerActivity.containerRegister)
        formContainer?.visibility = View.VISIBLE
        registerActivity.loaderRegister.visibility = View.GONE
    }

    private fun handleErrors(registerActivity: RegisterActivity, response: ResponseBody, code : Int) {
        val apiError = Utils.convertErrors(response)

        if (code == 422) {
            for (error in apiError?.errors?.entries!!) {
                if (error.key == "name")
                    registerActivity.til_name?.error = error.value[0]

                if (error.key == "email")
                    registerActivity.til_email?.error = error.value[0]

                if (error.key == "password")
                    registerActivity.til_password?.error = error.value[0]

            }
        } else if (code == 401) {
            registerActivity.til_email?.error = apiError?.message
        }
    }
}