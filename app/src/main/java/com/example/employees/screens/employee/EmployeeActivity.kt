package com.example.employees.screens.employee

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.employees.R
import com.example.employees.adapters.EmployeeAdapter
import com.example.employees.fragments.detailinfo.DetailInfoFragment
import com.example.employees.fragments.list.ListEmployeesFragment

class EmployeeActivity : AppCompatActivity(), EmployeeAdapter.OnEmployeeClick {

    private val listEmployeesFragment = ListEmployeesFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().apply {
            add(R.id.main_container, listEmployeesFragment)
            commit()
        }

        listEmployeesFragment.setClickListener(this@EmployeeActivity)

    }

    override fun click(id: Int) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.main_container, DetailInfoFragment.newInstance(id = id))
            commit()
        }
    }
}