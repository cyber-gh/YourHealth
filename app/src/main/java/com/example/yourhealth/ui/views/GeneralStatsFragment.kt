package com.example.yourhealth.ui.views

import android.os.Bundle
import android.view.View
import com.example.yourhealth.Data.Rep
import com.example.yourhealth.R
import com.example.yourhealth.models.GeneralInfo
import com.qlibrary.library.QFragment

class GeneralStatsFragment: QFragment(R.layout.user_stats) {

    private var stats: GeneralInfo? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        stats = Rep.userGeneralStats.value
    }
}