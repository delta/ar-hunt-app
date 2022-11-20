package edu.nitt.delta.orientation22.compose.screens

import android.net.Uri
import android.util.Log
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import edu.nitt.delta.orientation22.models.ClientCredentials

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DAuthWebView() {
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
                        webViewClient = WebViewClient()
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
