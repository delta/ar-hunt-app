package edu.nitt.delta.orientation22.di.storage

import android.app.DownloadManager
import android.content.Context
import android.util.Log
import androidx.core.net.toUri
import java.io.File

class Downloader(
    private val context: Context
): DownloaderInterface {

    private val downloadManager = context.getSystemService(DownloadManager::class.java)

    override fun downloadAsset(urls: List<String>): List<Long> {
        var downloads : MutableList<Long> = mutableListOf()
        for (i in urls.indices) {
            try {
                val file = File(context.getExternalFilesDir("GLBFile"), "model_${i}.glb")
                if (file.absoluteFile.exists()) {
                    if (file.absoluteFile.delete()) {
                        Log.d("Delete", "Delete - $i")
                    }
                }
            } catch (_: Exception){ }
            val request = DownloadManager.Request(urls[i].toUri())
                .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE or DownloadManager.Request.NETWORK_WIFI)
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                .setTitle("Downloading 3D Model - ${i + 1}")
                .setDescription("Please wait till the download is complete.")
                .setDestinationInExternalFilesDir(context, "GLBFile", "model_${i}.glb")

            downloads.add(downloadManager.enqueue(request))
        }

        return downloads.toList()
    }
}