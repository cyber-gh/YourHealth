package com.example.yourhealth

import androidx.lifecycle.MutableLiveData
import com.example.yourhealth.models.UserInfo
import com.example.yourhealth.ui.registration.LoginFragment
import com.example.yourhealth.ui.views.GeneralStatsFragment
import com.example.yourhealth.ui.views.MoveStatsFragment
import com.example.yourhealth.ui.views.SleepStatsFragment
import com.example.yourhealth.ui.views.UserListFragment

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

    fun showGeneralStatsFragment(userStats: UserInfo? = null) {

        val frag = GeneralStatsFragment()
        frag.stats = MutableLiveData()
        frag.stats.value = userStats?.generalStats
        activity.showFragment(frag, true)
    }
    fun showSleepStatsFragment() {
        activity.showFragment(SleepStatsFragment(), false)
    }

    fun showMoveStatsFragment() {
        activity.showFragment(MoveStatsFragment(), false)
    }

    fun showUserListFragment() {
        activity.showFragment(UserListFragment.newInstance() , false)
    }
}