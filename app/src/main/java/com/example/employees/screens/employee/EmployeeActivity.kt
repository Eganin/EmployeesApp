package com.example.employees.screens.employee

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.employees.R
import com.example.employees.adapters.EmployeeAdapter
import com.example.employees.adapters.SpecialityAdapter
import com.example.employees.exceptions.ExceptionFromNavigationView
import com.example.employees.fragments.addemployee.AddEmployeeFragment
import com.example.employees.fragments.detailinfo.DetailInfoFragment
import com.example.employees.fragments.list.ListEmployeesFragment
import com.example.employees.fragments.speciality.SpecialityFragment
import kotlinx.android.synthetic.main.activity_main.*

class EmployeeActivity : AppCompatActivity(), EmployeeAdapter.OnEmployeeClick,
    SpecialityAdapter.OnClickSpeciality, AddEmployeeFragment.SelectedImage {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().apply {
                add(
                    R.id.main_container,
                    ListEmployeesFragment.newInstance(firstStart = true, specialty = null)
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

    override fun clickSpeciality(specialityText: String) {
        supportFragmentManager.beginTransaction().apply {
            add(
                R.id.main_container,
                ListEmployeesFragment.newInstance(firstStart = false, specialty = specialityText)
                    .apply { setClickListener(listener = this@EmployeeActivity) })
            addToBackStack(null)
            commit()
        }
    }

    override fun getImageFromIntent(data: Intent?): Bitmap {
        val imageUri = data?.data
        val imageStream = contentResolver.openInputStream(imageUri!!)
        return BitmapFactory.decodeStream(imageStream)
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
                            ListEmployeesFragment.newInstance(firstStart = false, specialty = null)
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