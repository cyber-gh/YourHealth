package com.example.yourhealth.Data

import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.qlibrary.library.errAlert
import com.qlibrary.utils.Res
import com.qlibrary.utils.delegates.prefString
import com.qlibrary.utils.extensions.toast

val Rep = Repository()

class Repository {
    private  var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var user: FirebaseUser? = null

    

    var username by prefString("")
    var password by prefString("")
    var userTYpe  = UserType.PACIENT


    fun login(username: String, password: String) {
        auth.signInWithEmailAndPassword(username, password).addOnCompleteListener {
            task -> if (task.isSuccessful) {
            Log.d(TAG, "signInWithEmail:success")
            user = auth.currentUser
        }   else {
            Log.w(TAG, "signInWithEmail:failure", task.exception)
            errAlert("Authentication failed")
            
        }
        }
    }
    

}