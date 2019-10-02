package dev.csshortcourse.assignmenttwo.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import androidx.lifecycle.AndroidViewModel
import dev.csshortcourse.assignmenttwo.util.debugger
import dev.csshortcourse.assignmenttwo.viewmodel.repository.AppRepository

/**
 * ViewModel instance
 */
open class AppViewModel(app: Application) : AndroidViewModel(app) {
    // Create repository instance
    protected val repo: AppRepository by lazy { AppRepository.get(app) }

    // Connectivity Manager
    private val connectivityManager: ConnectivityManager by lazy { app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager }

    // Global connection variables
    var isConnected: Boolean = false
    private var monitoring: Boolean = false

    private val callback: ConnectivityManager.NetworkCallback =
        object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                isConnected = true
            }

            override fun onUnavailable() {
                isConnected = false
            }
        }

    init {
        // Register listener for network connections
        registerNetworkListener()
    }

    private fun registerNetworkListener() {
        monitoring = true
        debugger("Registering network listener")
        try {
            connectivityManager.unregisterNetworkCallback(callback)
        } catch (e: Exception) {
            debugger("Cannot register network listener")
        }
    }

    private fun unregisterNetworkListener() {
        monitoring = false
        debugger("Unregistering network listener")
        try {
            connectivityManager.unregisterNetworkCallback(callback)
        } catch (e: Exception) {
            debugger("Cannot unregister network listener")
        }
    }

    override fun onCleared() {
        unregisterNetworkListener()
    }

}