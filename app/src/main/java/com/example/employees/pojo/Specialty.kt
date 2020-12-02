package com.example.employees.pojo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(
    tableName = "speciality",
    indices = [Index(value = ["specialty_id", "name"], unique = true)]
)
data class Specialty(


    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,

    @ColumnInfo(name = "specialty_id")
    @SerializedName("specialty_id")
    @Expose
    var specialtyId: Int? = null,

    @ColumnInfo(name = "name")
    @SerializedName("name")
    @Expose
    var name: String? = null
)