package com.zoopzam.photofinder.services.sharedpreference

import android.text.TextUtils
import com.google.gson.Gson
import com.zoopzam.photofinder.utils.CommonUtil
import com.zoopzam.partner.services.sharedpreference.SharedPreferences


object SharedPreferenceManager {

    val sharedPreferences = SharedPreferences

    private val TAG = SharedPreferenceManager::class.java.simpleName

    private const val FIREBASE_AUTH_TOKEN = "firebase_auth_token"
    private const val FCM_REGISTERED_USER = "fcm_registered_user"
    private const val USER = "user"
    private const val RETAILER = "retailer"
    private const val RETAILER_DETAILS_ADDED = "retailer_details_added"
    private const val AWS_CONFIG = "aws_config"
    private const val CART_LIST = "cart_list"


    fun storeFirebaseAuthToken(firebaseAuthToken: String) {
        SharedPreferences.setString(FIREBASE_AUTH_TOKEN, firebaseAuthToken)
    }

    fun getFirebaseAuthToken(): String {
        return SharedPreferences.getString(FIREBASE_AUTH_TOKEN, "")!!
    }


    fun isFCMRegisteredOnserver(userId: String?): Boolean {
        return if (userId == null || TextUtils.isEmpty(userId)) {
            false
        } else SharedPreferences.getBoolean(FCM_REGISTERED_USER + userId, false)
    }

    fun setFCMRegisteredOnserver(userId: String) {
        SharedPreferences.setBoolean(FCM_REGISTERED_USER + userId, true)
    }
}