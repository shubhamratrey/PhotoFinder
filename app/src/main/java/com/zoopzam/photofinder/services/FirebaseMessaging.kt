package com.zoopzam.photofinder.services

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.zoopzam.photofinder.constants.BundleConstants
import com.zoopzam.partner.constants.FCMMessageType
import com.zoopzam.partner.constants.IntentConstants
import com.zoopzam.partner.constants.NotificationKeys
import com.zoopzam.photofinder.R
import com.zoopzam.photofinder.utils.CommonUtil
import com.zoopzam.photofinder.views.activity.MainActivity
import java.util.concurrent.atomic.AtomicInteger

class FirebaseMessaging : FirebaseMessagingService() {

    private val TAG = FirebaseMessaging::class.java.simpleName
    private val NOTIFICATION_ID = AtomicInteger(0)
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated.
        Log.d(TAG, remoteMessage.data.toString() + "  " + remoteMessage.notification.toString())
        if (remoteMessage.data!=null){
            Log.d(TAG, remoteMessage.data[NotificationKeys.DE_NOTIFICATION_TYPE]!!)
            showNotification(remoteMessage.data, applicationContext)
        }
    }

    override fun onNewToken(s: String) {
        super.onNewToken(s)
        Log.d(TAG, s)
    }

    @SuppressLint("ObsoleteSdkInt")
    fun showNotification(data: Map<String, String>, context: Context) {

        val message = data[NotificationKeys.MESSAGE]
        val description = data[NotificationKeys.DESCRIPTION]
        val imageUrl = data[NotificationKeys.IMAGE]
        val uriString = data[NotificationKeys.URI]
        val notificationType = data[NotificationKeys.NOTIFICATION_TYPE]
        val dataType = data[NotificationKeys.TYPE]
        val isSilent = java.lang.Boolean.valueOf(data[NotificationKeys.IS_SILENT])

        var notification_id: String? = data[NotificationKeys.NOTIFICATION_ID]

        if (notification_id == null) {
            notification_id = "not available"
        }

        val uri = Uri.parse(uriString)
//        var image: Bitmap? = null
//        try {
//            if (!CommonUtil.textIsEmpty(imageUrl)) {
//                // image = ImageHelper.getInstance().getBitmapSync(imageUrl, 1024, 500)//getBitmapfromUrl(imageUrl);
//                image = bitmap
//            }
//        } catch (e: InterruptedException) {
//            e.printStackTrace()
//        } catch (e: ExecutionException) {
//            e.printStackTrace()
//        } catch (e: IllegalArgumentException) {
//            e.printStackTrace()
//        }

        var channelId = data[NotificationKeys.N_CHANNEL_ID]
        var channelName = data[NotificationKeys.N_CHANNEL_NAME]
        val channelDescription = data[NotificationKeys.N_CHANNEL_DESCRIPTION]
        val buttonLabel =
                if (data.containsKey(NotificationKeys.BUTTON_LABEL)) data[NotificationKeys.BUTTON_LABEL] else ""
        var channelPriority = NotificationManager.IMPORTANCE_MAX
        if (data.containsKey(NotificationKeys.N_CHANNEL_PRIORITY)) {
            channelPriority = Integer.parseInt(data[NotificationKeys.N_CHANNEL_PRIORITY]!!)
        }

        if (CommonUtil.textIsEmpty(channelId) || CommonUtil.textIsEmpty(channelName)) {
            channelId = NotificationKeys.NOTIFICATION_CHANNEL_SHOW_PODCAST
            channelName = context.getString(R.string.app_name)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setupChannels(channelId!!, channelName!!, channelDescription, channelPriority, context)
        }
        val notificationId = NOTIFICATION_ID.incrementAndGet()
        val notificationBuilder = NotificationCompat.Builder(context, channelId!!)
                .setAutoCancel(true)
//                .setColor(CommonUtil.getColorFromAttr(R.attr.orange))
                .setDeleteIntent(getDeleteIntent(uri, notification_id, -1, context))
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            when (channelPriority) {
                NotificationManager.IMPORTANCE_MAX -> notificationBuilder.priority =
                        NotificationCompat.PRIORITY_MAX
                NotificationManager.IMPORTANCE_HIGH -> notificationBuilder.priority =
                        NotificationCompat.PRIORITY_HIGH
                NotificationManager.IMPORTANCE_DEFAULT -> notificationBuilder.priority =
                        NotificationCompat.PRIORITY_DEFAULT
                NotificationManager.IMPORTANCE_LOW -> notificationBuilder.priority =
                        NotificationCompat.PRIORITY_LOW
            }
        }

        if (description != null) {
            notificationBuilder.setContentText(description)
        }
        if (message != null) {
            notificationBuilder.setContentTitle(message)
        }

        if (notificationType != null && notificationType.isNotEmpty()) {
            when (notificationType) {
                FCMMessageType.TITLE_DESCRIPTION -> if (description != null) {
                    notificationBuilder.setStyle(NotificationCompat.BigTextStyle().bigText(description))
                            .setContentIntent(getContentIntent(uri, notification_id, -1, context))
                    if (!CommonUtil.textIsEmpty(buttonLabel)) {
                        notificationBuilder.addAction(0, buttonLabel, getContentIntent(uri, notification_id, -1, context))
                    }
//                    if (image != null) {
//                        notificationBuilder.setLargeIcon(image)
//                    }
                }
            }
        }

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(notificationId, notificationBuilder.build())
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun setupChannels(channel_id: String, channelName: String, channelDescription: String?, priority: Int, context: Context) {
        val adminChannel = NotificationChannel(channel_id, channelName, priority)
        adminChannel.enableLights(true)
        adminChannel.lightColor = Color.RED
        adminChannel.enableVibration(true)
        val sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val audioAttributes = AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build()
        adminChannel.setSound(sound, audioAttributes)

        if (channelDescription != null) {
            adminChannel.description = channelDescription
        }
        val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(adminChannel)
    }

    private fun getDeleteIntent(uri: Uri, notificationId: String, notificationIdInt: Int, context: Context): PendingIntent {
        val bundle = Bundle()
        bundle.putString(BundleConstants.NOTIFICATION_URI, uri.toString())
        bundle.putString(BundleConstants.NOTIFICATION_ID, notificationId)

        val intent = Intent(context, MainActivity::class.java)
        intent.action = IntentConstants.NOTIFICATION_DISMISS
        intent.putExtra(IntentConstants.NOTIFICATION_DISMISS, bundle)
        intent.putExtra(IntentConstants.NOTIFICATION_DISMISS_ID, notificationIdInt)

        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)
    }

    private fun getContentIntent(uri: Uri, notificationId: String, notificationIdInt: Int, context: Context): PendingIntent {
        val bundle = Bundle()
        bundle.putString(BundleConstants.NOTIFICATION_ID, notificationId)
        bundle.putString(BundleConstants.NOTIFICATION_URI, uri.toString())
        val intent = Intent(context, MainActivity::class.java)
        intent.data = uri
        intent.putExtra(IntentConstants.NOTIFICATION_TAPPED, bundle)
        intent.putExtra(IntentConstants.NOTIFICATION_DISMISS_ID, notificationIdInt)
        intent.action = IntentConstants.NOTIFICATION_URI
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        return PendingIntent.getActivity(context, 0 /* Request code */, intent, PendingIntent.FLAG_ONE_SHOT)
    }
}
