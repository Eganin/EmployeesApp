package com.example.employees.comon

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.employees.R
import com.example.employees.pojo.Employee
import com.example.employees.pojo.Specialty
import com.google.gson.Gson
import java.util.ArrayList

abstract class Utils {
    companion object {
        fun getSpeciality(employee: Employee): Specialty {
            val gson = Gson()
            val objects = gson.fromJson(employee.specialty.toString(), ArrayList::class.java)
            val specialties = mutableListOf<Specialty>()
            objects.forEach { specialties.add(gson.fromJson(it.toString(), Specialty::class.java)) }

            return specialties[0]
        }

        private val imageOptions = RequestOptions()
            .placeholder(R.drawable.ic_baseline_person_24)
            .fallback(R.drawable.ic_baseline_person_24)
            .circleCrop()

        fun downloadImage(
            context: Context,
            imageAvatar: ImageView,
            avatarUrl: String? = null,
            avatarNew: String? = null
        ) {
            if (avatarNew == null) {
                Glide.with(context)
                    .load(avatarUrl)
                    .apply(imageOptions)
                    .into(imageAvatar)
            } else {
                Glide.with(context)
                    .load(Uri.parse(avatarNew))
                    .apply(imageOptions)
                    .into(imageAvatar)
            }
        }
    }
}