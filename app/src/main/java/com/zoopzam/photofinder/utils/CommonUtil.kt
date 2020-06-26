package com.zoopzam.photofinder.utils

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Environment
import android.view.inputmethod.InputMethodManager
import androidx.annotation.DimenRes
import com.zoopzam.photofinder.PhotoFinder
import java.io.File
import java.io.IOException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

object CommonUtil {

    val context = PhotoFinder.getInstance()
    var priorityAppList: MutableList<String> = ArrayList()

    /**
     * convert dimens to exact pixels
     */
    fun dpToPx(dp: Int): Int {
        val density = context.resources.displayMetrics.density
        return Math.round(dp.toFloat() * density)
    }

    /**
     * Checks whether text is null or empty or not
     */
    fun textIsEmpty(value: String?): Boolean {

        if (value == null)
            return true

        var empty = false

        val message = value.trim { it <= ' ' }

        if (message.isEmpty()) {
            empty = true
        }

        val isWhitespace = message.matches("^\\s*$".toRegex())

        if (isWhitespace) {
            empty = true
        }

        return empty
    }

    @Throws(IOException::class)
    fun createImageFile(context: Context): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageFileName = "IMG_" + timeStamp + "_"
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(imageFileName, ".jpg", storageDir)
    }

    fun getDimensionPixelSize(@DimenRes dimenRes: Int): Int {
        return context.resources.getDimensionPixelSize(dimenRes)
    }

    fun showKeyboard(context: Context?) {
        val inputMethodManager =
                context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    fun hideKeyboard(context: Context) {
        val inputManager =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val v = (context as Activity).currentFocus ?: return
        inputManager.hideSoftInputFromWindow(v.windowToken, 0)
    }

    fun isAppInstalled(context: Context, uri: String): Boolean {
        val pm = context.packageManager
        var app_installed: Boolean
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES)
            app_installed = true
        } catch (e: PackageManager.NameNotFoundException) {
            app_installed = false
        } catch (e: RuntimeException) {
            app_installed = false
        }

        return app_installed
    }

    fun getDateFromString(input: String): String? {
        val serverFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val dateFormat = SimpleDateFormat("EEE, d MMM yy")
        try {
            val date = serverFormat.parse(input)
            return dateFormat.format(date.time)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return null
    }

    fun getAlphaNumericString(n: Int): String { // chose a Character random from this String
        val alphaNumericString = ("ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz")
        // create StringBuffer size of AlphaNumericString
        val sb = StringBuilder(n)
        sb.append(SimpleDateFormat("yyyyMMddHHmmss").format(Date()))
        for (i in 0 until n) { // generate a random number between // 0 to AlphaNumericString variable length
            val index = (alphaNumericString.length
                    * Math.random()).toInt()
            // add Character one by one in end of sb
            sb.append(alphaNumericString[index])
        }
        return sb.toString()
    }
}