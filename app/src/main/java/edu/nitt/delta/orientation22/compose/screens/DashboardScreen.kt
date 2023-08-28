package edu.nitt.delta.orientation22.compose.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.nitt.delta.orientation22.R
import edu.nitt.delta.orientation22.compose.MarqueeText
import edu.nitt.delta.orientation22.compose.avatarList
import edu.nitt.delta.orientation22.models.auth.TeamModel
import edu.nitt.delta.orientation22.ui.theme.*

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
   team: TeamModel,
    logout:() ->Unit
) {
    Orientation22androidTheme() {
        val painter = painterResource(id = R.drawable.background)
        val avatar = avatarList[team.avatar]

        val screenHeight = LocalConfiguration.current.screenHeightDp
        val screenWidth = LocalConfiguration.current.screenWidthDp

        Box(modifier = modifier.fillMaxSize()) {
            Image(
                painter = painter,
                contentDescription = "background",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .background(
                        translucentBackground,
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,

                        ) {
                        Box(
                            modifier = Modifier.fillMaxWidth()
                        ){
                            Column(
                                modifier=Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.splash_skull),
                                    contentDescription = "background",
                                    modifier = Modifier.fillMaxWidth(0.5f),
                                    contentScale = ContentScale.Crop
                                )
                            }

                        }

                TeamCard (
                    teamName = team.teamName,
                    points = team.points,
                    avatar = avatar!!,
                    screenHeight = screenHeight,
                    screenWidth = screenWidth
                )
                
                Text(
                    text = "CREW",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(all = 10.dp),
                    color = white,
                    fontFamily = FontFamily(Font(R.font.fiddlerscovecondital)),
                )

                TeamDetails(screenHeight, screenWidth, team = team)
            }
                    }
                }
            }

@Composable
fun InitialsCircle(initials: String, modifier: Modifier = Modifier, screenWidth: Int, screenHeight: Int, isLeader: Boolean) {
    Box(
        modifier = Modifier.clip(CircleShape)
            .size((screenWidth / 8).dp)
            .background(if (isLeader) lightCyan else red).padding()
    ) {
        Text(
            text = initials.uppercase(),
            color = black,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.Center),
            fontFamily = FontFamily(Font(R.font.daysone_regular)),
        )
    }
}

@Composable
fun TeamDetails(
    screenHeight: Int,
    screenWidth: Int,
    team: TeamModel
){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        for (i in 0 until team.members.size){
            val initials = team.members[i].name.split(" ").mapNotNull { it.firstOrNull() }.take(2).joinToString("")
            Row(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .background(
                        color = black,
                        shape = RoundedCornerShape(30)
                    ).fillMaxWidth(0.9f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Spacer(modifier = Modifier.width((screenWidth/20).dp))
                InitialsCircle(
                    initials = initials.takeIf { it.isNotEmpty() } ?: team.members[i].name.take(1),
                    screenWidth = screenWidth,
                    screenHeight = screenHeight,
                    isLeader = i == 0
                )

                Column(
                    modifier = Modifier.fillMaxHeight().padding(5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    MarqueeText(
                        text = team.members[i].name,
                        modifier = Modifier.fillMaxWidth(0.8f),
                        style = TextStyle(
                            fontSize = 25.sp,
                            fontFamily = FontFamily(Font(R.font.fiddlerscove)),
                            color = white,
                            textAlign = TextAlign.Center,
                        ),
                    )
                    Text(
                        text = team.members[i].rollNo.toString(),
                        modifier = Modifier.fillMaxWidth(0.8f),
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontFamily = FontFamily(Font(R.font.fiddlerscove)),
                            color = Color.Gray,
                            textAlign = TextAlign.Center,
                        ),
                    )
                }
                Spacer(modifier = Modifier.width((screenWidth/4).dp))
            }
            Spacer(modifier = Modifier.height((screenHeight/45).dp))
        }
    }
}

@Composable
fun TeamCard(
    teamName: String,
    points: Int,
    avatar: Int,
    screenHeight: Int,
    screenWidth: Int,
) {
    Row(
        modifier = Modifier
            .height((screenHeight / (5)).dp)
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = avatar),
            contentDescription = "Current Team Avatar",
            modifier = Modifier
                .size((screenWidth / 3.5).dp)
                .clip(CircleShape)
        )

        Column(
            modifier = Modifier.fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height((screenHeight / 40).dp))

            Box(
                modifier = Modifier.weight(0.5f)
            ) {
                MarqueeText(
                    text = teamName,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter),
                    style = TextStyle(
                        fontSize = 45.sp,
                        fontFamily = FontFamily(Font(R.font.fiddlerscove)),
                        color = white,
                        textAlign = TextAlign.Center,
                    ),
                )
            }

            Spacer(modifier = Modifier.height((screenHeight / 40).dp))

            Row(
                modifier = Modifier
                    .weight(0.5f)
                    .fillMaxWidth(),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(end = 20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = points.toString(),
                        modifier = Modifier.padding(end = 20.dp),
                        style = TextStyle(
                            fontSize = 25.sp,
                            fontFamily = FontFamily(Font(R.font.fiddlerscove)),
                            color = white,
                        ),
                    )
                    Icon(
                        modifier = Modifier.scale(3f),
                        painter = painterResource(id = R.drawable.coins),
                        contentDescription = "Coins Icon",
                        tint = Color.Unspecified
                    )
                }
            }
        }
    }
}
