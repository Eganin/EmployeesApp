package com.example.employees.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.employees.pojo.Employee
import com.example.employees.pojo.Specialty

@Dao
interface EmployeesDao {

    @Query("SELECT * FROM employees")
    fun getAllEmployees(): LiveData<List<Employee>>

    @Query("SELECT * FROM employees WHERE id == :employeeId")
    fun getEmployeeById(employeeId: Int): Employee

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEmployees(employees: List<Employee>)

    @Delete
    fun deleteEmployee(employee: Employee)

    @Query("DELETE FROM employees")
    fun deleteAllEmployees()

    @Query("SELECT * FROM speciality")
    fun getAllSpecialities(): LiveData<List<Specialty>>

    @Query("SELECT * FROM speciality WHERE specialty_id == :specialityId")
    fun getSpecialityById(specialityId: Int): Specialty

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSpeciality(speciality: Specialty)

    @Delete
    fun deleteSpeciality(speciality: Specialty)

    @Query("DELETE FROM speciality")
    fun deleteAllSpecialities()
}