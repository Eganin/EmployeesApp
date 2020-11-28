package com.example.employees.application

import android.app.Application
import com.example.employees.api.ApiServiceEmployees
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class EmployeeApp : Application() {

    lateinit var apiServiceEmployees: ApiServiceEmployees

    companion object {
        private const val BASE_URL = "https://gitlab.65apps.com/65gb/static/raw/master/"
    }

    override fun onCreate() {
        super.onCreate()
        configureRetrofit()
    }

    private fun configureRetrofit() {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build()

        apiServiceEmployees = retrofit.create(ApiServiceEmployees::class.java)
    }
}