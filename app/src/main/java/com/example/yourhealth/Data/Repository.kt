package com.example.yourhealth.Data

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.yourhealth.Router
import com.example.yourhealth.models.GeneralStats
import com.example.yourhealth.models.UserInfo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.qlibrary.library.errAlert
import com.qlibrary.utils.delegates.prefString
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import com.qlibrary.utils.Res
import com.qlibrary.utils.extensions.BS
import com.qlibrary.utils.extensions.emit


val Rep = Repository()

class Repository {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var user: FirebaseUser? = null
    private val database = FirebaseDatabase.getInstance()

    var userGeneralStats = MutableLiveData<GeneralStats>()
    var userInfo = MutableLiveData<UserInfo>()
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

        }   else {
            Log.w(TAG, "signInWithEmail:failure", task.exception)
            errAlert("Authentication failed")
            
            }
        }
    }

    fun getGeneralStats() {
        val ref = database.getReference(username.extractUser() + "/generalStats")

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                userGeneralStats.value = dataSnapshot.getValue(GeneralStats::class.java)
                Log.d(TAG, "Value retrieved: " )
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
    }

    fun retrieveUser() {
        val ref = database.getReference(username.extractUser())

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                userInfo.value = dataSnapshot.getValue(UserInfo::class.java)
                Log.d(TAG, "Value retrieved: " )
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
    }

    fun addUser(usr: UserInfo) {
        val ref = database.getReference("doctortest/")
        usr.type == "doctor"

        ref.setValue(usr)
    }


    fun getUsers(): BS<List<UserInfo>> {
        val returnObs = BS.create<List<UserInfo>>()
        val ref = database.getReference()


        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val rs = mutableListOf<UserInfo>()
                for (postSnapshot in dataSnapshot.children) {
                    val retriev = postSnapshot.getValue(UserInfo::class.java)
                    if (retriev != null && retriev.type == "pacient") rs.add(retriev)
                }
                returnObs.emit(rs as List<UserInfo>)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
                // ...
                returnObs.onError(Throwable(message = "Cant connect to database"))
            }
        })

        return returnObs

    }
    

}


fun String.extractUser(): String {
    var rs: String = ""
    for (letter in this) {
        if (letter == '@') return rs
        rs += letter
    }
    return rs
}