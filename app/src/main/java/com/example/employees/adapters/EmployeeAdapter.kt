package com.example.employees.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.employees.R
import com.example.employees.pojo.Employee
import com.squareup.picasso.Picasso
import java.util.*

class EmployeeAdapter(var employees: MutableList<Employee> = mutableListOf()) :
    RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder>() {

    interface OnEmployeeClick {
        fun click(position: Int)
    }

    var onEmployeeClick: OnEmployeeClick? = null

    inner class EmployeeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val firstNameTextView =
            itemView.findViewById<AppCompatTextView>(R.id.first_name_value)
        private val lastNameTextView =
            itemView.findViewById<AppCompatTextView>(R.id.last_name_value)

        private val imageAvatar = itemView.findViewById<AppCompatImageView>(R.id.image_avatar)

        init {
            itemView.apply {
                setOnClickListener { onEmployeeClick?.click(position = adapterPosition) }
            }
        }

        fun bind(position: Int) {
            val employee = employees[position]
            println(employee)
            firstNameTextView.text =
                employee.firstName?.toLowerCase(Locale.ROOT)?.capitalize(Locale.ROOT)
            lastNameTextView.text =
                employee.lastName?.toLowerCase(Locale.ROOT)?.capitalize(Locale.ROOT)
            Picasso.get().load(employee.avatarUrl).into(imageAvatar)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder =
        EmployeeViewHolder(
            itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.employee_item, parent, false)
        )

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) =
        holder.bind(position = position)

    override fun getItemCount(): Int = employees.size
}