package com.example.employees.viewholders

import android.graphics.drawable.Drawable
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.employees.R
import com.example.employees.pojo.Employee
import java.util.*

class EmployeeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    companion object {
        val imageOptions = RequestOptions()
            .placeholder(R.drawable.ic_baseline_person_24)
            .fallback(R.drawable.ic_baseline_person_24)
            .circleCrop()
    }

    private val firstNameTextView =
        itemView.findViewById<AppCompatTextView>(R.id.first_name_value)
    private val lastNameTextView =
        itemView.findViewById<AppCompatTextView>(R.id.last_name_value)

    private val imageAvatar = itemView.findViewById<AppCompatImageView>(R.id.image_avatar)

    fun bind(employee: Employee) {

        firstNameTextView.text =
            employee.firstName?.toLowerCase(Locale.ROOT)?.capitalize(Locale.ROOT)
        lastNameTextView.text =
            employee.lastName?.toLowerCase(Locale.ROOT)?.capitalize(Locale.ROOT)

        Glide.with(context)
            .load(employee.avatarUrl)
            .apply(imageOptions)
            .into(imageAvatar)
    }

}

private val RecyclerView.ViewHolder.context
    get() = this.itemView.context