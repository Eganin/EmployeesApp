package com.example.employees.screens.employee

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.example.employees.R
import com.example.employees.adapters.EmployeeAdapter
import com.example.employees.adapters.SpecialityAdapter
import com.example.employees.comon.SampleBottomSheet
import com.example.employees.exceptions.ExceptionFromNavigationView
import com.example.employees.fragments.addemployee.AddEmployeeFragment
import com.example.employees.fragments.detailinfo.DetailInfoFragment
import com.example.employees.fragments.detailinfo.DetailInfoViewModel
import com.example.employees.fragments.list.ListEmployeeViewModel
import com.example.employees.fragments.list.ListEmployeesFragment
import com.example.employees.fragments.speciality.SpecialityFragment
import com.example.employees.pojo.Employee
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class EmployeeActivity : AppCompatActivity(), EmployeeAdapter.OnEmployeeClick,
    SpecialityAdapter.OnClickSpeciality, AddEmployeeFragment.CreateEmployee , SampleBottomSheet.BottomSheetListener {

    val fragment = ListEmployeesFragment.newInstance(specialty = null)
        .apply { setClickListener(listener = this@EmployeeActivity) }
    private val uiScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            createListEmployeeFragment()
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
        createListEmployeeFragment()
    }

    override fun afterCreateEmployee() {
        createListEmployeeFragment()
    }

    override fun positiveClick(id : Int) {
        click(id=id)
    }

    override fun negativeClick(id : Int) {
         val viewModel =
            ViewModelProviders.of(this@EmployeeActivity)[ListEmployeeViewModel::class.java]
        val detailViewModel = ViewModelProviders.of(this@EmployeeActivity)[DetailInfoViewModel::class.java]
        uiScope.launch { viewModel.deleteEmployee(employee = detailViewModel.getEmployeeById(id=id)) }
    }

    private fun createListEmployeeFragment(){
        supportFragmentManager.beginTransaction().apply {
            add(
                R.id.main_container,
                fragment)
            addToBackStack(null)
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
                        addToBackStack(null)
                        commit()
                    }
                    true
                }

                R.id.employees_menu -> {
                    supportFragmentManager.beginTransaction().apply {
                        replace(
                            R.id.main_container,
                            fragment)
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