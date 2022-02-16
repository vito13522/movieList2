package com.mindorks.framework.movielist

import  android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.mindorks.framework.retrofitcoctail.R
import com.mindorks.framework.movielist.model.User

private lateinit var database: DatabaseReference

class MainActivity : AppCompatActivity() {

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        this.onSignInResult(res)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database = Firebase.database.reference

        // Choose authentication providers
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build())

        // Create and launch sign-in intent
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()
        signInLauncher.launch(signInIntent)


    }


    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse // результат с экрана Firebaseauth
        if (result.resultCode == RESULT_OK) {
            Log.d("testLogs", "RegistrationActivity registration success ${response?.email}")

            // Successfully signed in
            val authUser = FirebaseAuth.getInstance().currentUser //создаем обьект текущего пользователя
            authUser?.let { //если обьект существует добавляем его в бд
                val email = it.email.toString()
                val uid = it.uid
                 val firebaseUser = User(email, uid)

                database.child("users").child(uid).setValue(firebaseUser)

                val intent = Intent(this, MoviesActivity::class.java)
                startActivity(intent)
//                Toast.makeText(this@MainActivity, "You clicked me", Toast.LENGTH_SHORT).show()
            }

        }

         else {
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            // ...
        }
    }
}