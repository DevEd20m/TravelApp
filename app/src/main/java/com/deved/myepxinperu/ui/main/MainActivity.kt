package com.deved.myepxinperu.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import com.deved.myepxinperu.R
import com.deved.myepxinperu.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBottomNavigationView()
    }

    private fun setupBottomNavigationView() {
        OrdersCommandProcessor.init(this)
        binding.bottomNavigationView.inflateMenu(R.menu.menu_bottom_navigation)
        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            OrdersCommandProcessor.invoke(it.itemId)
            return@setOnNavigationItemSelectedListener true
        }
        binding.bottomNavigationView.selectedItemId = R.id.itemHome
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(MENU_ITEM, getSelectedItem())
    }

    private fun getSelectedItem(): Int {
        binding.bottomNavigationView.menu.forEach {
            if (it.isChecked) {
                return it.itemId
            }
        }
        return 0
    }

    override fun onDestroy() {
        OrdersCommandProcessor.clear()
        super.onDestroy()
    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }

    companion object {
        const val MENU_ITEM = "menu_item"
    }
}
