package com.example.yourhealth.ui.views

import android.content.Context
import com.example.yourhealth.Data.Rep
import com.example.yourhealth.R
import com.example.yourhealth.Router
import com.example.yourhealth.models.UserInfo
import com.qlibrary.library.QRecycleListFragment
import com.qlibrary.library.QRecycleView
import com.qlibrary.utils.extensions.onClick
import kotlinx.android.synthetic.main.user_minimal_view.view.*

class UserListFragment : QRecycleListFragment<UserInfo, UserListFragment.UserView>() {


    companion object {
        fun newInstance(onProductItemSelected: (data: UserInfo) -> Unit = {},
                        popOnSelect: Boolean = false) = UserListFragment().apply {
            onItemClickListener = { it ,_,_ ->
                if(popOnSelect) fragmentManager!!.popBackStack()
                onProductItemSelected(it)
            }

            viewClass = UserView::class

            getMoreData = {
                Rep.getUsers()

            }
        }
    }


    class UserView(context: Context) : QRecycleView<UserInfo>(context) {

        override fun setContent(data: UserInfo) {
            name.text = data.type
            stat1.text = data.generalStats.bloodPressure.toString()

            stat2.text = data.generalStats.sugarLevel.toString()
            pacientVIew.onClick {
                Router.showGeneralStatsFragment(data)
            }
        }

        override fun getLayoutId() = R.layout.user_minimal_view
    }
}