package edu.nitt.delta.orientation22.compose.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import edu.nitt.delta.orientation22.ui.theme.Orientation22androidTheme

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier
){
    Text(text = "In LoginScreen",modifier.fillMaxSize())
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    Orientation22androidTheme{
        LoginScreen()
    }
}
