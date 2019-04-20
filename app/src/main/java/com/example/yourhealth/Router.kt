package com.example.yourhealth

import com.example.yourhealth.ui.registration.LoginFragment
import com.example.yourhealth.ui.views.GeneralStatsFragment

object Router {
    lateinit var activity: MainActivity

    fun hideBack(){
        activity.supportActionBar!!.setDisplayHomeAsUpEnabled(false)
    }

    fun hideAppBar(){
        activity.supportActionBar!!.hide()
    }

    fun showAppBar(){
        activity.supportActionBar!!.show()
    }

    fun showLoginFragment() {
        activity.showFragment(LoginFragment(), false)
    }

    fun showGeneralStatsFragment() {
        activity.showFragment(GeneralStatsFragment(), false)
    }
}