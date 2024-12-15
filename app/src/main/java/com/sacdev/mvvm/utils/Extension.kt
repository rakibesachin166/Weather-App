package com.sacdev.mvvm.utils

import android.content.Context
import android.widget.Toast
import retrofit2.HttpException
import java.io.IOException
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.view.View

fun Exception.getErrorMessage(): String {
    return when (this) {
        is IOException -> "We are unable to connect to the server. Please check your internet connection and try again."
        is HttpException -> {
            val code = this.code()
            when (code) {
                404 -> "The requested information could not be found."
                500 -> "The server is currently facing issues."
                else -> "Something went wrong "
            }
        }
        else -> "Something went wrong. "
    }
}
fun View.makeVisible(makeVisible: Boolean = true) {
    visibility = if (makeVisible) View.VISIBLE else View.GONE
}

fun Context.showToast(string: String , duration: Int = Toast.LENGTH_SHORT){
    Toast.makeText(this, string, duration).show()
}

fun Context.showErrorToast(string: String , duration: Int = Toast.LENGTH_SHORT){
    Toast.makeText(this, string, duration).show()
}



fun Context.showSuccessToast(string: String , duration: Int = Toast.LENGTH_SHORT){
    Toast.makeText(this, string, duration).show()
}

fun Context.isNetworkAvailable(): Boolean {
    val connectivityManager =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

    return capabilities != null &&
            (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
}

