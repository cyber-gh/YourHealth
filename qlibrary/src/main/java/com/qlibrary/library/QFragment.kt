package com.qlibrary.library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.qlibrary.utils.extensions.BackPressListener
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

abstract class QFragment(val layoutId: Int) : Fragment(), BackPressListener {
    val pauseDisps = CompositeDisposable()
    val destrDisps = CompositeDisposable()

    open fun getActivityState() = QActivity.STATE.PARTICULAR
    open fun getAppBarTitle() : String? = null
    open fun getViewModel() : QViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        setUserVisibleHint(false);
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
        QRouter.hideProgress()
        super.onPause()
    }

    override fun onDestroy() {
        destrDisps.clear()
        destrDisps.dispose()
        super.onDestroy()
    }

    override fun onBackPressed() = true

    abstract class QArgs()
}