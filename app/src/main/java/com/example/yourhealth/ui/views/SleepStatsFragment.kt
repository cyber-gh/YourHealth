package com.example.yourhealth.ui.views

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import com.example.yourhealth.R
import com.example.yourhealth.Router
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.qlibrary.library.QFragment
import kotlinx.android.synthetic.main.sleep_stats.*
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.qlibrary.library.errAlert
import com.qlibrary.library.infoAlert
import com.qlibrary.utils.extensions.onClick
import kotlinx.android.synthetic.main.nav_header_main.*
import java.text.SimpleDateFormat
import java.util.*


class SleepStatsFragment : QFragment(R.layout.sleep_stats) {



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pieChart?.apply {
            data = PieData(PieDataSet(
                arrayListOf(PieEntry(7f,"none"), PieEntry(1f,"none")).apply {
                }, "none 2"
            ).apply {
                setColors(*ColorTemplate.VORDIPLOM_COLORS)
            })



            animateXY(2000, 2000)
            invalidate()
            
        }

        button.onClick {
            infoAlert("Reminder set")
        }

        startTime.onClick {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                startTime.setText( SimpleDateFormat("HH:mm").format(cal.time))
            }
            TimePickerDialog(Router.activity, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        endTIme.onClick {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                endTIme.setText( SimpleDateFormat("HH:mm").format(cal.time))
            }
            TimePickerDialog(Router.activity, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        }



    }


