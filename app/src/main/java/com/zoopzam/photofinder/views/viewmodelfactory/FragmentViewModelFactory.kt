package com.zoopzam.photofinder.views.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zoopzam.photofinder.views.fragments.BaseFragment
import com.zoopzam.photofinder.views.viewmodal.HomeFragmentViewModel

class FragmentViewModelFactory(private val fragment: BaseFragment) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(HomeFragmentViewModel::class.java) -> return HomeFragmentViewModel(fragment) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}