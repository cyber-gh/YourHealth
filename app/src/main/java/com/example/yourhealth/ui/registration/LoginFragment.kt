package com.example.yourhealth.ui.registration


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import com.example.yourhealth.Data.Rep
import com.example.yourhealth.R
import com.example.yourhealth.Router
import com.qlibrary.library.QFragment
import com.qlibrary.library.infoAlert
import com.qlibrary.utils.extensions.onClick
import kotlinx.android.synthetic.main.login_fragment.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
class LoginFragment : QFragment(R.layout.login_fragment) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        emailField.setText(Rep.username)
        passwordField.setText(Rep.password)

        login.onClick {

            if (emailField.text.toString() != "" && passwordField.text.toString() != "") {
                Rep.username = emailField.text.toString()
                Rep.password = passwordField.text.toString()
                Rep.login(emailField.text.toString(), passwordField.text.toString())
                Rep.retrieveUser()
                Thread.sleep(3000)
                Rep.userInfo.observe(this, Observer {
                    if (it.type == "pacient") Router.showGeneralStatsFragment()
                    else {
                            Router.showUserListFragment()
                    }
                    Rep.addUser(it)
                })
            }
        }

    }
}
