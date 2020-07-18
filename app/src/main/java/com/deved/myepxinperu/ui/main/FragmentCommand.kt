package com.deved.myepxinperu.ui.main

import androidx.fragment.app.Fragment

class FragmentCommand(private val fragment: Fragment) : OrderCommand {
    override fun execute(changeFragment: (Fragment) -> Unit) {
        changeFragment(fragment)
    }
}