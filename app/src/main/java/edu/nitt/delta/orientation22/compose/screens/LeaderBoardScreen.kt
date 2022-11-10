package edu.nitt.delta.orientation22.compose.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.nitt.delta.orientation22.R
import edu.nitt.delta.orientation22.compose.CustomItem
import edu.nitt.delta.orientation22.di.repository.PersonRepository

@Composable
fun LeaderBoardScreen(
    modifier: Modifier = Modifier,painter: Painter,contentDescription: String
){
    val fontFamily= FontFamily(
        Font(R.font.montserrat_regular)
    )
    val configuration= LocalConfiguration.current
    val screenHeight=configuration.screenHeightDp
    val screenWidth=configuration.screenWidthDp
    val personRepository=PersonRepository()
    val getalldata=personRepository.getalldata()
    Box(modifier.fillMaxSize())
    {
        Image(painter = painter, contentDescription = contentDescription,modifier=Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
        Column(modifier = Modifier
            .padding(top = (screenHeight / 16).dp)
            .fillMaxSize()
            .background(
                Color.hsl(0f, 0f, 0f, 0.25f),
                RoundedCornerShape(8, 8)
            ), horizontalAlignment = Alignment.CenterHorizontally) {
       Text(text = "LEADERBOARD", style = TextStyle(fontSize = 32.sp, fontFamily = fontFamily,fontWeight = FontWeight(400), color = Color.hsl(43f,0.62f,0.54f,1f), shadow =
       Shadow(color = Color.hsl(54f,1f,0.5f,0.12f),offset= Offset(0f,12f),
           blurRadius = 3f
       )
       ),
       modifier=Modifier.padding(top=(screenHeight/20).dp, bottom = 20.dp))
            Spacer(modifier=Modifier.height((screenHeight/70).dp))
            Text(text = "AR HUNT",fontSize = 32.sp, fontFamily = fontFamily,fontWeight = FontWeight(400), color = Color.hsl(47f,1f,0.61f,1f))
            Spacer(modifier=Modifier.height((screenHeight/80).dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .height((0.5).dp)
                    .background(
                        color = Color.White
                    )
            )
            Row(modifier= Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.35f)){
                Column(modifier= Modifier
                    .width((screenWidth / 3).dp)
                    .fillMaxHeight()) {
                    Box(modifier=Modifier.padding(start = (screenWidth/10).dp,end=(screenWidth/90).dp)){
                Image(painter = painterResource(id = R.drawable.profile_2), contentDescription ="Second place profile", modifier= Modifier
                    .fillMaxSize()
                    .padding(top = (screenHeight / 20).dp), contentScale = ContentScale.Fit)
                        Box(modifier= Modifier
                            .fillMaxSize()
                            .padding((screenWidth / 40).dp), contentAlignment = Alignment.BottomStart)
                        {
                    Text("Sarvesh", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth(),color= Color.White, fontWeight = FontWeight(400))
                }
                    }
                }
                Column(modifier= Modifier
                    .width((screenWidth / 3).dp)
                    .fillMaxHeight()) {
                    Box(){
                        Image(painter = painterResource(id = R.drawable.profile_1), contentDescription ="Second place profile", modifier= Modifier
                            .fillMaxSize()
                            .padding(top = (screenWidth / 60).dp, bottom = (screenWidth / 20).dp), contentScale = ContentScale.Fit)
                        Box(modifier=Modifier.fillMaxSize(), contentAlignment = Alignment.BottomStart)
                        {
                            Text("Sarvesh", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth(),color= Color.White,fontWeight = FontWeight(400))
                        }
                    }

                }
                Column(modifier= Modifier
                    .width((screenWidth / 3).dp)
                    .fillMaxHeight()) {
                    Box(modifier=Modifier.padding(start = (screenWidth/70).dp,end=(screenWidth/10).dp)){
                        Image(painter = painterResource(id = R.drawable.profile_3), contentDescription ="Second place profile", modifier= Modifier
                            .fillMaxSize()
                            .padding(top = (screenHeight / 20).dp), contentScale = ContentScale.Fit)
                        Box(modifier= Modifier
                            .fillMaxSize()
                            .padding((screenWidth / 40).dp), contentAlignment = Alignment.BottomStart)
                        {
                            Text("Sarvesh", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth(),color= Color.White,fontWeight = FontWeight(400))
                        }
                    }
                }
            }
            Spacer(modifier=Modifier.height((screenHeight/27).dp))
            LazyColumn(contentPadding = PaddingValues(start = (screenWidth/6).dp, end =(screenWidth/6).dp ),
            verticalArrangement = Arrangement.spacedBy((screenHeight/27).dp), modifier = Modifier.fillMaxHeight(0.79f)){
                items(items=getalldata){person->
                    CustomItem(person = person)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LeaderBoardScreenPreview()
{

    LeaderBoardScreen(Modifier,painterResource(id = R.drawable.background_image),"LeaderBoard")
}