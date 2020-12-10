package com.example.employees.fragments.list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.employees.R
import com.example.employees.adapters.EmployeeAdapter
import com.example.employees.application.EmployeeApp
import com.example.employees.comon.DiffUtilCallback
import com.example.employees.comon.Utils
import com.example.employees.comon.WrapContentLinearLayoutManager
import com.example.employees.fragments.detailinfo.DetailInfoFragment
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
        setupRecyclerView()
        setupTouchListener()
        setupFloatButton()

        if (arguments?.getString(SAVE_SPECIALITY) == null) {
            observeData()
        } else {
            observeDataSpeciality(value = arguments?.getString(SAVE_SPECIALITY)!!)
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
        main_recycler_view_employees.layoutManager =
            WrapContentLinearLayoutManager(context = requireContext())
    }

    private fun observeData() {
        viewModel.employees?.observe(this@ListEmployeesFragment, {
            adapter.bindEmployees(it!!)
            adapter.notifyDataSetChanged()
        })
        viewModel.errors.observe(this@ListEmployeesFragment, {
            Toast.makeText(requireContext(), getString(R.string.error_message), Toast.LENGTH_LONG)
                .show()
        })
    }

    private fun observeDataSpeciality(value: String) {
        viewModel.employees?.observe(this@ListEmployeesFragment, {
            adapter.bindEmployees(it.filter {
                Utils.getSpeciality(it).name == value
            })

            adapter.notifyDataSetChanged()
        })
        viewModel.errors.observe(this@ListEmployeesFragment, {
            Toast.makeText(requireContext(), "error", Toast.LENGTH_LONG).show()
        })
    }


    private fun loadData() {
        viewModel.loadData(api = (activity?.application as? EmployeeApp)?.apiServiceEmployees)
    }

    private fun setupTouchListener() {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // реагирует на свайп
                viewModel.deleteEmployee(employee = adapter.getData()[viewHolder.adapterPosition - 1])
            }
        }).attachToRecyclerView(main_recycler_view_employees)
    }

    private fun setupFloatButton() {
        shuffle_float_button.apply {
            setOnClickListener {
                val shuffledList = adapter.getData().shuffled()
                val originalList = adapter.getData()
                adapter.bindEmployees(shuffledList)

                val diffUtilCallback =
                    DiffUtilCallback(oldData = originalList, newData = shuffledList)
                val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(diffUtilCallback)
                diffResult.dispatchUpdatesTo(adapter)

            }
        }

        load_data_float_button.apply {
            setOnClickListener {
                loadData()
            }
        }
    }

    companion object {

        fun newInstance(specialty: String?): ListEmployeesFragment {
            val args = Bundle()
            val fragment = ListEmployeesFragment()
            specialty?.let { args.putString(SAVE_SPECIALITY, it) }
            fragment.arguments = args
            return fragment
        }

        fun removeEmployee(){
        }

        const val SAVE_SPECIALITY = "SAVE_SPECIALITY"
    }
}