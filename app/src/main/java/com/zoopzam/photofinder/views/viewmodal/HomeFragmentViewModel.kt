package com.zoopzam.photofinder.views.viewmodal

import com.zoopzam.photofinder.models.responses.HomeDataResponse
import com.zoopzam.photofinder.views.fragments.BaseFragment
import com.zoopzam.photofinder.views.module.BaseModule
import com.zoopzam.photofinder.views.module.HomeFragmentModule

class HomeFragmentViewModel(fragment: BaseFragment) : BaseViewModel(), HomeFragmentModule.APIModuleListener {

    val module = HomeFragmentModule(this)
    val listener = fragment as HomeFragmentModule.APIModuleListener


    override fun onApiFailure(statusCode: Int, message: String) {
        listener.onApiFailure(statusCode, message)
    }

    override fun onHomeApiSuccess(response: HomeDataResponse?) {
        listener.onHomeApiSuccess(response)
    }

    override fun setViewModel(): BaseModule {
        return module
    }

    fun getHomeData(pageNo: Int, folderPath:String?) {
        module.getHomeData(pageNo, folderPath)
    }

}