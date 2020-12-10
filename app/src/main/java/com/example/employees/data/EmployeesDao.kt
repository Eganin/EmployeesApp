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
    suspend fun getEmployeeById(employeeId: Int): Employee

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEmployees(employees: List<Employee>)

    @Delete
    suspend fun deleteEmployee(employee: Employee)

    @Query("DELETE FROM employees")
    suspend fun deleteAllEmployees()

    @Query("SELECT * FROM speciality")
    fun getAllSpecialities(): LiveData<List<Specialty>>

    @Query("SELECT * FROM speciality WHERE specialty_id == :specialityId")
    suspend fun getSpecialityById(specialityId: Int): Specialty

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSpeciality(speciality: Specialty)

    @Delete
    suspend fun deleteSpeciality(speciality: Specialty)

    @Query("DELETE FROM speciality")
    suspend fun deleteAllSpecialities()
}