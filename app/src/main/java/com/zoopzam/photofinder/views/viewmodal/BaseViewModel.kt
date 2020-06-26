package com.zoopzam.photofinder.views.viewmodal

import android.os.Handler
import androidx.lifecycle.ViewModel
import com.zoopzam.photofinder.services.AppDisposable
import com.zoopzam.photofinder.views.module.BaseModule

open abstract class BaseViewModel : ViewModel() {


    private var baseModule: BaseModule? = null

    abstract fun setViewModel(): BaseModule

    init {
        init()
    }

    private fun init() {
        Handler().postDelayed({
            baseModule = setViewModel()

        }, 200)
    }

    fun onDestroy() {
        baseModule?.onDestroy()
    }

    fun getAppDisposable(): AppDisposable {
        if (baseModule == null) {
            baseModule = setViewModel()
        }
        return baseModule?.getDisposable()!!
    }
}