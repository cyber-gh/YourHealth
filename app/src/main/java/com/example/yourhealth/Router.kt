package com.example.yourhealth

import com.example.yourhealth.ui.registration.LoginFragment

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
}