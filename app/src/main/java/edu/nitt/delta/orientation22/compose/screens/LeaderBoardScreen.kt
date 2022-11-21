package edu.nitt.delta.orientation22.compose.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import edu.nitt.delta.orientation22.models.leaderboard.LeaderboardData
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
import edu.nitt.delta.orientation22.models.Person
import edu.nitt.delta.orientation22.ui.theme.*


@Composable
fun LeaderBoardScreen(
    modifier: Modifier = Modifier,
    leaderBoardData: List<LeaderboardData>,
    painter: Painter,contentDescription: String
){
    val fontFamily= FontFamily(
        Font(R.font.montserrat_regular)
    )
    val configuration= LocalConfiguration.current
    val screenHeight=configuration.screenHeightDp
    val screenWidth=configuration.screenWidthDp
    Box(modifier.fillMaxSize())
    {
        Image(painter = painter, contentDescription = contentDescription,modifier=Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
        Column(modifier = Modifier
            .fillMaxSize()
            .background(
                color = background,

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
            Spacer(modifier=Modifier.height((screenHeight/70).dp))

            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(top = (screenHeight/15).dp)
                ) {
                    AvatarSmall(R.drawable.item2, R.drawable.second_place, color = white)
                    Box(
                        modifier = Modifier.fillMaxWidth(0.25f)
                    ) {
                        MarqueeText(text=leaderBoardData[0].teamName, textAlign = TextAlign.Center, color= Color.White, fontWeight = FontWeight(400))
                    }
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    AvatarBig(R.drawable.item1)
                    Box (
                        modifier = Modifier.fillMaxWidth(0.3f)
                    ) {
                        MarqueeText(
                            text = leaderBoardData[0].teamName,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth(),
                            color = Color.White,
                            fontWeight = FontWeight(400)
                        )
                    }
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(top = (screenHeight/15).dp)
                ) {
                    AvatarSmall(R.drawable.item1, R.drawable.third_place, color = orange)
                    Box (
                        modifier = Modifier.fillMaxWidth(0.25f)
                    ) {
                        MarqueeText(
                            text = leaderBoardData[0].teamName,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth(),
                            color = Color.White,
                            fontWeight = FontWeight(400)
                        )
                    }
                }
            }
            Spacer(modifier=Modifier.height((screenHeight/45).dp))
            LazyColumn(contentPadding = PaddingValues(start = (screenWidth/10).dp, end =(screenWidth/10).dp, bottom = 4.dp ),
            verticalArrangement = Arrangement.spacedBy((screenHeight/27).dp), modifier = Modifier.fillMaxHeight()){
                itemsIndexed(items=leaderBoardData){index,person->
                    CustomItem(person = Person(position = index+1, name = person.teamName, points = person.score, avatar = person.avatar))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LeaderBoardScreenPreview()
{
   // LeaderBoardScreen(Modifier,painterResource(id = R.drawable.background_image),"LeaderBoard")
}

@Composable
fun AvatarSmall (
    avatar_id: Int,
    position_id: Int,
    color: Color,
){
    val configuration= LocalConfiguration.current
    val screenWidth=configuration.screenWidthDp

    Box(
        modifier = Modifier.size(height = (screenWidth/3.8).dp, width = ((screenWidth/4.5)).dp)
    ){
        Box(
            modifier = Modifier.padding(bottom = (screenWidth/20).dp)
        ) {
            AvatarImage(
                image_id = avatar_id,
                color = color,
                size_value = (screenWidth / 4.5)
            )
        }
        Image(painter = painterResource(id = position_id), contentDescription ="Second place profile", modifier= Modifier
            .size((screenWidth / 10).dp)
            .align(Alignment.BottomCenter)
            , contentScale = ContentScale.Fit,)
    }
}

@Composable
fun AvatarBig (
    avatar_id: Int,
){
    val configuration= LocalConfiguration.current
    val screenWidth=configuration.screenWidthDp

    Box(
        modifier = Modifier.size(height = (screenWidth/2.1).dp, width = ((screenWidth/3.5)).dp)
    ){
        Box(
            modifier = Modifier.padding(bottom = (screenWidth/20).dp, top = (screenWidth/6.5).dp)
        ) {
            AvatarImage(
                image_id = avatar_id,
                color = lightYellow,
                size_value = (screenWidth / 3.5)
            )
        }
        Image(painter = painterResource(id = R.drawable.crown), contentDescription ="crown", modifier= Modifier
            .size((screenWidth / 5).dp)
            .align(Alignment.TopCenter)
            , contentScale = ContentScale.Fit)
        Image(painter = painterResource(id = R.drawable.first_place), contentDescription ="Second place profile", modifier= Modifier
            .size((screenWidth / 10).dp)
            .align(Alignment.BottomCenter)
            , contentScale = ContentScale.Fit,)
    }
}

@Composable
fun AvatarImage(image_id:Int, color: Color,size_value:Double,
){
    Image(painter = painterResource(id = image_id), contentDescription ="Avatar with border", modifier= Modifier
        .size(size_value.dp)
        .clip(CircleShape)
        .border(4.dp, color, CircleShape)
        , contentScale = ContentScale.Fit)
}
