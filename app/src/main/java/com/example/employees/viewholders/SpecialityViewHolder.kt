package com.example.employees.viewholders

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import com.example.employees.R
import com.example.employees.pojo.Specialty

class SpecialityViewHolder(itemView: View) : MainViewHolder(itemView = itemView) {

    interface OnClickSpeciality {
        fun clickSpeciality(specialityText: String)
    }

    var onClickSpeciality: OnClickSpeciality? = null

    private val specialtyText = itemView.findViewById<AppCompatTextView>(R.id.speciality)

    fun bind(specialty: Specialty) {
        specialtyText.text = specialty.name
        itemView.apply {
            setOnClickListener { onClickSpeciality?.clickSpeciality(specialityText = specialtyText.text.toString()) }
        }
    }
}