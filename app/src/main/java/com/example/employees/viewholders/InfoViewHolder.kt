package com.example.employees.viewholders

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import com.example.employees.R
import kotlinx.android.synthetic.main.info_item.view.*


class InfoViewHolder(itemView: View) : MainViewHolder(itemView=itemView) {

    private val titleValue = itemView.findViewById<AppCompatTextView>(R.id.title_value)
    private val title = itemView.findViewById<AppCompatTextView>(R.id.title)

    fun bind(text : String){
        if(text != ""){
            title.text = itemView.context.getString(R.string.all_title)
        }
        titleValue.text = text
    }
}