package com.example.employees.fragments.list

import android.annotation.SuppressLint
import android.app.Application
import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.employees.api.ApiServiceEmployees
import com.example.employees.data.DatabaseEmployee
import com.example.employees.pojo.Employee
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*
import java.lang.Exception

class ListEmployeeViewModel(application: Application) : AndroidViewModel(application) {
    private val database = DatabaseEmployee.getInstance(context = application.applicationContext)

    private val compositeDisposable = CompositeDisposable()

    val errors: MutableLiveData<Throwable> = MutableLiveData()

    var employees = database?.employeeDao()?.getAllEmployees()

    private val TAG = ListEmployeeViewModel::class.java.simpleName

    private var uiScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, exception ->
        Log.d(TAG, "CoroutineExceptionHandler got $exception in $coroutineContext")
    }

    fun insertEmployees(list: List<Employee>) {
        uiScope.launch { insertEmployeesTask(params = list) }
    }

    fun deleteEmployee(employee: Employee) {
        uiScope.launch { deleteEmployeeTask(params = employee) }
    }

    private fun deleteAllEmployees() {
        uiScope.launch { deleteAllEmployeeTask() }
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


    private suspend fun insertEmployeesTask(params: List<Employee>?) = withContext(Dispatchers.IO) {
        params?.let { database?.employeeDao()?.insertEmployees(employees = it) }
    }

    private suspend fun deleteEmployeeTask(params: Employee?) = withContext(Dispatchers.IO) {
        params?.let { database?.employeeDao()?.deleteEmployee(employee = it) }
    }

    private suspend fun deleteAllEmployeeTask() = withContext(Dispatchers.IO) {
        database?.employeeDao()?.deleteAllEmployees()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}