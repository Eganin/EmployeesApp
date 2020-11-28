package com.example.employees.converters

import androidx.room.TypeConverter
import com.example.employees.pojo.Specialty
import com.google.gson.Gson

@Suppress("UNCHECKED_CAST")
class EmployeeConverter {
    @TypeConverter
    fun listSpecialityToString(specialties: List<Specialty>): String = Gson().toJson(specialties)

    @TypeConverter
    fun stringToListSpeciality(specialitiesAsString: String): List<Specialty> {
        val gson = Gson()
        val objects = gson.fromJson(specialitiesAsString, ArrayList::class.java)
        val specialties = mutableListOf<Specialty>()
        objects.forEach { specialties.add(gson.fromJson(it.toString(), Specialty::class.java)) }

        return objects as List<Specialty>
    }
}