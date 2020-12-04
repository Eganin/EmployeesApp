package com.example.employees.fragments.speciality

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.employees.R
import com.example.employees.adapters.SpecialityAdapter
import kotlinx.android.synthetic.main.specialities_fragment.*

class SpecialityFragment : Fragment() {

    private lateinit var viewModel: SpecialityViewModel
    private val adapter = SpecialityAdapter()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.specialities_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this)[SpecialityViewModel::class.java]
        setupRecyclerView()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        adapter.onClickSpeciality = context as? SpecialityAdapter.OnClickSpeciality
    }

    override fun onDetach() {
        super.onDetach()
        adapter.onClickSpeciality = null
    }

    fun setListener(listener: SpecialityAdapter.OnClickSpeciality) {
        adapter.onClickSpeciality = listener
    }

    private fun setupRecyclerView() {
        speciality_recycler_view.layoutManager = LinearLayoutManager(requireContext())
        speciality_recycler_view.adapter = adapter

        viewModel.specialities?.observe(this@SpecialityFragment, {
            adapter.bindSpecialties(specialties = it)
            adapter.notifyDataSetChanged()
        })
    }
}