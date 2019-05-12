package com.yesayasoftware.learning.ui.categories

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.lifecycle.ViewModel
import androidx.transition.TransitionManager
import com.basgeekball.awesomevalidation.AwesomeValidation
import com.basgeekball.awesomevalidation.ValidationStyle
import com.basgeekball.awesomevalidation.utility.RegexTemplate
import com.yesayasoftware.learning.R
import com.yesayasoftware.learning.data.db.entity.CategoryEntry
import com.yesayasoftware.learning.data.network.ApiService
import com.yesayasoftware.learning.data.network.RetrofitBuilder
import com.yesayasoftware.learning.internal.TokenManager
import com.yesayasoftware.learning.internal.Utils
import kotlinx.android.synthetic.main.activity_category.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryViewModel : ViewModel() {
    private val TAG = "CategoryViewModel"
    private var service: ApiService? = null
    var call: Call<CategoryEntry>? = null

    private var validator: AwesomeValidation? = null
    private var formContainer: LinearLayout? = null

    init {
        validator = AwesomeValidation(ValidationStyle.TEXT_INPUT_LAYOUT)
    }

    fun store(categoryActivity: CategoryActivity, retrofitBuilder: RetrofitBuilder, tokenManager: TokenManager, name: String, description: String) {
        setupRules(categoryActivity)
        service = retrofitBuilder.createServiceWithAuth(ApiService::class.java, tokenManager)

        validator?.clear()

        if (validator?.validate()!!) {
            showLoading(categoryActivity)

            call = service!!.categories(name, description)

            call!!.enqueue(object : Callback<CategoryEntry> {
                override fun onResponse(call: Call<CategoryEntry>, response: Response<CategoryEntry>) {

                    Log.w(TAG, "onResponse: $response")

                    if (response.isSuccessful) {
                        Log.w(TAG, "onResponse: ${response.body()}")

                        showForm(categoryActivity)
                    } else {
                        showForm(categoryActivity)

                        handleErrors(categoryActivity, response.errorBody()!!, response.code())
                    }
                }

                override fun onFailure(call: Call<CategoryEntry>, t: Throwable) {
                    showForm(categoryActivity)

                    Log.w(TAG, "onFailure: " + t.message)
                }
            })
        }
    }

    private fun setupRules(categoryActivity: CategoryActivity) {
        validator?.addValidation(categoryActivity, R.id.til_cat_name, RegexTemplate.NOT_EMPTY, R.string.err_cat_name)
        validator?.addValidation(categoryActivity, R.id.til_cat_description, RegexTemplate.NOT_EMPTY, R.string.err_cat_description)
    }

    private fun showLoading(categoryActivity: CategoryActivity) {
        TransitionManager.beginDelayedTransition(categoryActivity.containerCategory)
        formContainer?.visibility = View.GONE
        categoryActivity.loaderCategory.visibility = View.VISIBLE
    }

    private fun showForm(categoryActivity: CategoryActivity) {
        TransitionManager.beginDelayedTransition(categoryActivity.containerCategory)
        formContainer?.visibility = View.VISIBLE
        categoryActivity.loaderCategory.visibility = View.GONE
    }

    private fun handleErrors(categoryActivity: CategoryActivity, response: ResponseBody, code : Int) {
        val apiError = Utils.convertErrors(response)

        if (code == 422) {
            for (error in apiError?.errors?.entries!!) {
                if (error.key == "name") {
                    categoryActivity.til_cat_name?.error = error.value[0]
                }
                if (error.key == "description") {
                    categoryActivity.til_cat_description?.error = error.value[0]
                }
            }
        } else if (code == 401) {
            categoryActivity.til_cat_name?.error = apiError?.message
        }
    }
}