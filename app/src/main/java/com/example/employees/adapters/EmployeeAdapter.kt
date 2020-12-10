package com.example.employees.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.employees.R
import com.example.employees.pojo.Employee
import com.example.employees.viewholders.EmployeeViewHolder
import com.example.employees.viewholders.InfoViewHolder
import com.example.employees.viewholders.MainViewHolder


class EmployeeAdapter : RecyclerView.Adapter<MainViewHolder>() {

    private var employees: List<Employee> = listOf()

    interface OnEmployeeClick {
        fun click(id: Int)
        fun onLongClick(employee : Employee)
    }

    var onEmployeeClick: OnEmployeeClick? = null

    override fun getItemViewType(position: Int) =
        when (position) {
            0 -> ViewTypes.VIEW_INFO.value
            employees.size + 1 -> ViewTypes.VIEW_INFO.value
            else -> ViewTypes.VIEW_EMPLOYEE.value
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder =
        when (viewType) {
            ViewTypes.VIEW_INFO.value -> {
                InfoViewHolder(
                    itemView = LayoutInflater.from(parent.context)
                        .inflate(R.layout.info_item, parent, false)
                )
            }

            else -> {
                EmployeeViewHolder(
                    itemView = LayoutInflater.from(parent.context)
                        .inflate(R.layout.employee_item, parent, false)
                )
            }
        }


    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {

        when (holder) {
            is EmployeeViewHolder -> {
                val employee = employees[position - 1]
                holder.itemView.apply {
                    setOnClickListener {
                        employee.id?.let {
                            onEmployeeClick?.click(
                                id = it
                            )
                        }
                    }
                    setOnLongClickListener {
                        onEmployeeClick?.onLongClick(employee = employees[position])
                        true
                    }
                }

                holder.bind(employee = employee)
            }

            is InfoViewHolder -> {
                if (position == 0) {
                    holder.bind(text = "")
                } else {
                    holder.bind(text = (employees.size).toString())
                }
            }
        }

    }

    override fun getItemCount(): Int = employees.size + 2

    fun bindEmployees(employees: List<Employee>) {
        this.employees = employees
    }

    fun getData() = employees
}

enum class ViewTypes(val value: Int) {
    VIEW_INFO(value = 1),
    VIEW_EMPLOYEE(value = 2),
}