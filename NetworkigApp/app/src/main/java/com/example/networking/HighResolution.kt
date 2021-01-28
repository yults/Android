package com.example.networking

import android.content.*
import android.content.Intent.CATEGORY_DEFAULT
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.high_resolution_activity.*

class HighResolution : AppCompatActivity() {
    private var connection: ServiceConnection? = null
    var myService: DownloadService? = null
    var isCon = false
    private val broadcast = ImageBroadcastReceiver()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.high_resolution_activity)
        val intentt = Intent(this,  DownloadService::class.java)
        intentt.putExtra("url", intent.extras?.getString("url"))
        connection = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName, binder: IBinder) {
                isCon = true
                myService = (binder as DownloadService.MyBinder).getService()
            }
            override fun onServiceDisconnected(name: ComponentName) {
                isCon = false
            }
        }
        startService(intentt)
        bindService(intentt, connection as ServiceConnection, 0)
        val intentFilter = IntentFilter(
            "SEND"
        )
        intentFilter.addCategory(CATEGORY_DEFAULT)
        registerReceiver(broadcast, intentFilter)
    }
    inner class ImageBroadcastReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            if (myService?.bitmap != null) {
                highRes.setImageBitmap(myService?.bitmap)
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(broadcast)
        if (isCon) {
            connection?.let { unbindService(it) }
        }
    }
}
