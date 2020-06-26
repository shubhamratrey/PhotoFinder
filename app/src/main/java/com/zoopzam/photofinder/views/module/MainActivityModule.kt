package com.zoopzam.photofinder.views.module

import com.zoopzam.photofinder.events.IBaseView

class MainActivityModule(val iModuleListener: IModuleListener) : BaseModule() {


    interface IModuleListener : IBaseView {
        fun onApiFailure(statusCode: Int, message: String)
    }

}