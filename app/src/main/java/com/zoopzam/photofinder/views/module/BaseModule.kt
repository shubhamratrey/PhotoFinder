package com.zoopzam.photofinder.views.module

import com.zoopzam.photofinder.PhotoFinder
import com.zoopzam.photofinder.services.AppDisposable


open class BaseModule {
    val application = PhotoFinder.getInstance()
    val apiService = application.getAPIService()
    val apiCacheService = application.getAPIService(true)
    var appDisposable = AppDisposable()

    fun onDestroy() {
        if (appDisposable != null) {
            appDisposable.dispose()
        }
    }

    fun getDisposable(): AppDisposable {
        if (appDisposable == null) {
            appDisposable = AppDisposable()
        }
        return appDisposable
    }


}