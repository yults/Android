package com.example.networking

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException
import java.io.InputStreamReader
import java.lang.ref.WeakReference
import java.net.URL

class MainActivity : AppCompatActivity() {
    companion object {
        const val COMMAND = "COMMAND"
    }
    private var command : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        stopService(Intent(this, DownloadService::class.java))
        command = savedInstanceState?.getString(COMMAND)
        setContentView(R.layout.activity_main)
        if (command == null) {
            ImageList(this).execute("")
            Log.i("downloading", "Downloading")
        }
        else {
            containRecyclerView(command.toString())
        }
    }
    override fun onResume() {
        super.onResume()
        stopService(Intent(this, DownloadService::class.java))
    }
    private class ImageList(activity: MainActivity) : AsyncTask<String, Unit, String>(){
        private val reference = WeakReference(activity)
        override fun doInBackground(vararg params: String?): String? {
            var res : String? = null
            try {
                InputStreamReader(URL("https://picsum.photos/v2/list?limit=10").openConnection().getInputStream()).use {
                    res = it.readText()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return res
        }
        override fun onPostExecute(res: String?) {
            if (res != null)
                reference.get()?.containRecyclerView(res)
        }
    }
    fun containRecyclerView(str: String) {
        command = str
        val list : List<Image> = Gson().fromJson(command, Array<Image>::class.java).toList()
        val manager = LinearLayoutManager(this)
        RecyclerView.apply {
            layoutManager = manager
            adapter = Adapter(list) {
                val intent = Intent(this@MainActivity, HighResolution::class.java)
                intent.putExtra("url", (it.download_url))
                startActivity(intent)
            }
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(COMMAND, command)
        super.onSaveInstanceState(outState)
    }
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        command = savedInstanceState.getString(COMMAND)
    }
}