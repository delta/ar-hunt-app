package edu.nitt.delta.orientation22.di.storage

import android.app.DownloadManager
import android.content.Context
import androidx.core.net.toUri

class Downloader(
    private val context: Context
): DownloaderInterface {

    private val downloadManager = context.getSystemService(DownloadManager::class.java)

    override fun downloadAsset(urls: List<String>): List<Long> {
        var downloads : MutableList<Long> = mutableListOf()
        for (i in urls.indices) {
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