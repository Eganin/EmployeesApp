package com.example.employees.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.employees.R
import com.example.employees.pojo.Employee
import com.example.employees.viewholders.EmployeeViewHolder


class EmployeeAdapter(var employees: List<Employee> = mutableListOf()) :
    RecyclerView.Adapter<EmployeeViewHolder>() {

    interface OnEmployeeClick {
        fun click(id: Int)
    }

    var onEmployeeClick: OnEmployeeClick? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder =
        EmployeeViewHolder(
            itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.employee_item, parent, false)
        )

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        holder.itemView.apply {
            setOnClickListener {
                employees[position].id?.let {
                    onEmployeeClick?.click(
                        id = it
                    )
                }
            }
        }

        holder.bind(employee = employees[position])
    }

    override fun getItemCount(): Int = employees.size
}