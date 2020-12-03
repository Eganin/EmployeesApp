package com.example.employees.fragments.addemployee

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.employees.R
import com.example.employees.exceptions.ExceptionFromSendData
import com.example.employees.fragments.list.ListEmployeeViewModel
import com.example.employees.fragments.speciality.SpecialityViewModel
import com.example.employees.pojo.Employee
import com.example.employees.pojo.Specialty
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.add_employee_fragment.*

class AddEmployeeFragment : Fragment() {

    interface CreateEmployee {
        fun getImageFromIntent(data: Intent?): Bitmap
        fun afterCreateEmployee()
    }

    private var createEmployee : CreateEmployee? = null

    private lateinit var viewModel: SpecialityViewModel
    private lateinit var viewModelListEmployee: ListEmployeeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.add_employee_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()

        add_image_employee.setOnClickListener {
            val pickerIntent = Intent(Intent.ACTION_PICK)
            pickerIntent.type = "image/*"
            startActivityForResult(pickerIntent, REQUEST_CODE_IMAGE)
        }

        birthday_edit.setOnClickListener{
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { p0, p1, p2, p3 ->
                    birthday_edit.text = "$p3-$p2-$p1"
                },
                0,
                0,
                0
            )

            datePickerDialog.show()
        }

        send_data.setOnClickListener {
            try {
                viewModelListEmployee.insertEmployees(
                    listOf(
                        Employee(
                            firstName = name_label_edit.editText?.text.toString(),
                            lastName = last_name_edit.editText?.text.toString(),
                            birthday = birthday_edit.text.toString(),
                            specialty = listOf(Specialty(name = speciality_edit.selectedItem.toString()))
                        )
                    )
                )

                Toast.makeText(requireContext(), getString(R.string.add_emplouee_toast), Toast.LENGTH_LONG).show()
                createEmployee?.afterCreateEmployee()
            } catch (e: Exception) {
                Toast.makeText(requireContext(), getString(R.string.error_toast), Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CODE_IMAGE -> {
                add_image_employee.setImageBitmap(createEmployee?.getImageFromIntent(data = data))
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        createEmployee = context as? CreateEmployee
    }

    override fun onDetach() {
        super.onDetach()
        createEmployee = null
    }


    private fun getData() {
        viewModel = ViewModelProviders.of(this)[SpecialityViewModel::class.java]
        viewModelListEmployee = ViewModelProviders.of(this)[ListEmployeeViewModel::class.java]

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