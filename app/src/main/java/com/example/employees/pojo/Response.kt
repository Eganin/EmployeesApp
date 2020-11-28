package com.example.employees.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Response {
    @SerializedName("f_name")
    @Expose
    var firstName: String? = null

    @SerializedName("l_name")
    @Expose
    var lastName: String? = null

    @SerializedName("birthday")
    @Expose
    var birthday: String? = null

    @SerializedName("avatr_url")
    @Expose
    var avatarUrl: String? = null

    @SerializedName("specialty")
    @Expose
    var specialty: List<Specialty>? = null
}