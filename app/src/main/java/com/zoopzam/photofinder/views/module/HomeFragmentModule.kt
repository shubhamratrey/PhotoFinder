package com.zoopzam.photofinder.views.module

import com.zoopzam.photofinder.constants.NetworkConstants
import com.zoopzam.photofinder.models.responses.HomeDataResponse
import com.zoopzam.photofinder.services.CallbackWrapper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class HomeFragmentModule(val listener: APIModuleListener) : BaseModule() {

    fun getHomeData(pageNo: Int, folderPath:String?) {
        val hashMap = HashMap<String, String>()
        hashMap[NetworkConstants.API_PATH_QUERY_PAGE] = pageNo.toString()
        if (folderPath!=null) {
            hashMap[NetworkConstants.API_PATH_QUERY_FOLDER_PATH] = folderPath
        }
        appDisposable.add(apiService
                .getHomeData(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(object : CallbackWrapper<Response<HomeDataResponse>>() {
                    override fun onSuccess(t: Response<HomeDataResponse>) {
                        if (t.isSuccessful) {
                            listener.onHomeApiSuccess(t.body()!!)
                        } else {
                            listener.onApiFailure(t.code(), "empty body")
                        }
                    }

                    override fun onFailure(code: Int, message: String) {
                        listener.onApiFailure(code, message)
                    }
                }))
    }

    interface APIModuleListener {
        fun onHomeApiSuccess(response: HomeDataResponse?)
        fun onApiFailure(statusCode: Int, message: String)
    }
}