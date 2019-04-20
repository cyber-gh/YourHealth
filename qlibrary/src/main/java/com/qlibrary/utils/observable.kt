package com.qlibrary.utils

import com.qlibrary.utils.extensions.BS
import com.qlibrary.utils.extensions.consume
import com.qlibrary.utils.extensions.emit


class Res<V>{
    val onOK: BS<V> = BS.create<V>()
    val onErr: BS<Throwable> = BS.create<Throwable>()
    val finally: BS<Unit> = BS.create<Unit>()

    constructor(){
        onOK.consume ({
            finally.emit()
        })
        onErr.consume ({
            finally.emit()
        })
    }

    constructor(value: V) : this(){
        onOK.emit(value)
    }
}
