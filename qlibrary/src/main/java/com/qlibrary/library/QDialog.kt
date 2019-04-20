package com.qlibrary.library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.FragmentManager
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

abstract class QDialog(val layoutId: Int) : AppCompatDialogFragment() {
    val pauseDisps = CompositeDisposable()
    val destrDisps = CompositeDisposable()

    open fun getViewModel() : QViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            = inflater.inflate(layoutId,container,false)

    fun <T> Observable<T>.dispOnPause(listener: (T) -> Unit){
        pauseDisps.add(
                this.subscribe{ listener(it) }
        )
    }

    fun <T> Observable<T>.dispOnDestroy(listener: (T) -> Unit){
        destrDisps.add(
                this.subscribe{ listener(it) }
        )
    }


    override fun onPause() {
        pauseDisps.clear()
        super.onPause()
    }

    override fun onDestroy() {
        destrDisps.clear()
        destrDisps.dispose()
        super.onDestroy()
    }


    fun show(manager:FragmentManager){show(manager,"dialog")}
    fun show(){show(QRouter.activity.supportFragmentManager,"dialog")}

}