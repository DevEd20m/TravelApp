package com.deved.myepxinperu.ui.security.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.deved.myepxinperu.R
import com.deved.myepxinperu.databinding.ActivityRegisterBinding
import com.deved.myepxinperu.ui.common.toast
import com.deved.myepxinperu.ui.security.logIn.LoginActivity
import org.koin.android.scope.lifecycleScope
import org.koin.android.viewmodel.scope.viewModel

class RegisterActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityRegisterBinding
    private val viewmodel: RegisterViewModel by lifecycleScope.viewModel(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpObserversViewModel()
        binding.materialButtonRegister.setOnClickListener(this)
    }

    private fun setUpObserversViewModel() {
        viewmodel.onMessageError.observe(this, onMessageErrorObserver)
        viewmodel.onMessageSucces.observe(this, onMessageSuccessObserver)
        viewmodel.isViewLoading.observe(this, isViewLoadingObserver)
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

    private val onMessageErrorObserver = Observer<Any> {
        toast(it.toString())
    }

    private val onMessageSuccessObserver = Observer<Any> {
        toast(it.toString())
        Thread.sleep(TIME_SLEEP)
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private val isViewLoadingObserver = Observer<Boolean> {
        binding.progressBarRegister.isVisible = it
    }

    companion object {
        const val TIME_SLEEP: Long = 1000
    }
}
