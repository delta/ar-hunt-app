package edu.nitt.delta.orientation22.compose.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import edu.nitt.delta.orientation22.models.leaderboard.LeaderboardData
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.nitt.delta.orientation22.R
import edu.nitt.delta.orientation22.compose.CustomItem
import edu.nitt.delta.orientation22.compose.MarqueeText
import edu.nitt.delta.orientation22.compose.avatarList
import edu.nitt.delta.orientation22.models.Person
import edu.nitt.delta.orientation22.ui.theme.*
import androidx.compose.ui.draw.scale
import edu.nitt.delta.orientation22.models.auth.TeamModel


@Composable
fun LeaderBoardScreen(
    modifier: Modifier = Modifier,
    leaderBoardData: List<LeaderboardData>,
    currentTeamData: TeamModel,
    painter: Painter, contentDescription: String
){
    val fontFamily = FontFamily(
        Font(R.font.daysone_regular)
    )
    val configuration= LocalConfiguration.current
    val screenHeight=configuration.screenHeightDp
    val screenWidth=configuration.screenWidthDp

    val currentTeamIndex = leaderBoardData.indexOfFirst { it.teamName == currentTeamData.teamName }

    Box(modifier.fillMaxSize())
    {
        Image(painter = painter, contentDescription = contentDescription,modifier=Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
        Image(painter = painterResource(id = R.drawable.splash_skull), contentDescription = "Skull", modifier = Modifier.align(
            Alignment.Center).scale(2.5f))
        Column(modifier = Modifier
            .fillMaxSize()
            .background(
                color = background,

                ), horizontalAlignment = Alignment.CenterHorizontally) {


            CurrentTeamCard(
                teamName = currentTeamData.teamName,
                teamRank = currentTeamIndex + 1,
                points = currentTeamData.points,
                avatar = avatarList[currentTeamData.avatar]?: avatarList[1]!!,
                screenHeight = screenHeight,
                screenWidth = screenWidth
            )

            Text(
                text = "TOP PIRATES",
                modifier = Modifier.fillMaxWidth(),
                style = TextStyle(
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.daysone_regular)),
                    color = white,
                    textAlign = TextAlign.Center
                ),
            )

            Spacer(modifier=Modifier.height((screenHeight/45).dp))
            LazyColumn(contentPadding = PaddingValues(start = (screenWidth/20).dp, end =(screenWidth/20).dp, bottom = 4.dp ),
            verticalArrangement = Arrangement.spacedBy((screenHeight/100).dp),
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(bottom = (screenWidth / 3.5).dp)){
                itemsIndexed(items=leaderBoardData){index,person->
                    CustomItem(
                        person = Person(position = index+1, name = person.teamName, points = person.score, avatar = avatarList[leaderBoardData[index].avatar]?: avatarList[1]!!),
                        isCurrentTeam = index == currentTeamIndex
                        )
                }
            }
        }
    }
}

@Composable
fun CurrentTeamCard(
    teamName: String,
    teamRank: Int,
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
                .size((screenWidth / 4.5).dp)
                .clip(CircleShape)
        )

        Column(
            modifier = Modifier.fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.weight(0.5f)
            ) {
                MarqueeText(
                    text = teamName,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter),
                    style = TextStyle(
                        fontSize = 25.sp,
                        fontFamily = FontFamily(Font(R.font.daysone_regular)),
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
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(start = 20.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = "RANK: $teamRank",
                        modifier = Modifier.fillMaxWidth(),
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontFamily = FontFamily(Font(R.font.daysone_regular)),
                            color = white,
                        ),
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(end = 20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = points.toString(),
                        modifier = Modifier.padding(end = 15.dp),
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontFamily = FontFamily(Font(R.font.daysone_regular)),
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
