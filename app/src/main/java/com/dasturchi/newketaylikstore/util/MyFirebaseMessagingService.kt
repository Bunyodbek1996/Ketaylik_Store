package com.dasturchi.newketaylikstore.util


import com.dasturchi.newketaylikstore.network.repository.MainRepository
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class MyFirebaseMessagingService : FirebaseMessagingService(),KodeinAware {
    override val kodein by kodein()
    private val repository: MainRepository by instance()
    private val pref: PreferenceProvider by instance()
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        if (remoteMessage.notification != null) {
            val title = remoteMessage.notification!!.title
            val text = remoteMessage.notification!!.body
            NotificationHelper.displayNotification(applicationContext, title, text)
        }
    }

    override fun onNewToken(s: String) {
        Coroutines.main {
            repository.senFCM(pref.getToken().toString(),s)
        }
    }

}