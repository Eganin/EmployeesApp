package com.example.employees.fragments.detailinfo

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.employees.R
import com.example.employees.pojo.Employee
import com.example.employees.pojo.Specialty
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.detail_info_fragment.*
import java.util.*

class DetailInfoFragment : Fragment() {

    interface OnClickBackToList {
        fun clickToBack()
    }

    var onClickBackToList: OnClickBackToList? = null

    private lateinit var viewModel: DetailInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.detail_info_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getInfoFromEmployee()

        view.findViewById<AppCompatImageView>(R.id.detail_poster).apply {
            setOnClickListener { onClickBackToList?.clickToBack() }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnClickBackToList) {
            onClickBackToList = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        onClickBackToList = null
    }

    fun setClickListener(listener: OnClickBackToList) {
        onClickBackToList = listener
    }

    private fun getInfoFromEmployee() {
        viewModel = ViewModelProviders.of(this@DetailInfoFragment)[DetailInfoViewModel::class.java]
        val employee = viewModel.getEmployeeById(id = arguments!!.getInt(SAVE_ID_EMPLOYEE))
        name_label_value.text =
            employee.firstName?.toLowerCase(Locale.ROOT)?.capitalize(Locale.ROOT)
        second_name_value.text =
            employee.lastName?.toLowerCase(Locale.ROOT)?.capitalize(Locale.ROOT)
        birthday_value.text = employee.birthday
        speciality_value.text = getNameSpeciality(employee = employee)
        viewModel.insertSpeciality(speciality = getSpeciality(employee = employee))
        try {
            Picasso.get().load(employee.avatarUrl).into(detail_poster)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }

    }

    private fun getNameSpeciality(employee: Employee): String? {
        val gson = Gson()
        val objects = gson.fromJson(employee.specialty.toString(), ArrayList::class.java)
        val specialties = mutableListOf<Specialty>()
        objects.forEach { specialties.add(gson.fromJson(it.toString(), Specialty::class.java)) }

        return specialties[0].name
    }

    private fun getSpeciality(employee: Employee): Specialty {
        val gson = Gson()
        val objects = gson.fromJson(employee.specialty.toString(), ArrayList::class.java)
        val specialties = mutableListOf<Specialty>()
        objects.forEach { specialties.add(gson.fromJson(it.toString(), Specialty::class.java)) }

        return specialties[0]
    }


    companion object {
        fun newInstance(id: Int): DetailInfoFragment {
            val fragment = DetailInfoFragment()
            val bundle = Bundle()
            bundle.putInt(SAVE_ID_EMPLOYEE, id)
            fragment.arguments = bundle
            return fragment
        }

        private const val SAVE_ID_EMPLOYEE = "SAVE_ID_EMPLOYEE"
    }
}