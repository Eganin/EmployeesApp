package com.example.employees.fragments.detailinfo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.employees.data.DatabaseEmployee
import com.example.employees.pojo.Employee
import com.example.employees.pojo.Specialty
import kotlinx.coroutines.*

class DetailInfoViewModel(application: Application) : AndroidViewModel(application) {
    private val database = DatabaseEmployee.getInstance(context = application.applicationContext)

    private var uiScope = CoroutineScope(Dispatchers.Main + SupervisorJob() )
    private lateinit var employee : Employee

    suspend fun getEmployeeById(id: Int): Employee {
        coroutineScope {
            employee = uiScope.async { getEmployeeByIdTask(params = id) }.await()!!
        }
        return employee
    }

    private suspend  fun getEmployeeByIdTask(params : Int?) : Employee? = withContext(Dispatchers.IO) {
        return@withContext params?.let { database?.employeeDao()?.getEmployeeById(employeeId = it) }
    }


    fun insertSpeciality(speciality: Specialty) {
        uiScope.launch { insertSpecialityTask(params = speciality) }
    }

    private suspend fun insertSpecialityTask(params: Specialty?) = withContext(Dispatchers.IO){
        params?.let { database?.employeeDao()?.insertSpeciality(speciality = it) }
    }

}