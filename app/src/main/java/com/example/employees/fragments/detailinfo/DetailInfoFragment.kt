package com.example.employees.fragments.detailinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.employees.R
import com.example.employees.comon.Utils
import com.example.employees.viewholders.EmployeeViewHolder
import com.google.gson.Gson
import kotlinx.android.synthetic.main.detail_info_fragment.*
import kotlinx.coroutines.*
import java.util.*

class DetailInfoFragment : Fragment() {


    private lateinit var viewModel: DetailInfoViewModel
    private var uiScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.detail_info_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        uiScope.launch { getInfoFromEmployee() }
    }


    private suspend fun getInfoFromEmployee() {
        viewModel = ViewModelProviders.of(this@DetailInfoFragment)[DetailInfoViewModel::class.java]
        val employee =
            uiScope.async { viewModel.getEmployeeById(id = arguments!!.getInt(SAVE_ID_EMPLOYEE)) }
                .await()
        name_label_value.text =
            employee.firstName?.toLowerCase(Locale.ROOT)?.capitalize(Locale.ROOT)
        second_name_value.text =
            employee.lastName?.toLowerCase(Locale.ROOT)?.capitalize(Locale.ROOT)
        birthday_value.text = employee.birthday
        speciality_value.text = Utils.getSpeciality(employee = employee).name
        viewModel.insertSpeciality(speciality = Utils.getSpeciality(employee = employee))

        Utils.downloadImage(
            context = requireContext(),
            imageAvatar = detail_poster,
            avatarUrl = employee.avatarUrl,
            avatarNew = employee.avatarNew
        )

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