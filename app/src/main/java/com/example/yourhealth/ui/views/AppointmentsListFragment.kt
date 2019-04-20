package com.example.yourhealth.ui.views

import android.content.Context
import com.example.yourhealth.Data.Rep
import com.example.yourhealth.R
import com.example.yourhealth.Router
import com.example.yourhealth.models.Appointment
import com.example.yourhealth.models.UserInfo
import com.qlibrary.library.QRecycleListFragment
import com.qlibrary.library.QRecycleView
import com.qlibrary.utils.extensions.onClick
import kotlinx.android.synthetic.main.appointment_view.view.*
import kotlinx.android.synthetic.main.user_minimal_view.view.*


class AppointmentsListFragment : QRecycleListFragment<Appointment, AppointmentsListFragment.AppointmentView>() {


    companion object {
        fun newInstance(onProductItemSelected: (data: Appointment) -> Unit = {},
                        popOnSelect: Boolean = false) = AppointmentsListFragment().apply {
            onItemClickListener = { it ,_,_ ->
                if(popOnSelect) fragmentManager!!.popBackStack()
                onProductItemSelected(it)
            }

            viewClass = AppointmentView::class

            getMoreData = {
                Rep.getAppointments()
            }
        }
    }


    class AppointmentView(context: Context) : QRecycleView<Appointment>(context) {

        override fun setContent(data: Appointment) {
            dateField.text = data.date
            descriptionField.text = data.description
        }

        override fun getLayoutId() = R.layout.appointment_view
    }
}