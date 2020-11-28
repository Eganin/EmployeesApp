package com.example.employees.screens.employee

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.employees.R
import com.example.employees.fragments.ListEmployeesFragment

class EmployeeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().apply {
            add(R.id.main_container, ListEmployeesFragment())
            commit()
        }

    }
}