package com.qlibrary.library

import android.content.Context
import android.text.method.TextKeyListener.clear
import android.R
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.*


class QStringAdapter(context: Context, resource: Int) : ArrayAdapter<String>(context, resource) {
    override fun getFilter(): Filter? {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                return FilterResults()
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                notifyDataSetChanged()
            }

        }
    }

}/*: BaseAdapter(), Filterable{

    override fun getFilter(): Filter? {
        return null
    }

    var inflater: LayoutInflater = LayoutInflater.from(mContext)

    override fun getCount(): Int {
        return arraylist.size
    }

    override fun getItem(position: Int): String{
        return arraylist[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, view: View, parent: ViewGroup): View {
        val newView = inflater.inflate(android.R.layout.simple_dropdown_item_1line, parent)
        newView.findViewById<TextView>(R.id.text1).text = arraylist[position]
        return newView
    }
} */