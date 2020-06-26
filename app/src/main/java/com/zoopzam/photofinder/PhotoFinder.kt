package com.zoopzam.photofinder

import android.app.Application
import android.content.IntentFilter
import android.net.ConnectivityManager
import com.zoopzam.photofinder.events.RxBus
import com.zoopzam.photofinder.events.RxEvent
import com.zoopzam.photofinder.services.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


class PhotoFinder : Application(), ConnectivityReceiverListener {
    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        appDisposable.dispose()
        if (!isConnected) {
            appDisposable.add(Observable.timer(1, TimeUnit.SECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        RxBus.publish(RxEvent.NetworkConnectivity(isConnected))
                    })
        } else {
            RxBus.publish(RxEvent.NetworkConnectivity(isConnected))
        }
    }

    @Volatile
    private var mIAPIService: IAPIService? = null
    @Volatile
    private var mIAPIServiceCache: IAPIService? = null

    private var connectivityReceiver: ConnectivityReceiver? = null

    var appDisposable: AppDisposable = AppDisposable()

    companion object {
        @Volatile
        private var application: PhotoFinder? = null

        @Synchronized
        fun getInstance(): PhotoFinder {
            return application!!
        }
    }

    override fun onCreate() {
        super.onCreate()
        application = this@PhotoFinder

        val intentFilter = IntentFilter()
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        connectivityReceiver = ConnectivityReceiver(this)
        registerReceiver(connectivityReceiver, intentFilter)
    }

    @Synchronized
    fun getAPIService(): IAPIService {
        if (mIAPIService == null) {
            mIAPIService = APIService.build()
        }
        return mIAPIService!!
    }

    @Synchronized
    fun getAPIService(cacheEnabled: Boolean): IAPIService {
        if (mIAPIService == null) {
            mIAPIService = APIService.build()
        }
        if (mIAPIServiceCache == null) {
            val cacheDuration = 3600.toLong()
            mIAPIServiceCache = APIService.build(this, cacheDuration)

        }
        return if (cacheEnabled) mIAPIServiceCache!! else mIAPIService!!
    }

}