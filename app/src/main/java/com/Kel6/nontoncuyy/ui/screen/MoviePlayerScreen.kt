package com.Kel6.nontoncuyy.ui.screen

import android.content.Intent
import android.net.Uri
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

@OptIn(UnstableApi::class)
@Composable
fun MoviePlayerScreen(urlTrailer: String) {
    val context = LocalContext.current
    val videoId = remember(urlTrailer) { extractYouTubeVideoId(urlTrailer) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        when {
            videoId != null -> {
                // By using m.youtube.com/watch instead of /embed, we "act" like a real browser
                // This bypasses the 152-4 restriction which usually targets embedded players
                Box(modifier = Modifier.fillMaxSize()) {
                    YouTubeWebView(urlTrailer)
                    
                    // Reliable fallback: Open in the actual YouTube App
                    FloatingActionButton(
                        onClick = {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$videoId"))
                            try {
                                context.startActivity(intent)
                            } catch (e: Exception) {
                                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(urlTrailer)))
                            }
                        },
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(24.dp),
                        containerColor = Color.Red,
                        contentColor = Color.White
                    ) {
                        Icon(Icons.Default.OpenInNew, contentDescription = "Open in YouTube App")
                    }
                }
            }
            urlTrailer.endsWith(".mp4") || urlTrailer.contains("cdn") || urlTrailer.contains("discordapp") || urlTrailer.contains(".m3u8") -> {
                val exoPlayer = remember {
                    ExoPlayer.Builder(context).build().apply {
                        setMediaItem(MediaItem.fromUri(urlTrailer))
                        prepare()
                        playWhenReady = true
                    }
                }

                AndroidView(
                    factory = {
                        PlayerView(context).apply {
                            player = exoPlayer
                            layoutParams = FrameLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT
                            )
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                )

                DisposableEffect(Unit) {
                    onDispose {
                        exoPlayer.release()
                    }
                }
            }
            urlTrailer.isNotEmpty() -> {
                YouTubeWebView(urlTrailer)
            }
            else -> {
                Text("Trailer not available", color = Color.White)
            }
        }
    }
}

@Composable
fun YouTubeWebView(url: String) {
    val videoId = extractYouTubeVideoId(url)
    // Using the mobile watch page is the most "authentic" way to play on YouTube without leaving the app
    val watchUrl = if (videoId != null) "https://m.youtube.com/watch?v=$videoId" else url

    AndroidView(
        factory = { context ->
            WebView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                
                setBackgroundColor(0xFF000814.toInt())
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                settings.mediaPlaybackRequiresUserGesture = false
                settings.loadWithOverviewMode = true
                settings.useWideViewPort = true
                
                // Real mobile Chrome user agent to trick YouTube into thinking we are a browser
                settings.userAgentString = "Mozilla/5.0 (Linux; Android 13; Pixel 7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Mobile Safari/537.36"
                
                webViewClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                        // Keep internal navigation (like clicking 'play') inside the WebView
                        return false
                    }
                }
                
                webChromeClient = WebChromeClient()
                loadUrl(watchUrl)
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}

fun extractYouTubeVideoId(url: String): String? {
    return when {
        url.contains("v=") -> url.split("v=")[1].split("&")[0]
        url.contains("youtu.be/") -> {
            val parts = url.split("youtu.be/")[1].split("?")[0]
            parts.split("/").last()
        }
        url.contains("embed/") -> url.split("embed/")[1].split("?")[0]
        else -> null
    }
}
