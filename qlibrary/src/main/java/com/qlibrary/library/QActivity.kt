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

    fun showFragment(fragment: Fragment, contentId: Int, addToBackStack: Boolean = true){

        val transaction = supportFragmentManager.beginTransaction()

        if(supportFragmentManager.fragments.size == 0) {
            transaction.add(contentId, fragment)
        }
        else {

            transaction.replace(contentId, fragment)
        }
        if(addToBackStack) transaction.addToBackStack(null)
        transaction.commit()
    }

    abstract fun initialize()
}