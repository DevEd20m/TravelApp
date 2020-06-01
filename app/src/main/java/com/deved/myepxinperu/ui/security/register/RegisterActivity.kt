package com.deved.myepxinperu.ui.security.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.deved.data.repository.SecurityRepository
import com.deved.interactors.RegisterUser
import com.deved.myepxinperu.R
import com.deved.myepxinperu.data.server.ThePlacesDataSource
import com.deved.myepxinperu.databinding.ActivityRegisterBinding
import com.deved.myepxinperu.ui.common.getViewModel
import com.deved.myepxinperu.ui.common.toast
import com.deved.myepxinperu.ui.security.logIn.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var fbFirestore: FirebaseFirestore
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewmodel: RegisterViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpViewModel()
        setUpObserversViewModel()
        binding.materialButtonRegister.setOnClickListener(this)
    }

    private fun setUpViewModel() {
        viewmodel = getViewModel {
            fbFirestore = FirebaseFirestore.getInstance()
            firebaseAuth = FirebaseAuth.getInstance()
            val useCase = RegisterUser(SecurityRepository(ThePlacesDataSource(firebaseAuth, fbFirestore)))
            RegisterViewModel(useCase)
        }
    }

    private fun setUpObserversViewModel() {
        viewmodel.onMessageError.observe(this,onMessageErrorObserver)
        viewmodel.onMessageSucces.observe(this,onMessageSuccessObserver)
        viewmodel.isViewLoading.observe(this,isViewLoadingObserver)
    }

    override fun onClick(button: View?) {
        when (button?.id) {
            R.id.materialButtonRegister -> {
                val name = binding.textInputEditTextName.text.toString().trim()
                val lastName = binding.textInputEditTextLastName.text.toString().trim()
                val email = binding.textInputEditEmail.text.toString().trim()
                val password = binding.textInputEditPassword.text.toString().trim()
                viewmodel.validateForRegister(name, lastName, email, password)
            }
        }
    }

    private val onMessageErrorObserver = Observer<Any>{
        toast(it.toString())
    }

    private val onMessageSuccessObserver = Observer<Any>{
        toast(it.toString())
        Thread.sleep(1000)
        startActivity(Intent(this,LoginActivity::class.java))
        finish()
    }

    private val isViewLoadingObserver = Observer<Boolean>{
        binding.progressBarRegister.isVisible = it
    }
}
