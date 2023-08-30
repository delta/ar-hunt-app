package edu.nitt.delta.orientation22.di.storage

interface DownloaderInterface {
    fun downloadAsset(urls: List<String>): List<Long>
}