package com.qlibrary.library

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.MapView

class QMapVIew : MapView {

    constructor(var1: Context) : super(var1)

    constructor(var1: Context, var2: AttributeSet): super(var1,var2)

    constructor(var1: Context, var2: AttributeSet, var3: Int) : super(var1, var2, var3)

    constructor(var1: Context, var2: GoogleMapOptions): super(var1,var2)


    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        when(ev?.action){
            MotionEvent.ACTION_UP -> parent.requestDisallowInterceptTouchEvent(false)
            MotionEvent.ACTION_DOWN -> parent.requestDisallowInterceptTouchEvent(true)
        }
        return super.dispatchTouchEvent(ev)
    }

}