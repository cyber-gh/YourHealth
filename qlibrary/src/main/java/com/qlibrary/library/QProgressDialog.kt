package com.qlibrary.library

import android.content.DialogInterface
import android.os.Bundle
import android.view.WindowManager
import com.qlibrary.R

class QProgressDialog : QDialog(R.layout.progress_dialog){

    var dismissable = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setStyle(AppCompatDialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen)
        dialog?.window?.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    fun persShow(){
        dismissable = false
        show()
    }

    fun persDismiss(){
        dismissable = true
        dismiss()
    }

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
        if(!dismissable) show()
    }
}