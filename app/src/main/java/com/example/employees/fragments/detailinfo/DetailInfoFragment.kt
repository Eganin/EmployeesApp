package com.example.employees.fragments.detailinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.employees.R
import com.example.employees.pojo.Employee
import com.example.employees.pojo.Specialty
import com.example.employees.viewholders.EmployeeViewHolder
import com.google.gson.Gson
import kotlinx.android.synthetic.main.detail_info_fragment.*
import java.util.*

class DetailInfoFragment : Fragment() {


    private lateinit var viewModel: DetailInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.detail_info_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getInfoFromEmployee()
    }


    private fun getInfoFromEmployee() {
        viewModel = ViewModelProviders.of(this@DetailInfoFragment)[DetailInfoViewModel::class.java]
        val employee = viewModel.getEmployeeById(id = arguments!!.getInt(SAVE_ID_EMPLOYEE))
        name_label_value.text =
            employee.firstName?.toLowerCase(Locale.ROOT)?.capitalize(Locale.ROOT)
        second_name_value.text =
            employee.lastName?.toLowerCase(Locale.ROOT)?.capitalize(Locale.ROOT)
        birthday_value.text = employee.birthday
        speciality_value.text = getSpeciality(employee = employee).name
        viewModel.insertSpeciality(speciality = getSpeciality(employee = employee))

        EmployeeViewHolder.downloadImage(
            context = requireContext(),
            imageAvatar = detail_poster,
            avatarUrl = employee.avatarUrl,
            avatarNew = employee.avatarNew
        )

    }

    companion object {

        fun getSpeciality(employee: Employee): Specialty {
            val gson = Gson()
            val objects = gson.fromJson(employee.specialty.toString(), ArrayList::class.java)
            val specialties = mutableListOf<Specialty>()
            objects.forEach { specialties.add(gson.fromJson(it.toString(), Specialty::class.java)) }

            return specialties[0]
        }

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