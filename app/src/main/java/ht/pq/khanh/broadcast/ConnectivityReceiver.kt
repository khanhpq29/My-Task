package ht.pq.khanh.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import ht.pq.khanh.bus.NetworkEvent
import ht.pq.khanh.bus.RxBus
import ht.pq.khanh.extension.showToast


/**
 * Created by khanhpq on 11/1/17.
 */
class ConnectivityReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val networkEvent = NetworkEvent(isConnected(context))
        RxBus.getInstance().send(networkEvent)
    }

    private fun isConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }
}