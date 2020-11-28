@file:Suppress("DEPRECATION")

package com.example.employees.screens.employee

import android.annotation.SuppressLint
import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.employees.api.ApiServiceEmployees
import com.example.employees.data.DatabaseEmployee
import com.example.employees.pojo.Employee
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class EmployeeViewModel(application: Application) : AndroidViewModel(application) {
    private val database = DatabaseEmployee.getInstance(context = application.applicationContext)

    private val compositeDisposable = CompositeDisposable()

    val errors: MutableLiveData<Throwable> = MutableLiveData()

    var employees = database?.employeeDao()?.getAllEmployees()

    fun getEmployeeById(id: Int): Employee {
        return GetEmployeeByIdTask().execute(id).get()
    }

    fun insertEmployees(list: List<Employee>) {
        InsertEmployeesTask().execute(list)
    }

    fun deleteEmployee(employee: Employee) {
        DeleteEmployeeTask().execute(employee)
    }

    fun deleteAllEmployees() {
        DeleteAllEmployeesTask().execute()
    }

    fun loadData(api: ApiServiceEmployees?) {
        api?.let {
            compositeDisposable.add(
                it.getEmployees()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        deleteAllEmployees()
                        insertEmployees(list = it.response!!)
                    }, {
                        errors.value = it
                    })
            )
        }
    }

    @SuppressLint("StaticFieldLeak")
    inner class GetEmployeeByIdTask : AsyncTask<Int, Void, Employee>() {
        override fun doInBackground(vararg params: Int?): Employee? =
            params[0]?.let { database?.employeeDao()?.getEmployeeById(employeeId = it) }

    }

    @SuppressLint("StaticFieldLeak")
    inner class InsertEmployeesTask : AsyncTask<List<Employee>, Void, Void>() {
        override fun doInBackground(vararg params: List<Employee>?): Void? {
            params[0]?.let { database?.employeeDao()?.insertEmployees(employees = it) }
            return null
        }

    }

    @SuppressLint("StaticFieldLeak")
    inner class DeleteEmployeeTask : AsyncTask<Employee, Void, Void>() {
        override fun doInBackground(vararg params: Employee?): Void? {
            params[0]?.let { database?.employeeDao()?.deleteEmployee(employee = it) }
            return null
        }
    }

    @SuppressLint("StaticFieldLeak")
    private inner class DeleteAllEmployeesTask : AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg params: Void?): Void? {
            database?.employeeDao()?.deleteAllEmployees()
            return null
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}