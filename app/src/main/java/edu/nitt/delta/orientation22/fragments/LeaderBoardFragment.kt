package edu.nitt.delta.orientation22.fragments

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import edu.nitt.delta.orientation22.compose.screens.LeaderBoardScreen

@Composable
fun LeaderBoardFragment(
){
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ){
        LeaderBoardScreen()
    }
}
