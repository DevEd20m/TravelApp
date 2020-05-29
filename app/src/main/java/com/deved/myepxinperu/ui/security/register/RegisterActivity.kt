package com.deved.myepxinperu.ui.security.register

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.deved.myepxinperu.R
import com.deved.myepxinperu.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewmodel :RegisterViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        val view = super.onCreateView(name, context, attrs)
        binding.materialButtonRegister.setOnClickListener(this)
        return view
    }

    override fun onClick(button: View?) {
        when (button?.id) {
            R.id.materialButtonRegister -> {
            }
        }
    }
}
