package com.example.employees.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Specialty (
    @SerializedName("specialty_id")
    @Expose
    var specialtyId: Int? = null,

    @SerializedName("name")
    @Expose
    var name: String? = null
)