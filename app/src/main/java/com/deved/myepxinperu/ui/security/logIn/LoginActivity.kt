package com.deved.myepxinperu.ui.security.logIn

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.deved.domain.User
import com.deved.myepxinperu.databinding.ActivityLoginBinding
import com.deved.myepxinperu.ui.common.UserSingleton
import com.deved.myepxinperu.ui.common.gonnaToClass
import com.deved.myepxinperu.ui.common.toast
import com.deved.myepxinperu.ui.main.MainActivity
import com.deved.myepxinperu.ui.security.register.RegisterActivity
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.android.get
import org.koin.android.scope.lifecycleScope
import org.koin.android.viewmodel.scope.viewModel

class LoginActivity : AppCompatActivity() {
    private val fireAuth: FirebaseAuth = get()
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by lifecycleScope.viewModel(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpViewModelObservers()
        binding.materialButtonLogin.setOnClickListener {
            val email = binding.textInputEditTextEmail.text.toString()
            val password = binding.textInputEditTextPassword.text.toString()
            viewModel.validateForLogIn(email, password)
        }
        binding.textViewRegister.setOnClickListener {
            gonnaToClass(RegisterActivity::class.java)
        }
    }

    override fun onStart() {
        super.onStart()
        if (fireAuth.currentUser != null) {
            gonnaToClass(MainActivity::class.java)
        }
    }

    private fun setUpViewModelObservers() {
        viewModel.isViewLoading.observe(this, isViewLoadingObserver)
        viewModel.onMessageError.observe(this, onMessageErrorObserver)
        viewModel.onMessageSuccess.observe(this, onMessageSuccessObserver)
    }

    private val isViewLoadingObserver = Observer<Boolean> {
        binding.progressBarLogIn.isVisible = it
    }

    private val onMessageErrorObserver = Observer<Any> {
        toast(it.toString())
    }

    private val onMessageSuccessObserver = Observer<User> {
        UserSingleton.setUid(it.id)
        UserSingleton.setEmail(it.email)
        gonnaToClass(MainActivity::class.java)
    }

}
