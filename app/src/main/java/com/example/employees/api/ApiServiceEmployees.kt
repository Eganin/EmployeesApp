package com.example.employees.api

import com.example.employees.pojo.ResponseEmployees
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiServiceEmployees {
    @GET(value = "testTask.json")
    @Headers(value = ["Content-Type: application/json"])
    fun getEmployees(): Single<ResponseEmployees>
}