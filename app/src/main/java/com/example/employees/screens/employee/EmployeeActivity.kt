package com.example.employees.screens.employee

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.employees.R
import com.example.employees.adapters.EmployeeAdapter
import com.example.employees.adapters.SpecialityAdapter
import com.example.employees.comon.SampleBottomSheet
import com.example.employees.exceptions.ExceptionFromNavigationView
import com.example.employees.fragments.addemployee.AddEmployeeFragment
import com.example.employees.fragments.detailinfo.DetailInfoFragment
import com.example.employees.fragments.list.ListEmployeesFragment
import com.example.employees.fragments.speciality.SpecialityFragment
import com.example.employees.pojo.Employee
import kotlinx.android.synthetic.main.activity_main.*

class EmployeeActivity : AppCompatActivity(), EmployeeAdapter.OnEmployeeClick,
    SpecialityAdapter.OnClickSpeciality, AddEmployeeFragment.CreateEmployee , SampleBottomSheet.BottomSheetListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().apply {
                add(
                    R.id.main_container,
                    ListEmployeesFragment.newInstance(specialty = null)
                        .apply { setClickListener(listener = this@EmployeeActivity) })
                addToBackStack(null)
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
            replace(R.id.main_container, DetailInfoFragment.newInstance(id = id))
            addToBackStack(null)
            commit()
        }
    }

    override fun onLongClick(employee: Employee) {
        val dialog = SampleBottomSheet(employee=employee)
        dialog.show(supportFragmentManager , "newDialog")
    }

    override fun clickSpeciality(specialityText: String) {
        supportFragmentManager.beginTransaction().apply {
            add(
                R.id.main_container,
                ListEmployeesFragment.newInstance(specialty = specialityText)
                    .apply { setClickListener(listener = this@EmployeeActivity) })
            addToBackStack(null)
            commit()
        }
    }

    override fun afterCreateEmployee() {
        supportFragmentManager.beginTransaction().apply {
            replace(
                R.id.main_container,
                ListEmployeesFragment.newInstance(specialty = null)
                    .apply { setClickListener(listener = this@EmployeeActivity) })
            addToBackStack(null)
            commit()
        }
    }

    override fun positiveClick(id : Int) {
        click(id=id)
    }

    override fun negativeClick(id : Int) {
        TODO("Not yet implemented")
    }

    private fun handlerClickNavigationView() {
        navigation_view_main.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.specialities_menu -> {
                    supportFragmentManager.beginTransaction().apply {
                        replace(
                            R.id.main_container,
                            SpecialityFragment().apply { setListener(listener = this@EmployeeActivity) })
                        addToBackStack(null)
                        commit()
                    }
                    true
                }

                R.id.employees_menu -> {
                    supportFragmentManager.beginTransaction().apply {
                        replace(
                            R.id.main_container,
                            ListEmployeesFragment.newInstance(specialty = null)
                                .apply { setClickListener(listener = this@EmployeeActivity) })
                        addToBackStack(null)
                        commit()
                    }
                    true
                }

                R.id.add_employee_menu -> {
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.main_container, AddEmployeeFragment())
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