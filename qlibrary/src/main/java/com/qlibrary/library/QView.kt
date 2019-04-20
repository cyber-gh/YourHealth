package com.qlibrary.library

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout

abstract class QView : ConstraintLayout{
    abstract fun getLayoutId() : Int

    open fun onCreate(){
        View.inflate(context,getLayoutId(),this)
    }

    constructor(context: Context) : super(context) {onCreate()}

}