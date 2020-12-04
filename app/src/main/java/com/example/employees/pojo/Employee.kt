package com.example.employees.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.employees.converters.EmployeeConverter
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "employees")
@TypeConverters(value = [EmployeeConverter::class])
data class Employee(

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,

    @SerializedName("f_name")
    @Expose
    var firstName: String? = null,

    @SerializedName("l_name")
    @Expose
    var lastName: String? = null,

    @SerializedName("birthday")
    @Expose
    var birthday: String? = null,

    @SerializedName("avatr_url")
    @Expose
    var avatarUrl: String? = null,

    @SerializedName("specialty")
    @Expose
    var specialty: List<Specialty>? = null,

    var avatarNew : String? = null
)