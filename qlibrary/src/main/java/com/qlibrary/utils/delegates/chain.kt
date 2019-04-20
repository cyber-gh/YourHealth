package com.qlibrary.utils.delegates

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class chain<R, T> : ReadWriteProperty<R, T> {
    private val onGet: () -> T
    private val onSet : (T) -> Unit

    constructor(onGet: () -> T,onSet : (T) -> Unit){
        this.onGet = onGet
        this.onSet = onSet
    }
    constructor(onGet: () -> T) : this(onGet,{})
    override fun getValue(thisRef: R, property: KProperty<*>): T = onGet()
    override fun setValue(thisRef: R, property: KProperty<*>, value: T)  = onSet(value)
}
