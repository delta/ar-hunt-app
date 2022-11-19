package edu.nitt.delta.orientation22.compose

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import edu.nitt.delta.orientation22.R
import edu.nitt.delta.orientation22.ui.theme.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun Context.toast(message: CharSequence)
{
Toast.makeText(this,message,Toast.LENGTH_LONG).show()
}

fun Context.getAnnotatedString(color: Color) : AnnotatedString {
    val annotatedString = buildAnnotatedString {
        val str = "MADE WITH ❤ BY DELTA FORCE AND ORIENTATION"
        val indexStartDelta = str.indexOf("DELTA FORCE")
        val indexStartOrientation = str.indexOf("ORIENTATION")
        append(str)
        addStyle(
            style = SpanStyle(
                color = color,
                fontFamily = FontFamily(Font(R.font.montserrat_regular)),
                fontWeight = FontWeight.Bold
            ),
            start = 0,
            end = 42
        )
        addStyle(
            style = SpanStyle(
                color = color,
                fontFamily = FontFamily(Font(R.font.montserrat_regular)),
                textDecoration = TextDecoration.Underline,
                fontWeight = FontWeight.Bold
            ),
            start = indexStartDelta,
            end = indexStartDelta+11
        )
        addStyle(
            style = SpanStyle(
                color = color,
                fontFamily = FontFamily(Font(R.font.montserrat_regular)),
                textDecoration = TextDecoration.Underline,
                fontWeight = FontWeight.Bold
            ),
            start = indexStartOrientation,
            end = indexStartOrientation + 11
        )
        addStringAnnotation(
            tag = "URL",
            annotation = "https://delta.nitt.edu/",
            start = indexStartDelta,
            end = indexStartDelta + 11
        )
        addStringAnnotation(
            tag = "URL",
            annotation = "https://www.instagram.com/nitt.orientation/",
            start = indexStartOrientation,
            end = indexStartOrientation + 11
        )
    }
    return annotatedString
}

@Composable
fun Context.SnackShowError(errorMessage : String, modifier: Modifier) {
    val snackHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    SnackbarHost(snackHostState, modifier = modifier){ data ->
        Snackbar(
            snackbarData = data,
            containerColor = black,
            contentColor = white,
        )
    }
    fun showSnack() {
        scope.launch {
            snackHostState.showSnackbar(errorMessage, duration = SnackbarDuration.Short)
        }
    }
    showSnack()
}

@Composable
fun Context.SnackShowSuccess(errorMessage : String, modifier: Modifier) {
    val snackHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    SnackbarHost(snackHostState, modifier = modifier){ data ->
        Snackbar(
            snackbarData = data,
            containerColor = Color.Red,
            contentColor = black,
        )
    }
    fun showSnack() {
        scope.launch {
            snackHostState.showSnackbar(errorMessage, duration = SnackbarDuration.Short)
        }
    }
    showSnack()
}

@Composable
fun LoadingIcon() {
    CircularProgressIndicator(color = yellow)
}
