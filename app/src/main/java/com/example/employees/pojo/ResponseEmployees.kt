package com.example.employees.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class ResponseEmployees {
    @SerializedName("response")
    @Expose
    var response: List<Response>? = null
}