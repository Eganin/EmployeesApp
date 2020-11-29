package com.example.employees.screens.employee

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.employees.R
import com.example.employees.adapters.EmployeeAdapter
import com.example.employees.adapters.SpecialityAdapter
import com.example.employees.exceptions.ExceptionFromNavigationView
import com.example.employees.fragments.detailinfo.DetailInfoFragment
import com.example.employees.fragments.list.ListEmployeesFragment
import com.example.employees.fragments.speciality.SpecialityFragment
import kotlinx.android.synthetic.main.activity_main.*

class EmployeeActivity : AppCompatActivity(), EmployeeAdapter.OnEmployeeClick,
    DetailInfoFragment.OnClickBackToList, SpecialityAdapter.OnClickSpeciality {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().apply {
                add(
                    R.id.main_container,
                    ListEmployeesFragment.newInstance(specialty = null)
                        .apply { setClickListener(listener = this@EmployeeActivity) })
                commit()
            }
        }

        try {
            handlerClickNavigationView()
        } catch (e: ExceptionFromNavigationView) {
            e.printStackTrace()
        }

    }

    override fun click(id: Int) {

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.main_container, DetailInfoFragment.newInstance(id = id).apply {
                setClickListener(listener = this@EmployeeActivity)
            })
            commit()
        }
    }

    override fun clickToBack() {
        supportFragmentManager.beginTransaction().apply {
            replace(
                R.id.main_container,
                ListEmployeesFragment.newInstance(null)
                    .apply { setClickListener(listener = this@EmployeeActivity) })

            commit()
        }
    }

    override fun clickSpeciality(specialityText : String) {
        supportFragmentManager.beginTransaction().apply {
            add(
                R.id.main_container,
                ListEmployeesFragment.newInstance(specialty = specialityText)
                    .apply { setClickListener(listener = this@EmployeeActivity) })
            commit()
        }
    }

    private fun handlerClickNavigationView() {
        navigation_view_main.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.specialities_menu -> {
                    supportFragmentManager.beginTransaction().apply {
                        replace(
                            R.id.main_container,
                            SpecialityFragment().apply { setListener(listener = this@EmployeeActivity) })
                        commit()
                    }
                    true
                }

                R.id.employees_menu -> {
                    supportFragmentManager.beginTransaction().apply {
                        replace(
                            R.id.main_container,
                            ListEmployeesFragment().apply { setClickListener(listener = this@EmployeeActivity) })
                        commit()
                    }
                    true
                }

                else -> {
                    throw ExceptionFromNavigationView()
                }
            }
        }
    }

}