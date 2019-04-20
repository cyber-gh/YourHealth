package com.example.yourhealth.ui.views

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.example.yourhealth.Data.Rep
import com.example.yourhealth.R
import com.qlibrary.library.QFragment
import kotlinx.android.synthetic.main.user_stats.*

class GeneralStatsFragment: QFragment(R.layout.user_stats) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Rep.getGeneralStats()
        Rep.userGeneralStats.observe(this, Observer {
            heartRateLabel.text = it.bloodPressure.toString()
            bloodSugarLabel.text = it.sugarLevel.toString()
            sleepHoursLabel.text = it.sleepHours.toString()
        })
    }
}