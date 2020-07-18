package com.deved.myepxinperu.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.deved.myepxinperu.databinding.FragmentProfileBinding
import com.deved.myepxinperu.ui.common.gonnaToClass
import com.deved.myepxinperu.ui.security.logIn.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.android.get

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val fireAuth: FirebaseAuth = get()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        events()
    }

    private fun events() {
        with(binding) {
            materialButtonLogOut.setOnClickListener {
                fireAuth.signOut()
                activity?.gonnaToClass(LoginActivity::class.java)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProfileFragment()
    }
}