package com.qlibrary.library

import android.app.Service
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

abstract class QService : Service(){

    val destrDisps = CompositeDisposable()


    fun <T> Observable<T>.dispOnDestroy(listener: (T) -> Unit){
        destrDisps.add(
                this.subscribe{ listener(it) }
        )
    }

    override fun onDestroy() {
        destrDisps.clear()
        destrDisps.dispose()
        super.onDestroy()
    }
}