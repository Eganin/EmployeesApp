package com.example.employees.viewholders

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.employees.R
import com.example.employees.comon.Utils.Companion.downloadImage
import com.example.employees.pojo.Employee
import java.util.*

class EmployeeViewHolder(itemView: View) : MainViewHolder(itemView = itemView) {

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

        downloadImage(
            context = context,
            imageAvatar = imageAvatar,
            avatarUrl = employee.avatarUrl,
            avatarNew = employee.avatarNew
        )

    }


}

private val RecyclerView.ViewHolder.context
    get() = this.itemView.context
