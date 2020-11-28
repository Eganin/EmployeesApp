package com.example.employees.fragments.list

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
        viewModel = ViewModelProviders.of(this@ListEmployeesFragment)[ListEmployeeViewModel::class.java]
        loadData()
        setupRecyclerView()
    }

    fun setClickListener(listener : EmployeeAdapter.OnEmployeeClick){
        adapter.onEmployeeClick = listener
    }

    private fun setupRecyclerView() {
        main_recycler_view_employees.adapter = adapter
        main_recycler_view_employees.layoutManager = LinearLayoutManager(requireContext())
        viewModel.employees?.observe(this@ListEmployeesFragment, {
            adapter.employees = it!!
            adapter.notifyDataSetChanged()
        })
        viewModel.errors.observe(this@ListEmployeesFragment, {
            Toast.makeText(requireContext(), "error", Toast.LENGTH_LONG).show()
        })


    }

    private fun loadData() {
        viewModel.loadData(api = (activity?.application as? EmployeeApp)?.apiServiceEmployees)
    }
}