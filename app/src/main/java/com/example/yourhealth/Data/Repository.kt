package com.example.yourhealth.Data

import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.yourhealth.Router
import com.example.yourhealth.models.GeneralInfo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.qlibrary.library.errAlert
import com.qlibrary.utils.Res
import com.qlibrary.utils.delegates.prefString
import com.qlibrary.utils.extensions.toast
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener



val Rep = Repository()

class Repository {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var user: FirebaseUser? = null
    private val database = FirebaseDatabase.getInstance()

    var userGeneralStats = MutableLiveData<GeneralInfo>()

    init {
        
    }

    var username by prefString("")
    var password by prefString("")
    var userTYpe  = UserType.PACIENT

    fun login(username: String, password: String){
        auth.signInWithEmailAndPassword(username, password).addOnCompleteListener {
            task -> if (task.isSuccessful) {
            Log.d(TAG, "signInWithEmail:success")
            user = auth.currentUser
            Router.showGeneralStatsFragment()

        }   else {
            Log.w(TAG, "signInWithEmail:failure", task.exception)
            errAlert("Authentication failed")
            
            }
        }
    }

    fun loadUserGeneralStats() {
        
    }

    fun getGeneralStats() {
        val ref = database.getReference("test/GeneralStats")

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                userGeneralStats.value = dataSnapshot.getValue(GeneralInfo::class.java)
                Log.d(TAG, "Value retrieved: " )
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
    }

    
    

}