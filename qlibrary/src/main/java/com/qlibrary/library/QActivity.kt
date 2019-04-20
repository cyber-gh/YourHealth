package com.qlibrary.library


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

abstract class QActivity : AppCompatActivity() {

    companion object {
        val APPBAR = 1
    }

    enum class STATE(val x: Int) { PARTICULAR(APPBAR), FULLSCREEN(0) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        QRouter.init(this)
        initialize()
    }

    abstract fun initialize()
}