package com.example.employees.screens.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.employees.data.DatabaseEmployee

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val database  = DatabaseEmployee.getInstance(context = application.applicationContext)

    var employees = database?.employeeDao()?.getAllEmployees()

    fun getEmployee(){}
}