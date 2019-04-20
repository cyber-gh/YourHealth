package com.qlibrary.library

import android.os.Bundle
import android.view.View
import com.qlibrary.R
import com.qlibrary.utils.Res
import com.qlibrary.utils.extensions.BS
import com.qlibrary.utils.extensions.consume
import com.qlibrary.utils.extensions.emit
import io.reactivex.Observable
import kotlinx.android.synthetic.main.simple_list_fragment.*
import kotlin.reflect.KClass

open class QSimpleListFragment<T,V: QSimpleView<T>> : QFragment(R.layout.simple_list_fragment){
    lateinit var dataObs : Observable<List<T>>
    lateinit var kClass: KClass<V>
    val dataSetObs = BS.create<Unit>()

    companion object {
        fun <T,V: QSimpleView<T>> newInstance(data: Res<List<T>>, kClass: KClass<V>): QSimpleListFragment<T, V> {
            return newInstance(data.onOK,kClass)
        }

        fun <T,V: QSimpleView<T>> newInstance(data: Observable<List<T>>, kClass: KClass<V>): QSimpleListFragment<T, V> {
            return QSimpleListFragment<T, V>().apply {
                init(data,kClass)
            }
        }
    }

    open fun init(data: Res<List<T>>, kClass: KClass<V>){
        this.dataObs = data.onOK
        this.kClass = kClass
    }

    open fun init(data: Observable<List<T>>, kClass: KClass<V>){
        this.dataObs = data
        this.kClass = kClass
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        QRouter.showProgress()
        dataObs.consume ({
            QRouter.hideProgress()
            if(container != null) {
                it.populate(container, kClass)
                dataSetObs.emit()
            }
        })
    }
}