package com.example.employees.viewholders

import android.content.Context
import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.employees.R
import com.example.employees.pojo.Employee
import java.io.File
import java.net.URI
import java.util.*

class EmployeeViewHolder(itemView: View) : MainViewHolder(itemView = itemView) {

    companion object {
        val imageOptions = RequestOptions()
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
