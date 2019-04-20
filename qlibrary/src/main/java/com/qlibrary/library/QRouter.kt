package com.qlibrary.library

import com.qlibrary.utils.extensions.resStr


object QRouter {
    lateinit var activity: QActivity
    var progressDialog: QProgressDialog? = null

    fun init(activity: QActivity){
        this.activity = activity
    }

    fun setTitle(title: String){
        activity.supportActionBar?.title = title
    }

    fun setTitle(titleResId: Int){
        setTitle(titleResId.resStr())
    }

    fun runOnUiThread(action: () -> Unit) {
        activity.runOnUiThread(action)
    }

    fun showProgress(){
        if(progressDialog == null){
            progressDialog = QProgressDialog()
            progressDialog?.persShow()
        }
    }

    fun hideProgress(){
        progressDialog?.persDismiss()
        progressDialog = null
    }

}