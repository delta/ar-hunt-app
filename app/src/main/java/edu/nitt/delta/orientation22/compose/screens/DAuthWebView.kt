package edu.nitt.delta.orientation22.compose.screens

import android.net.Uri
import android.util.Log
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import edu.nitt.delta.orientation22.di.api.ApiRoutes
import edu.nitt.delta.orientation22.models.ClientCredentials

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DAuthWebView(
    onSuccess:(code:String)->Unit
) {
    Scaffold(
        content = {paddingValues ->
            val url = Uri.Builder().apply {
                scheme("https")
                authority("auth.delta.nitt.edu")
                appendPath("authorize")
                appendQueryParameter("client_id", ClientCredentials.ClientID.param)
                appendQueryParameter("redirect_uri", ClientCredentials.RedirectUri.param)
                appendQueryParameter("response_type", "code")
                appendQueryParameter("grant_type", "authorization_code")
                appendQueryParameter("state", ClientCredentials.State.param)
                appendQueryParameter("scope", "email+openid+profile+user")
                appendQueryParameter("nonce", ClientCredentials.Nonce.param)
            }.toString()
            Log.v("url", url)
            AndroidView(
                modifier = Modifier.padding(paddingValues),
                factory = {
                    val apply = WebView(it).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        webViewClient = object: WebViewClient() {
                            override fun shouldOverrideUrlLoading(
                                view: WebView?,
                                request: WebResourceRequest?
                            ): Boolean {
                                Log.d("Login",request?.url.toString())
                                if(request?.url.toString().contains(ApiRoutes.BASE_URL)){
                                    Log.d("Login","In web review")
                                    onSuccess(request?.url?.getQueryParameter("code").toString())
                                    return true
                                }
                                return super.shouldOverrideUrlLoading(view, request)
                            }
                        }
                        settings.javaScriptEnabled = true
                        settings.domStorageEnabled = true
                        loadUrl(url)
                    }
                    apply
                }, update = {
                    it.loadUrl(url)
                }, )
        }
    )
}
