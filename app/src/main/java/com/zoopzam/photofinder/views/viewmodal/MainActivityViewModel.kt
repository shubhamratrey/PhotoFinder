package com.zoopzam.photofinder.views.viewmodal

import com.zoopzam.photofinder.views.activity.BaseActivity
import com.zoopzam.photofinder.views.module.BaseModule
import com.zoopzam.photofinder.views.module.MainActivityModule

class MainActivityViewModel(activity: BaseActivity) : BaseViewModel(), MainActivityModule.IModuleListener {


    override fun onApiFailure(statusCode: Int, message: String) {
        viewListener.onApiFailure(statusCode, message)
    }

    val module = MainActivityModule(this)
    val viewListener = activity as MainActivityModule.IModuleListener
    override fun setViewModel(): BaseModule {
        return module
    }

}
