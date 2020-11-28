package com.example.employees.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class ResponseEmployees (
    @SerializedName("response")
    @Expose
    var response: List<Employee>? = null
)