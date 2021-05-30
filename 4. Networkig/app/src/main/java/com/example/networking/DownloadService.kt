package com.example.networking

import android.app.Service
import android.content.Intent
import android.content.Intent.CATEGORY_DEFAULT
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Binder
import android.os.IBinder
import android.util.Log
import java.lang.ref.WeakReference
import java.net.URL
import android.os.AsyncTask

class DownloadService : Service() {
    private var binder = MyBinder()
    var bitmap: Bitmap? = null
    private var prev : String? = null
    override fun onStartCommand (intent: Intent?, flags: Int, startId: Int): Int {
        return START_NOT_STICKY
    }
    private class Downloading (activity : DownloadService) : AsyncTask<String, Unit, Bitmap>() {
        private val reference = WeakReference(activity)
        override fun doInBackground(vararg params: String?): Bitmap? {
            var bitmap : Bitmap? = null
            try {
                val inputStream = URL(params[0]).openStream()
                bitmap = BitmapFactory.decodeStream(inputStream)
            } catch (e : Exception) {
                e.printStackTrace()
            }
            return bitmap
        }
        override fun onPostExecute(res: Bitmap?) {
            Log.i("downloading", "Downloading")
            reference.get()?.bitmap = res
            val intent = Intent()
            intent.action = "SEND"
            intent.addCategory(CATEGORY_DEFAULT)
            reference.get()?.sendBroadcast(intent)
        }
    }
    override fun onBind(intent: Intent): IBinder {
        val url = intent.getStringExtra("url")
        if (!url.equals(prev)) {
            prev = url
            Downloading(this).execute(url)
        }
        return binder
    }
    override fun onRebind(intent: Intent?) {
        val url = intent?.getStringExtra("url")
        if (!url.equals(prev)) {
            prev = url
            Downloading(this).execute(url)
        }
        else {
            val respIntent = Intent()
            respIntent.action = "SEND"
            respIntent.addCategory(CATEGORY_DEFAULT)
            sendBroadcast(respIntent)
        }
    }
    inner class MyBinder : Binder() {
        fun getService() : DownloadService {
            return this@DownloadService
        }
    }
    override fun onUnbind(intent: Intent?): Boolean {
        return true
    }
}
