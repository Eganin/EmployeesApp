package com.example.employees.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.employees.R
import com.example.employees.pojo.Specialty

class SpecialityAdapter(var specialties: List<Specialty> = mutableListOf()) :
    RecyclerView.Adapter<SpecialityAdapter.SpecialityViewHolder>() {

    interface OnClickSpeciality {
        fun clickSpeciality(specialityText: String)
    }

    var onClickSpeciality: OnClickSpeciality? = null

    inner class SpecialityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val specialtyText = itemView.findViewById<AppCompatTextView>(R.id.speciality)

        init {
            itemView.apply {
                setOnClickListener { onClickSpeciality?.clickSpeciality(specialityText = specialtyText.text.toString()) }
            }
        }

        fun bind(position: Int) {
            specialtyText.text = specialties[position].name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecialityViewHolder =
        SpecialityViewHolder(
            itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.speciality_item, parent, false)
        )

    override fun onBindViewHolder(holder: SpecialityViewHolder, position: Int) =
        holder.bind(position = position)

    override fun getItemCount() = specialties.size
}