package com.example.employees.fragments.detailinfo

import android.annotation.SuppressLint
import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.AndroidViewModel
import com.example.employees.data.DatabaseEmployee
import com.example.employees.pojo.Employee

class DetailInfoViewModel(application: Application)  :AndroidViewModel(application) {
    private val database = DatabaseEmployee.getInstance(context = application.applicationContext)

    fun getEmployeeById(id: Int): Employee {
        return GetEmployeeByIdTask().execute(id).get()
    }

    @SuppressLint("StaticFieldLeak")
    inner class GetEmployeeByIdTask : AsyncTask<Int, Void, Employee>() {
        override fun doInBackground(vararg params: Int?): Employee? =
            params[0]?.let { database?.employeeDao()?.getEmployeeById(employeeId = it) }

    }
}