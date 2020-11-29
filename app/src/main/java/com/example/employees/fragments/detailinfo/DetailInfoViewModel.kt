package com.example.employees.fragments.detailinfo

import android.annotation.SuppressLint
import android.app.Application
import android.os.AsyncTask
import android.provider.ContactsContract
import androidx.lifecycle.AndroidViewModel
import com.example.employees.data.DatabaseEmployee
import com.example.employees.pojo.Employee
import com.example.employees.pojo.Specialty

class DetailInfoViewModel(application: Application) : AndroidViewModel(application) {
    private val database = DatabaseEmployee.getInstance(context = application.applicationContext)

    fun getEmployeeById(id: Int): Employee {
        return GetEmployeeByIdTask().execute(id).get()
    }

    @SuppressLint("StaticFieldLeak")
    private inner class GetEmployeeByIdTask : AsyncTask<Int, Void, Employee>() {
        override fun doInBackground(vararg params: Int?): Employee? =
            params[0]?.let { database?.employeeDao()?.getEmployeeById(employeeId = it) }

    }

    fun insertSpeciality(speciality: Specialty) {
        InsertSpecialityTask().execute(speciality)
    }

    private inner class InsertSpecialityTask : AsyncTask<Specialty, Void, Void>() {
        override fun doInBackground(vararg params: Specialty?): Void? {
            params[0]?.let { database?.employeeDao()?.insertSpeciality(speciality = it) }
            return null
        }
    }
}