package com.example.employees.fragments.speciality

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

    private fun setupRecyclerView() {
        val adapter = SpecialityAdapter()

        speciality_recycler_view.layoutManager = LinearLayoutManager(requireContext())
        speciality_recycler_view.adapter = adapter

        viewModel.specialities?.observe(this@SpecialityFragment, {
            adapter.specialties = it
            adapter.notifyDataSetChanged()
        })
    }
}