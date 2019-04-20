package com.qlibrary.utils.extensions

import com.qlibrary.library.QRouter
import com.qlibrary.library.errAlert
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

typealias BS<T> = BehaviorSubject<T>
typealias PS<T> = PublishSubject<T>

fun <T> Subject<T>.emit(a:T): Subject<T>{
    this.onNext(a)
    return this
}

fun Subject<Unit>.emit() = this.onNext(Unit)

fun <T> Observable<T>.obsOnMain() = this.observeOn(AndroidSchedulers.mainThread())
fun <T> Observable<T>.subsOnIO() = this.subscribeOn(Schedulers.io())
fun <T> Observable<T>.doIO() = this.obsOnMain().subsOnIO()
fun <T> Observable<T>.consume(onOK: (T) -> Unit,onERR: (Throwable) -> Boolean = {true}, finally: ()->Unit = {}): Observable<T>{
    var d : Disposable? = null
    var b = false
    d = this.subscribe({
        onOK(it)
        finally()
        if(d != null) d!!.dispose() else b = true
    },{
        onERR(it)
        it.printStackTrace()
        if(!onERR(it)) errAlert(it.localizedMessage)
        if(d != null) d!!.dispose() else b = true
    },{
        finally()
        if(d != null) d!!.dispose() else b = true
    })
    if(b) d.dispose()
    return this
}

fun <T> Observable<T>.consumeWithProgress(onOK: (T) -> Unit,onERR: (Throwable) -> Boolean = {false}, finally: ()->Unit = {}): Observable<T> {
    QRouter.showProgress()

    this.ioConsume({
        onOK(it)
        QRouter.hideProgress()
    },{
        it.printStackTrace()
        if(!onERR(it)) errAlert(it.localizedMessage)
        QRouter.hideProgress()
    },finally)

    return this
}

fun <T> Observable<T>.consumeState(onOK: (T) -> Unit = {},onERR: (Throwable) -> Boolean = {false}, finally: ()->Unit = {}): Observable<Boolean>{
    val stateObs = BS.create<Boolean>()
    this.consume({
        onOK(it)
        stateObs.emit(true)
    },{
        stateObs.emit(false)
        onERR(it)
    },finally)
    return stateObs
}

fun syncObsWithCall(callback: ()->Unit, vararg observables: () -> Observable<Boolean>){

    fun helper(i: Int){
        if(i >= observables.size){
            callback()
        }else {
            observables[i]().consume ({
                helper(i + 1)
            })
        }
    }

    helper(0)
}


fun asyncObs(vararg observables: Observable<*>): BS<Boolean>{
    val rs = BS.create<Boolean>()
    var count = 0

    for(obs in observables) obs.consume({
        count++
        if(count == observables.size) rs.emit(true)
    },{
        rs.emit(false)
        false
    })

    return rs
}

fun syncObs(vararg observables: () -> Observable<*>): BS<Boolean>{
    val obs=BS.create<Boolean>()

    fun helper(i: Int){
        if(i >= observables.size){
            obs.emit(true)
        }else {
            observables[i]().consume ({
                helper(i + 1)
            },{
                obs.emit(false)
                false
            })
        }
    }

    helper(0)
    return obs
}


fun <T> Observable<T>.ioSubs(onOK: (T) -> Unit, onERR: (Throwable) -> Unit = {}, finally: ()->Unit = {}) = this
    .doIO()
    .subscribe({
        onOK(it)
        finally()
    },{
        onERR(it)
        finally()
    })


fun <T> Observable<T>.ioConsume(onOK: (T) -> Unit, onERR: (Throwable) -> Unit = {}, finally: ()->Unit = {}): Observable<T>{
    var d : Disposable? = null
    var b = false
    d = this.ioSubs(onOK,onERR,{
        finally()
        if(d != null) d!!.dispose()
        else b = true
    })
    if(b) d.dispose()
    return this
}

fun <T> Observable<T>.disposeOnEnd(listener: (T) -> Unit): Observable<T>{
    this.subscribe{
        listener(it)
    }
    return this
}
