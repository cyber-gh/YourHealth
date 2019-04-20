package com.qlibrary.utils.delegates

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty


class viewmodel<R, T: ViewModel>(val frag: Fragment,val modelClass: Class<T>) : ReadOnlyProperty<R, T> {
    var model: T? = null
    override fun getValue(thisRef: R, property: KProperty<*>): T {
        if(model == null) model = ViewModelProviders.of(frag)[modelClass]
        return model!!
    }
}