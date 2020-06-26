package com.zoopzam.photofinder.views.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zoopzam.photofinder.views.activity.BaseActivity
import com.zoopzam.photofinder.views.viewmodal.MainActivityViewModel

class ActivityViewModelFactory(private val activity: BaseActivity) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(MainActivityViewModel::class.java) -> return MainActivityViewModel(activity) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}