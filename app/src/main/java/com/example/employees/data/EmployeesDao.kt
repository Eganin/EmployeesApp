package com.example.employees.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.employees.pojo.Employee

@Dao
interface EmployeesDao {

    @Query("SELECT * FROM employees")
    fun getAllEmployees(): LiveData<List<Employee>>

    @Query("SELECT * FROM employees WHERE id == :employeeId")
    fun getEmployeeById(employeeId: Int): Employee

    @Insert
    fun insertEmployees(employees: List<Employee>)

    @Delete
    fun deleteEmployee(employee: Employee)

    @Query("DELETE FROM employees")
    fun deleteAllEmployees()
}