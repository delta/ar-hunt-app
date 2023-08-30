package edu.nitt.delta.orientation22.di.viewModel.repository

import android.app.DownloadManager
import android.content.Context
import android.util.Log
import edu.nitt.delta.orientation22.di.api.ApiInterface
import edu.nitt.delta.orientation22.di.api.ResponseConstants
import edu.nitt.delta.orientation22.di.storage.Downloader
import edu.nitt.delta.orientation22.di.storage.SharedPrefHelper
import edu.nitt.delta.orientation22.models.IsRegisteredResponse
import edu.nitt.delta.orientation22.models.Result
import edu.nitt.delta.orientation22.models.auth.TokenRequestModel
import edu.nitt.delta.orientation22.models.auth.UserModel
import kotlinx.coroutines.delay
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val apiInterface: ApiInterface
) {
    @Inject
    lateinit var sharedPrefHelper: SharedPrefHelper
    suspend fun Login(code:String): Result<UserModel> =try {
        val response = apiInterface.login(code)
        Log.d("Login",response.user_token)
        if(response.user_token != "" )
        {
            sharedPrefHelper.token = response.user_token
            sharedPrefHelper.leaderName =response.name
            sharedPrefHelper.rollNo = response.email.substring(0,9).toInt()
            Result.build { UserModel(name = response.name, email = response.email) }
        } else {
            Log.d("Login", response.message.toString())
            Result.build { throw Exception(ResponseConstants.ERROR) }
        }
    }catch (e:Exception){
        Log.d("Login",e.message.toString())
        Result.build { throw Exception(ResponseConstants.ERROR) }
    }

    fun isLoggedInCheck():Result<Boolean> =try {
        val token = sharedPrefHelper.token
        Log.d("sharedLogin",token.toString())
        if(token == "")
            Result.build { false }
        else
            Result.build { true }

    }catch (e:Exception){
        Result.build { throw e }
    }

    suspend fun isRegistered():Result<IsRegisteredResponse> =try {
        val token = sharedPrefHelper.token
        val response = apiInterface.isRegistered(TokenRequestModel(token.toString()))
        Log.d("isRegistered",response.toString())
        if(response.message == ResponseConstants.IS_REGISTERED_SUCCESS){
            Log.d("isRegistered",response.toString())
            Result.build { response }
        } else{
            Log.d("isRegistered",response.message.toString())
            Result.build { response }
        }

    }catch (e:Exception){
        Log.d("isRegistered",e.message.toString())
        Result.build { throw Exception(ResponseConstants.ERROR) }
    }

    fun isLogOut(){
        sharedPrefHelper.clear()
    }

    suspend fun isLive() : Result<Boolean> = try {
        val token = sharedPrefHelper.token.toString()
        val response = apiInterface.isLive(TokenRequestModel(token))
        Log.d("isLive",response.live.toString())
        if (response.message == ResponseConstants.LIVE_SUCCESS1 || response.message == ResponseConstants.LIVE_SUCCESS2){
            Result.build { response.live }
        }
        else{
            Result.build { false }
        }
    }catch (e:Exception){
        Result.build { throw e }
    }

    suspend fun downloadAssets(urls: List<String>, context: Context): Result<Boolean> = try {
        if (sharedPrefHelper.isAssetReady) {
            Result.build { true }
        }

        Log.d("Download", urls.toString())
        val downloader = Downloader(context)
        val assetIDs = downloader.downloadAsset(urls)
        val downloadManager = context.getSystemService(DownloadManager::class.java)

        delay(1000)

        var count = 0
        var allDownloadsCompleted = false

        while (!allDownloadsCompleted && count < 4) {
            allDownloadsCompleted = true

            for (id in assetIDs) {
                val query = DownloadManager.Query().setFilterById(id)
                val cursor = downloadManager.query(query)

                if (cursor != null && cursor.moveToFirst()) {
                    val downloaded = cursor.getLong(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))
                    val total = cursor.getLong(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))

                    val percentage: Long = (downloaded * 100) / total
                    if (percentage != 100L) {
                        allDownloadsCompleted = false
                    }
                }
            }

            if (!allDownloadsCompleted) {
                count ++
                delay(1000)
            }
        }

        if (allDownloadsCompleted){
            sharedPrefHelper.isAssetReady = true
            Result.build { true }
        } else {
            Result.build { throw Exception("Download is incomplete, please try again.") }
        }
    } catch (e: Exception) {
        Result.build { throw e }
    }

    fun isDownloaded():Result<Boolean> = try {
        Result.build { sharedPrefHelper.isAssetReady }
    }catch (e:Exception){
        Result.build { throw e }
    }
}
