package edu.nitt.delta.orientation22.compose.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import edu.nitt.delta.orientation22.compose.MarqueeText
import edu.nitt.delta.orientation22.di.repository.PersonRepository
import edu.nitt.delta.orientation22.ui.theme.*


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
            .fillMaxSize()
            .background(
                color= background,

                ), horizontalAlignment = Alignment.CenterHorizontally) {
       Text(text = "LEADERBOARD", style = TextStyle(fontSize = 32.sp, fontFamily = fontFamily,fontWeight = FontWeight(400), color = colour_Leaderboard, shadow =
       Shadow(color = shadow2,offset= Offset(0f,12f),
           blurRadius = 3f
       )
       ),
       modifier=Modifier.padding(top=(screenHeight/20).dp, bottom = 20.dp))
            Spacer(modifier=Modifier.height((screenHeight/70).dp))
            Text(text = "AR HUNT",fontSize = 32.sp, fontFamily = fontFamily,fontWeight = FontWeight(400), color = colour_AR_Hunt)
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
                        Box(modifier = Modifier
                            .align(Alignment.Center)
                            .padding(top = (screenWidth / 10).dp)){
                            avatar(image_id = R.drawable.item1, color = Color.White , size_value =(screenWidth/4.5) )
                        }

                        Box(modifier= Modifier
                            .fillMaxSize()
                            .padding((screenWidth / 21).dp)
                            .fillMaxWidth(), contentAlignment = Alignment.BottomStart)
                        {
                    MarqueeText(text="Sarvesh", textAlign = TextAlign.Center, color= Color.White, fontWeight = FontWeight(400))

                }
                        Box(modifier= Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .height((screenWidth / 5).dp)
                            .padding(bottom = (screenWidth / 9).dp)){
                            Image(painter = painterResource(id = R.drawable.second_place), contentDescription ="Second place profile", modifier= Modifier
                                .fillMaxSize()
                                , contentScale = ContentScale.Fit)
                        }
                    }
                }
                Column(modifier= Modifier
                    .width((screenWidth / 3).dp)
                    .fillMaxHeight()) {
                    Box(modifier = Modifier.padding(bottom=(screenWidth/70).dp)) {
                        Box(modifier= Modifier
                            .fillMaxSize()
                            .padding(
                                top = (screenHeight / 18).dp,
                                start = (screenHeight / 120).dp,
                                end = (screenHeight / 120).dp
                            ), contentAlignment = Alignment.Center){
                            avatar(R.drawable.item1,color= lightYellow,(screenWidth/3.6))
                        }
                        Box(modifier=Modifier.fillMaxSize().padding(start=(screenWidth / 14).dp,end=(screenWidth / 14).dp), contentAlignment = Alignment.BottomStart)
                        {
                            MarqueeText(text="Vaikuntapuramalooo", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth(),color= Color.White,fontWeight = FontWeight(400))
                        }
                            Box(modifier= Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.46f)
                                .padding(
                                    top = (screenWidth / 15).dp,
                                    start = (screenWidth / 18).dp,
                                    end = (screenWidth / 18).dp
                                )){
                                Image(painter = painterResource(id = R.drawable.crown), contentDescription ="crown", modifier= Modifier
                                    .fillMaxSize()
                                    , contentScale = ContentScale.Fit)
                        }
                        Box(modifier= Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .height((screenWidth / 7).dp)
                            .padding(bottom = (screenWidth / 16).dp)){
                            Image(painter = painterResource(id = R.drawable.first_place), contentDescription ="First place profile", modifier= Modifier
                                .fillMaxSize()
                                , contentScale = ContentScale.Fit)
                        }

                    }

                }
                Column(modifier= Modifier
                    .width((screenWidth / 3).dp)
                    .fillMaxHeight()) {
                    Box(modifier=Modifier.padding(start = (screenWidth/90).dp,end=(screenWidth/10).dp)){
                        Box(modifier = Modifier
                            .align(Alignment.Center)
                            .padding(top = (screenWidth / 10).dp)){
                            avatar(image_id = R.drawable.item1, color = orange , size_value =(screenWidth/4.5) )
                        }

                        Box(modifier= Modifier
                            .fillMaxSize()
                            .padding((screenWidth / 21).dp)
                            .fillMaxWidth(), contentAlignment = Alignment.BottomStart)
                        {
                            MarqueeText(text="Sarvesh", textAlign = TextAlign.Center, color= Color.White, fontWeight = FontWeight(400))
                        }
                        Box(modifier= Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .height((screenWidth / 5).dp)
                            .padding(bottom = (screenWidth / 9).dp)){
                            Image(painter = painterResource(id = R.drawable.third_place), contentDescription ="Third place profile", modifier= Modifier
                                .fillMaxSize()
                                , contentScale = ContentScale.Fit)
                        }
                    }
                }
            }
            Spacer(modifier=Modifier.height((screenHeight/45).dp))
            LazyColumn(contentPadding = PaddingValues(start = (screenWidth/6).dp, end =(screenWidth/6).dp ),
            verticalArrangement = Arrangement.spacedBy((screenHeight/27).dp), modifier = Modifier.fillMaxHeight(0.73f)){
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

@Composable
fun avatar(image_id:Int, color: Color,size_value:Double

){
    Image(painter = painterResource(id = image_id), contentDescription ="Avatar with border", modifier= Modifier
        .size(size_value.dp)
        .clip(CircleShape)
        .border(4.dp, color, CircleShape)
        , contentScale = ContentScale.Fit)

}

/*
@Composable
fun first(a:Double)
{
    Box(modifier = Modifier
        .height(150.dp)
        .width(a.dp)
        )
    {
        Box(modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 10.dp)){avatar(image_id = R.drawable.item1, color = lightYellow, size_value = 100.0)}
        Box(modifier = Modifier.align(Alignment.TopCenter))
        {
            Image(painter = painterResource(id = R.drawable.crown), contentDescription = "", modifier = Modifier.fillMaxSize(0.39f))
        }
        Box(modifier = Modifier.align(Alignment.BottomCenter)){
            Image(painter = painterResource(id = R.drawable.first_place), contentDescription = "", modifier = Modifier.fillMaxSize(0.15f))
        }
    }
}*/
