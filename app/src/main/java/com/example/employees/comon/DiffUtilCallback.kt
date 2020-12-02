package com.example.employees.comon

import androidx.recyclerview.widget.DiffUtil
import com.example.employees.pojo.Employee

class DiffUtilCallback(
    private val oldData: List<Employee>,
    private val newData: List<Employee>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldData.size

    override fun getNewListSize(): Int = newData.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val newItem = newData[newItemPosition]
        val oldItem = oldData[oldItemPosition]
        return newItem.firstName == oldItem.firstName
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val newItem = newData[newItemPosition]
        val oldItem = oldData[oldItemPosition]
        return newItem == oldItem
    }
}