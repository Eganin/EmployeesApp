package com.example.employees.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.employees.R
import com.example.employees.pojo.Specialty
import com.example.employees.viewholders.InfoViewHolder
import com.example.employees.viewholders.MainViewHolder
import com.example.employees.viewholders.SpecialityViewHolder

class SpecialityAdapter :
    RecyclerView.Adapter<MainViewHolder>() {

    private var specialties: List<Specialty> = listOf()


    override fun getItemViewType(position: Int) =
        when (position) {
            specialties.size -> ViewTypes.VIEW_INFO.value
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
                SpecialityViewHolder(
                    itemView = LayoutInflater.from(parent.context)
                        .inflate(R.layout.speciality_item, parent, false)
                )
            }
        }


    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        when (holder) {
            is SpecialityViewHolder -> {
                holder.bind(specialty = specialties[position])
            }

            is InfoViewHolder -> {
                holder.bind(text = (specialties.size).toString())
            }
        }
    }


    override fun getItemCount() = specialties.size + 1

    fun bindSpecialties(specialties: List<Specialty>) {
        this.specialties = specialties
    }

}