package com.zoopzam.photofinder.services

import com.zoopzam.photofinder.constants.NetworkConstants
import com.zoopzam.partner.models.responses.GenericResponse
import com.zoopzam.photofinder.models.responses.HomeDataResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.*

interface IAPIService {


    @FormUrlEncoded
    @POST("${NetworkConstants.V1}/users/register-fcm/")
    fun registerFCM(
            @Field("app_name") appName: String,
            @Field("os_type") osType: String,
            @Field("app_instance_id") appInstanceId: String,
            @Field("app_build_number") appBuildNumber: Int,
            @Field("installed_version") installedVersion: String,
            @Field("fcm_token") fcmToken: String
    ): Observable<Response<GenericResponse>>

    @FormUrlEncoded
    @POST("${NetworkConstants.V1}/users/unregister-fcm/")
    fun unregisterFCM(@Field("fcm_token") fcmToken: String): Observable<Response<String>>


    @GET("${NetworkConstants.V1}/folders/list/")
    fun getHomeData(@QueryMap queryMap: Map<String, String>): Observable<Response<HomeDataResponse>>

    
}