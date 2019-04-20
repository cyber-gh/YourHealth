package com.qlibrary.utils.delegates


import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.qlibrary.BuildConfig
import com.qlibrary.library.QRouter
import com.qlibrary.library.QRouter.activity
import com.qlibrary.utils.extensions.BS
import com.qlibrary.utils.extensions.consume
import com.qlibrary.utils.extensions.emit
import com.qlibrary.utils.serialization.Serializer
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty
import java.io.Serializable

val appId = BuildConfig.APPLICATION_ID
val appVersion = BuildConfig.VERSION_CODE.toString()

class prefString<R>(var value: String = "") : ReadWriteProperty<R, String> {
    lateinit var pref : SharedPreferences
    private var initialized = false
    private var id = ""

    fun create(){
        initialized = true
        pref = QRouter.activity.getSharedPreferences(appId,MODE_PRIVATE)
        if(!pref.contains(id)) pref.edit().putString(id,value).apply()
    }

    override fun getValue(thisRef: R, property: KProperty<*>): String {
        id = property.name
        if(!initialized) create()
        return pref.getString(id, null)
    }

    override fun setValue(thisRef: R, property: KProperty<*>, value: String) {
        id = property.name
        if(!initialized) create()
        pref.edit().putString(id,value).apply()
    }
}

class prefBoolean<R>(var value: Boolean = false) : ReadWriteProperty<R, Boolean> {
    lateinit var pref : SharedPreferences
    private var initialized = false
    private var id = ""

    fun create(){
        initialized = true
        pref = QRouter.activity.getSharedPreferences(appId,MODE_PRIVATE)
        if(!pref.contains(id)) pref.edit().putBoolean(id,value).apply()
    }

    override fun getValue(thisRef: R, property: KProperty<*>): Boolean {
        id = property.name
        if(!initialized) create()
        return pref.getBoolean(id,false)
    }

    override fun setValue(thisRef: R, property: KProperty<*>, value: Boolean) {
        id = property.name
        if(!initialized) create()
        pref.edit().putBoolean(id,value).apply()
    }

}

class prefInt<R>(var value: Int = 0) : ReadWriteProperty<R, Int> {
    lateinit var pref : SharedPreferences
    private var initialized = false
    private var id = ""

    fun create(){
        initialized = true
        pref = QRouter.activity.getSharedPreferences(appId,MODE_PRIVATE)
        if(!pref.contains(id)) pref.edit().putInt(id,value).apply()
    }

    override fun getValue(thisRef: R, property: KProperty<*>): Int {
        id = property.name
        if(!initialized) create()
        return pref.getInt(id,0)
    }

    override fun setValue(thisRef: R, property: KProperty<*>, value: Int) {
        id = property.name
        if(!initialized) create()
        pref.edit().putInt(id,value).apply()
    }

}


val saveCacheObs = BS.create<Unit>()
fun SaveCache() = saveCacheObs.emit()

class prefSerial<R,T : Serializable>(var value: T) : ReadWriteProperty<R, T> {

    lateinit var pref : SharedPreferences
    private var initialized = false
    private var id = ""

    fun create(){
        initialized = true
        pref = activity.getSharedPreferences(appId,MODE_PRIVATE)

        if(pref.contains(id)){
            val str = pref.getString(id,null)
            value = (Serializer.stringToObject(str) as? T) ?: value
        }

        saveCacheObs.consume ({
            pref.edit().putString(id,Serializer.objectToString(value)).apply()
        })
    }

    override fun getValue(thisRef: R, property: KProperty<*>): T {
        id = property.name
        if(!initialized) create()
        return value
    }

    override fun setValue(thisRef: R, property: KProperty<*>, value: T) {
        id = property.name
        if(!initialized) create()
        this.value = value
    }
}

class prefSerialN<R,T : Serializable?>(var value: T? = null) : ReadWriteProperty<R, T?> {

    lateinit var pref : SharedPreferences
    private var initialized = false
    private var id = ""

    fun create(){
        initialized = true
        pref = activity.getSharedPreferences(appId,MODE_PRIVATE)

        if(pref.contains(id)){
            val str = pref.getString(id,null)
            value = if(str == "") null
            else Serializer.stringToObject(str) as T
        }

        saveCacheObs.consume ({
            pref.edit().putString(id,Serializer.objectToString(value)).apply()
        })
    }

    override fun getValue(thisRef: R, property: KProperty<*>): T? {
        id = property.name
        if(!initialized) create()
        return value
    }

    override fun setValue(thisRef: R, property: KProperty<*>, value: T?) {
        id = property.name
        if(!initialized) create()
        this.value = value
    }
}



