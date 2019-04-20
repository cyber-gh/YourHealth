package com.qlibrary.library

import android.content.Context
import android.view.ViewGroup
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import kotlin.reflect.KClass

abstract class QSimpleView<T>(context: Context) : QView(context){
    val detDisps = CompositeDisposable()


    fun <T> Observable<T>.dispOnDetach(listener: (T) -> Unit){
        detDisps.add(
            this.subscribe{ listener(it) }
        )
    }

    abstract fun setContent(data: T)

    override fun onDetachedFromWindow() {
        detDisps.clear()
        super.onDetachedFromWindow()
    }
}

fun <T,V : QSimpleView<T>> List<T>.populate(viewGroup: ViewGroup, kClass: KClass<V>, clearExistent: Boolean = true){
    if(clearExistent) viewGroup.removeAllViews()
    for(el in this){
        viewGroup.addView(
            (kClass.constructors.first().call(viewGroup.context) as QSimpleView<T>).apply {
                setContent(el)
            }
        )
    }
}