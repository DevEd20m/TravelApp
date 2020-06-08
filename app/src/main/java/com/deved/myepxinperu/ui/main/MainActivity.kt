package com.deved.myepxinperu.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import com.deved.domain.Department
import com.deved.myepxinperu.R
import com.deved.myepxinperu.databinding.ActivityMainBinding
import com.deved.myepxinperu.ui.detail.DetailFragment
import com.deved.myepxinperu.ui.home.HomeFragment

class MainActivity : AppCompatActivity(), HomeFragment.HomeFragmentListener {
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

    private fun changeFragment(fragment:Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayoutMain,fragment)
            addToBackStack(null)
        }.commit()
    }
    override fun goToDetail(it: Department) {
        changeFragment(DetailFragment.newInstance(it.name,it.place?.name))
    }

    override fun onDestroy() {
        OrdersCommandProcessor.clear()
        super.onDestroy()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) super.onBackPressed()
        else finish()
    }

    companion object {
        const val MENU_ITEM = "menu_item"
    }
}
