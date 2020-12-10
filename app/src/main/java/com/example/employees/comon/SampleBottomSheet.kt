package com.example.employees.comon

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import com.example.employees.R
import com.example.employees.pojo.Employee
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_fragmrnt.*

class SampleBottomSheet(val employee : Employee) : BottomSheetDialogFragment(){

    private var bottomListener : BottomSheetListener? = null

    override fun setupDialog(dialog: Dialog, style: Int) {
        val contentView =
            View.inflate(context, R.layout.dialog_fragmrnt, null)
        dialog.setContentView(contentView)
        (contentView.parent as View).setBackgroundColor(
            ContextCompat.getColor(
                contentView.context,
                android.R.color.transparent
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_fragmrnt, container, false)

        view.findViewById<Button>(R.id.btn_ok).setOnClickListener {
            dialog?.cancel()
            bottomListener?.positiveClick(id=employee.id!!)
        }

        view.findViewById<Button>(R.id.btn_cancel).setOnClickListener {
            bottomListener?.negativeClick(id=employee.id!!)
            dialog?.cancel()
        }

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is BottomSheetListener)
            bottomListener = context
    }

    override fun onDetach() {
        super.onDetach()
        bottomListener = null
    }

    interface BottomSheetListener{
        fun positiveClick(id : Int)
        fun negativeClick(id : Int)
    }
}