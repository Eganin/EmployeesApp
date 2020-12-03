package com.example.employees.fragments.addemployee

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.employees.R
import com.example.employees.fragments.speciality.SpecialityViewModel
import kotlinx.android.synthetic.main.add_employee_fragment.*

class AddEmployeeFragment : Fragment() {

    interface SelectedImage{
        fun getImageFromIntent(data : Intent?) : Bitmap
    }

    private var selectedImage : SelectedImage? = null

    private lateinit var viewModel: SpecialityViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.add_employee_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDataFromSpinner()

        add_image_employee.setOnClickListener {
            val pickerIntent = Intent(Intent.ACTION_PICK)
            pickerIntent.type = "image/*"
            startActivityForResult(pickerIntent, REQUEST_CODE_IMAGE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            REQUEST_CODE_IMAGE ->{
                add_image_employee.setImageBitmap(selectedImage?.getImageFromIntent(data=data))
            }
        }
    }


    private fun getDataFromSpinner() {
        viewModel = ViewModelProviders.of(this)[SpecialityViewModel::class.java]

        viewModel.specialities?.observe(this@AddEmployeeFragment, {
            val items = mutableListOf<String?>()
            it.forEach { items.add(it.name) }
            val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
            speciality_edit.adapter = adapter
        })
    }

    companion object {
        const val REQUEST_CODE_IMAGE = 1231
    }
}