package com.deved.myepxinperu.ui.security.logIn

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.deved.data.repository.SecurityRepository
import com.deved.domain.User
import com.deved.interactors.LogIn
import com.deved.myepxinperu.data.server.FirebaseSecurityDataSource
import com.deved.myepxinperu.databinding.ActivityLoginBinding
import com.deved.myepxinperu.ui.common.UserSingleton
import com.deved.myepxinperu.ui.common.getViewModel
import com.deved.myepxinperu.ui.common.gonnaToClass
import com.deved.myepxinperu.ui.common.toast
import com.deved.myepxinperu.ui.main.MainActivity
import com.deved.myepxinperu.ui.security.register.RegisterActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {
    private lateinit var fireAuth: FirebaseAuth
    private lateinit var fireFirestore: FirebaseFirestore
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpViewModel()
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
        if(fireAuth.currentUser != null){
            gonnaToClass(MainActivity::class.java)
        }
    }
    private fun setUpViewModel() {
        viewModel = getViewModel {
            fireAuth = FirebaseAuth.getInstance()
            fireFirestore = FirebaseFirestore.getInstance()
            val useCase = LogIn(SecurityRepository(FirebaseSecurityDataSource(fireAuth, fireFirestore)))
            LoginViewModel(useCase)
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
