package com.example.employees.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "speciality")
data class Specialty(


    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,

    @SerializedName("specialty_id")
    @Expose
    var specialtyId: Int? = null,

    @SerializedName("name")
    @Expose
    var name: String? = null
)