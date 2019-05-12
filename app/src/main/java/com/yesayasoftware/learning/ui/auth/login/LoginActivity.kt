package com.yesayasoftware.learning.ui.auth.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.yesayasoftware.learning.R
import com.yesayasoftware.learning.internal.TokenManager
import com.yesayasoftware.learning.ui.MainActivity
import com.yesayasoftware.learning.ui.auth.register.RegisterActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein

class LoginActivity : AppCompatActivity(), KodeinAware {
    private val TAG = "LoginActivity"

    override val kodein by closestKodein()
    private lateinit var viewModel: LoginViewModel
    private var tokenManager: TokenManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        tokenManager = TokenManager.getInstance(getSharedPreferences("prefs", Context.MODE_PRIVATE))

        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        if (tokenManager?.getTokens()?.access_token != null) {
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            finish()
        }

        btn_login.setOnClickListener{
            val email = til_email?.editText?.text.toString()
            val password = til_password?.editText?.text.toString()

            til_email?.error = null
            til_password?.error = null

            viewModel.login(this@LoginActivity, tokenManager, email, password)
        }

        go_to_register.setOnClickListener{
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            finish()
        }
    }
}
