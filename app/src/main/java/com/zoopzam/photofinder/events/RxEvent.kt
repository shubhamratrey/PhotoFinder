package com.zoopzam.photofinder.events

import android.content.Intent
import com.zoopzam.partner.constants.RxEventType

class RxEvent {
    data class NetworkConnectivity(var isConnected: Boolean)
    data class ActivityResult(var requestCode: Int, var resultCode: Int, var data: Intent?)
    class Action(var eventType: RxEventType, vararg val items: Any)
}