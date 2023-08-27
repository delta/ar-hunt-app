package edu.nitt.delta.orientation22.fragments

import android.graphics.fonts.Font
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.nitt.delta.orientation22.R
import edu.nitt.delta.orientation22.compose.CustomItem
import edu.nitt.delta.orientation22.compose.screens.LeaderBoardScreen
import edu.nitt.delta.orientation22.di.api.ApiInterface
import edu.nitt.delta.orientation22.di.viewModel.actions.LeaderBoardAction
import edu.nitt.delta.orientation22.di.viewModel.actions.TeamAction
import edu.nitt.delta.orientation22.di.viewModel.repository.LeaderBoardRepository
import edu.nitt.delta.orientation22.di.viewModel.uiState.LeaderBoardStateViewModel
import edu.nitt.delta.orientation22.models.Person
import edu.nitt.delta.orientation22.ui.theme.white

@Composable
fun LeaderBoardFragment(
    leaderBoardViewModel : LeaderBoardStateViewModel
){
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ){
        leaderBoardViewModel.doAction(LeaderBoardAction.GetLeaderBoard)
        val leaderBoardData = leaderBoardViewModel.leaderBoardData.value
        LeaderBoardScreen(leaderBoardData = leaderBoardData,
            painter = painterResource(id = R.drawable.background_image),
            contentDescription = "LeaderBoard")
    }
}


//@RequiresApi(Build.VERSION_CODES.Q)
//@Composable
//fun SplashContent(
//    scale: Float,
//    progress: Float
//) {
//    Column(
//
//        modifier = Modifier
//            .background(Color.Black)
//            .fillMaxSize(),
//        verticalArrangement = Arrangement.SpaceAround,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Row(
//                horizontalArrangement = Arrangement.Center,
//                verticalAlignment = Alignment.CenterVertically,
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Text(
//                    text = "AR",
//                    style = TextStyle(
//                        fontSize = 45.sp,
//                        color = white,
//
//                        ),
//                )
//                Image(
//                    painter = painterResource(id = R.drawable.cat),
//                    contentDescription = "AR Hunt Logo",
//                    modifier = Modifier
//                        .size(70.dp, 60.dp)
//                        .padding(10.dp, 0.dp, 0.dp, 0.dp)
//                )
//            }
//
//        }
//    }
//}
//
//@RequiresApi(Build.VERSION_CODES.Q)
//@Preview
//@Composable
//fun SpalshContentPreview() {
//    SplashContent(scale = 20f, progress = 20f)
//}




