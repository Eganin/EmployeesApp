package com.example.employees.comon

import com.example.employees.pojo.Specialty
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class EmployeeSave(
    @SerializedName("first_name")
    @Expose
    val firstName: String,
    @SerializedName("last_name")
    @Expose
    val lastName: String,
    @SerializedName("birthday")
    @Expose
    val birthday: String,
    @SerializedName("speciality_position")
    @Expose
    val specialtyPosition:Int,
    @SerializedName("uri_image")
    @Expose
    val uriImage: String
)