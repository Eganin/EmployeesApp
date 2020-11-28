package com.example.employees.fragments

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.employees.R
import com.example.employees.adapters.EmployeeAdapter
import com.example.employees.api.ApiServiceEmployees
import com.example.employees.application.EmployeeApp
import com.example.employees.pojo.Employee
import com.example.employees.screens.employee.EmployeeViewModel
import kotlinx.android.synthetic.main.fragment_list_employees.*

class ListEmployeesFragment : Fragment() {

    private lateinit var viewModel: EmployeeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_list_employees, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this@ListEmployeesFragment)[EmployeeViewModel::class.java]
        loadData()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val adapter = EmployeeAdapter()
        adapter.employees = mutableListOf(Employee(id=1,firstName = "Egor",lastName = "Zakharin" , avatarUrl = "https://2.cdn.echo.msk.ru/files/avatar2/2561900.jpg"))
        main_recycler_view_employees.adapter = adapter
        main_recycler_view_employees.layoutManager = LinearLayoutManager(requireContext())
        viewModel.employees?.observe(this@ListEmployeesFragment, {
            adapter.employees = it!!
        })
        viewModel.errors.observe(this@ListEmployeesFragment, {
            Toast.makeText(requireContext(), "error", Toast.LENGTH_LONG).show()
        })


    }

    private fun loadData() {
        viewModel.loadData(api = (activity?.application as? EmployeeApp)?.apiServiceEmployees)
    }
}