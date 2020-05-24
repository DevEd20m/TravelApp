package com.deved.myepxinperu.ui.main

import androidx.fragment.app.Fragment

interface OrderCommand {
    fun execute(changeFragment: (Fragment) -> Unit)
}