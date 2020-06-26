package com.zoopzam.photofinder.views.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.zoopzam.photofinder.constants.Constants
import com.zoopzam.photofinder.R
import com.zoopzam.photofinder.events.RxBus
import com.zoopzam.photofinder.events.RxEvent
import com.zoopzam.photofinder.utils.FragmentHelper


@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {


    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun showToast(message: String?, length: Int) {
        if (message != null && !isFinishing) {
            Toast.makeText(this, message, length).show()
        }
    }

    fun addFragment(fragment: Fragment, tag: String?=null) {
        FragmentHelper.add(R.id.container, supportFragmentManager, fragment, tag!!)
    }

    fun replaceFragment(fragment: Fragment, tag: String) {
        FragmentHelper.replace(R.id.container, supportFragmentManager, fragment, tag)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Constants.GALLERY) {
            RxBus.publish(RxEvent.ActivityResult(requestCode, resultCode, data))
            return
        }
    }

}