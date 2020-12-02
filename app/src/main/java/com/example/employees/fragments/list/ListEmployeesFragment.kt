package com.example.employees.fragments.list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.employees.R
import com.example.employees.adapters.EmployeeAdapter
import com.example.employees.application.EmployeeApp
import com.example.employees.pojo.Employee
import com.example.employees.pojo.Specialty
import kotlinx.android.synthetic.main.fragment_list_employees.*

class ListEmployeesFragment : Fragment() {

    private lateinit var viewModel: ListEmployeeViewModel
    private val adapter = EmployeeAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_list_employees, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =
            ViewModelProviders.of(this@ListEmployeesFragment)[ListEmployeeViewModel::class.java]
        loadData()
        setupRecyclerView()
        if (savedInstanceState?.getString(SAVE_SPECIALITY) == null) {
            observeData()
        } else {
            observeDataSpeciality(value = savedInstanceState.getString(SAVE_SPECIALITY)!!)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is EmployeeAdapter.OnEmployeeClick) {
            adapter.onEmployeeClick = context

        }
    }

    override fun onDetach() {
        super.onDetach()
        adapter.onEmployeeClick = null
    }

    fun setClickListener(listener: EmployeeAdapter.OnEmployeeClick) {
        adapter.onEmployeeClick = listener
    }

    private fun setupRecyclerView() {
        main_recycler_view_employees.adapter = adapter
        main_recycler_view_employees.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun observeData() {
        viewModel.employees?.observe(this@ListEmployeesFragment, {
            adapter.bindEmployees(it!!)
        })
        viewModel.errors.observe(this@ListEmployeesFragment, {
            Toast.makeText(requireContext(), "error", Toast.LENGTH_LONG).show()
        })
    }

    private fun observeDataSpeciality(value: String) {
        viewModel.employees?.observe(this@ListEmployeesFragment, {
            adapter.bindEmployees(it.filter {
                it.specialty?.get(0)?.name == value
            })
        })
        viewModel.errors.observe(this@ListEmployeesFragment, {
            Toast.makeText(requireContext(), "error", Toast.LENGTH_LONG).show()
        })
    }


    private fun loadData() {
        viewModel.loadData(api = (activity?.application as? EmployeeApp)?.apiServiceEmployees)
    }

    companion object {

        fun newInstance(specialty: String?): ListEmployeesFragment {
            val args = Bundle()
            val fragment = ListEmployeesFragment()
            specialty?.let { args.putString(SAVE_SPECIALITY, it) }
            fragment.arguments = args
            return fragment
        }

        const val SAVE_SPECIALITY = "SAVE_SPECIALITY"
    }
}