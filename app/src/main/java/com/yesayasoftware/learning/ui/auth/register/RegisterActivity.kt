package com.yesayasoftware.learning.ui.auth.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.yesayasoftware.learning.R
import com.yesayasoftware.learning.internal.TokenManager
import com.yesayasoftware.learning.ui.MainActivity
import com.yesayasoftware.learning.ui.auth.login.LoginActivity
import com.yesayasoftware.learning.ui.auth.login.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.til_email
import kotlinx.android.synthetic.main.activity_login.til_password
import kotlinx.android.synthetic.main.activity_register.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein

class RegisterActivity : AppCompatActivity(), KodeinAware {
    private val TAG = "RegisterActivity"

    override val kodein by closestKodein()
    private lateinit var viewModel: RegisterViewModel
    private var tokenManager: TokenManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        tokenManager = TokenManager.getInstance(getSharedPreferences("prefs", Context.MODE_PRIVATE))

        viewModel = ViewModelProviders.of(this).get(RegisterViewModel::class.java)

        if (tokenManager?.getTokens()?.access_token != null) {
            startActivity(Intent(this@RegisterActivity, MainActivity::class.java))
            finish()
        }

        btn_register.setOnClickListener{
            val name = til_name?.editText?.text.toString()
            val email = til_email?.editText?.text.toString()
            val password = til_password?.editText?.text.toString()

            til_name?.error = null
            til_email?.error = null
            til_password?.error = null

            viewModel.register(this@RegisterActivity, tokenManager, name, email, password)
        }

        go_to_login.setOnClickListener{
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
            finish()
        }
    }
}
