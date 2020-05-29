package com.deved.myepxinperu.ui.security.register

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.deved.data.repository.SecurityRepository
import com.deved.interactors.RegisterUser
import com.deved.myepxinperu.R
import com.deved.myepxinperu.data.server.DataSource
import com.deved.myepxinperu.databinding.ActivityRegisterBinding
import com.deved.myepxinperu.ui.common.getViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User

class RegisterActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var fbFirestore: FirebaseFirestore
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewmodel: RegisterViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewmodel = getViewModel {
            fbFirestore = FirebaseFirestore.getInstance()
            firebaseAuth = FirebaseAuth.getInstance()
            val useCase = RegisterUser(SecurityRepository(DataSource(firebaseAuth, fbFirestore)))
            RegisterViewModel(useCase)
        }
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        val view = super.onCreateView(name, context, attrs)
        binding.materialButtonRegister.setOnClickListener(this)
        return view
    }

    override fun onClick(button: View?) {
        when (button?.id) {
            R.id.materialButtonRegister -> {
                val name = binding.textInputEditTextName.toString().trim()
                val lastName = binding.textInputEditTextLastName.toString().trim()
                val email = binding.textInputEditEmail.toString().trim()
                val password = binding.textInputEditPassword.toString().trim()
                viewmodel.validateForRegister(name,lastName,email, password)
            }
        }
    }
}
