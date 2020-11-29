package com.example.employees.fragments.speciality

import android.app.Application
import android.provider.ContactsContract
import androidx.lifecycle.AndroidViewModel
import com.example.employees.data.DatabaseEmployee
import com.example.employees.pojo.Specialty

class SpecialityViewModel(application: Application) : AndroidViewModel(application) {
    private val database = DatabaseEmployee.getInstance(application.applicationContext)

    var specialities = database?.employeeDao()?.getAllSpecialities()
}