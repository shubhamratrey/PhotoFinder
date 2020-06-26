package com.zoopzam.photofinder.views.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.zoopzam.photofinder.R
import com.zoopzam.photofinder.services.FirebaseAuthUserManager
import com.zoopzam.photofinder.utils.FragmentHelper
import com.zoopzam.photofinder.views.fragments.HomeFragment


class MainActivity : BaseActivity() {


    val RC_SIGN_IN = 12132
    private val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(HomeFragment.newInstance(), FragmentHelper.HOME)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                Log.d(
                        "onActivityResult",
                        "using FirebaseAuthUserManager ${FirebaseAuthUserManager.getFirebaseAuthToken()}"
                )
                FirebaseAuth.getInstance().currentUser?.getIdToken(true)
                        ?.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.d("onActivityResult", task.result!!.token!!)
                                FirebaseAuthUserManager.registerFCMToken()
                            }
                        }
                // ...
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
