package com.yesayasoftware.learning.ui.categories

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.yesayasoftware.learning.R
import com.yesayasoftware.learning.data.network.RetrofitBuilder
import com.yesayasoftware.learning.internal.TokenManager
import com.yesayasoftware.learning.ui.auth.login.LoginActivity
import kotlinx.android.synthetic.main.activity_category.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein

class CategoryActivity(
    private val retrofitBuilder: RetrofitBuilder
) :  AppCompatActivity(), KodeinAware {
    private val TAG = "CategoryActivity"

    override val kodein by closestKodein()
    private lateinit var viewModel: CategoryViewModel
    private var tokenManager: TokenManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        viewModel = ViewModelProviders.of(this).get(CategoryViewModel::class.java)

        tokenManager = TokenManager.getInstance(getSharedPreferences("prefs", Context.MODE_PRIVATE))


        if (tokenManager?.getTokens()?.access_token == null) {
            startActivity(Intent(this@CategoryActivity, LoginActivity::class.java))
            finish()
        }

        btn_category.setOnClickListener{
            val name = til_cat_name?.editText?.text.toString()
            val description = til_cat_description?.editText?.text.toString()

            til_cat_name?.error = null
            til_cat_description?.error = null

            viewModel.store(this@CategoryActivity, retrofitBuilder, tokenManager!!,  name, description)
        }
    }
}
