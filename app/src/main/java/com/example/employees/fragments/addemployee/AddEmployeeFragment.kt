package com.example.employees.fragments.addemployee

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.employees.R
import com.example.employees.comon.EmployeeSave
import com.example.employees.comon.Utils
import com.example.employees.fragments.list.ListEmployeeViewModel
import com.example.employees.fragments.speciality.SpecialityViewModel
import com.example.employees.pojo.Employee
import com.example.employees.pojo.Specialty
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.add_employee_fragment.*
import java.util.*

class AddEmployeeFragment : Fragment() {

    private lateinit var currentAvatar: Uri

    interface CreateEmployee {
        fun afterCreateEmployee()
    }

    private var createEmployee: CreateEmployee? = null

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

        birthday_edit.setOnClickListener {
            val currentCalendar = Calendar.getInstance()
            val year = currentCalendar[Calendar.YEAR]
            val month = currentCalendar[Calendar.MONTH]
            val day = currentCalendar[Calendar.DAY_OF_MONTH]
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { p0, p1, p2, p3 ->
                    birthday_edit.text = "$p3-$p2-$p1"
                },
                year,
                month,
                day
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
                            specialty = listOf(Specialty(name = speciality_edit.selectedItem.toString())),
                            avatarNew = currentAvatar.toString()
                        )
                    )
                )

                Toast.makeText(
                    requireContext(),
                    getString(R.string.add_emplouee_toast),
                    Toast.LENGTH_LONG
                ).show()
                createEmployee?.afterCreateEmployee()
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(requireContext(), getString(R.string.error_toast), Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {

            REQUEST_CODE_IMAGE -> {
                try{
                    currentAvatar = data?.data!!
                    Utils.downloadImage(
                        context = requireContext(),
                        imageAvatar = add_image_employee,
                        avatarNew = currentAvatar.toString()
                    )
                }catch (e : Exception){

                }
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val json = JsonObject()
        try {
            json.addProperty("first_name", name_label_edit.editText?.text.toString())
            json.addProperty("last_name", last_name_edit.editText?.text.toString())
            json.addProperty("birthday", birthday_edit.text.toString())
            json.addProperty("speciality_position", speciality_edit.selectedItemPosition)
            json.addProperty("uri_image", currentAvatar.toString())
        } catch (e: Exception) {
            e.printStackTrace()

        }

        outState.putString(SAVE_JSON_DATA, Gson().toJson(json))
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        try {
            val result =
                Gson().fromJson(
                    savedInstanceState?.getString(SAVE_JSON_DATA),
                    EmployeeSave::class.java
                )
            name_label_edit.editText?.setText(result.firstName, TextView.BufferType.EDITABLE)
            last_name_edit.editText?.setText(result.lastName, TextView.BufferType.EDITABLE)
            birthday_edit.text = result.birthday
            speciality_edit.setSelection(result.specialtyPosition)
            Log.d("AAA", result.specialtyPosition.toString())
            Log.d("AAA", result.uriImage)
            add_image_employee.setImageURI(Uri.parse(result.uriImage))
        } catch (e: Exception) {
            e.printStackTrace()
        }
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
        private const val REQUEST_CODE_IMAGE = 1231
        private const val SAVE_JSON_DATA = "SAVE_JSON_DATA"
    }
}