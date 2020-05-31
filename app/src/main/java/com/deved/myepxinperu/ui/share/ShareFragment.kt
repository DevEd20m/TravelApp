package com.deved.myepxinperu.ui.share

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.deved.myepxinperu.R
import com.deved.myepxinperu.databinding.FragmentShareBinding

class ShareFragment : Fragment() {
    private lateinit var binding :FragmentShareBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShareBinding.inflate(inflater,container,false)
        
        return binding.root
    }

}
