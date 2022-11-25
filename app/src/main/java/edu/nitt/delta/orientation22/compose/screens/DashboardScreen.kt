package edu.nitt.delta.orientation22.compose.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import edu.nitt.delta.orientation22.R
import edu.nitt.delta.orientation22.compose.MarqueeText
import edu.nitt.delta.orientation22.compose.avatarList
import edu.nitt.delta.orientation22.models.auth.TeamModel
import edu.nitt.delta.orientation22.ui.theme.*

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    team: TeamModel,
    logout:() ->Unit
) {
    Orientation22androidTheme() {
        val painter = painterResource(id = R.drawable.background_image)
        val uriHandler = LocalUriHandler.current
        val screenHeight = LocalConfiguration.current.screenHeightDp
        val screenWidth = LocalConfiguration.current.screenWidthDp
        val avatar = avatarList[team.avatar]
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
                            Text(
                                text = "AR HUNT",
                                fontSize = 45.sp,
                                letterSpacing = 0.15.em,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(top = 30.dp).align(alignment = Alignment.Center),
                                color = brightYellow,
                                fontFamily = FontFamily(Font(R.font.montserrat_regular))
                            )
//                            IconButton(onClick = {
//                                logout()
//                            }, modifier = Modifier.padding(top = 30.dp, end = 10.dp).align(Alignment.CenterEnd)) {
//                                Icon(
//                                    Icons.Sharp.ExitToApp, "logout", modifier = Modifier.size(30.dp), tint = peach
//                                )
//                            }
                        }
                        
                        Spacer(modifier = Modifier.height(5.dp))
                        MarqueeText(
                            text = team.teamName,
                            fontSize = 30.sp,
                            letterSpacing = 0.08.em,
                            fontWeight = FontWeight.Light,
                            modifier = Modifier.padding(all = 15.dp),
                            color = brightYellow,
                            fontFamily = FontFamily(Font(R.font.montserrat_regular),),
                            textAlign = TextAlign.Center,
                        )
                        Box(
                            modifier = Modifier.fillMaxWidth(0.3f)
                        ) {

                            Image(
                                painter = painterResource(id = avatar!!),
                                contentDescription = "",
                                modifier = Modifier
                                    .clip(
                                        RoundedCornerShape(100)
                                    )
                                    .padding(all = 15.dp)
                                    .size(80.dp)
                                    .align(Alignment.Center)
                            )
                        }
                    Text(
                        text = "Total Points = ${team.points}",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(all = 3.dp),
                        color = peach,
                        fontFamily = FontFamily(Font(R.font.montserrat_regular))
                    )
                        Spacer(modifier = Modifier.height(10.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.85f)
                                .height((0.5).dp)
                                .background(
                                    color = white,
                                )
                        )
                        Spacer(modifier = Modifier.height(30.dp))
                        Card(1, team.members[0].name, avatar!!)
                        Card(2, team.members[1].name, avatar)
                        Card(3, team.members[2].name, avatar)
                        Card(4, team.members[3].name, avatar)
                        Spacer(modifier = Modifier.height((screenWidth/3.2).dp))
                    }
                }
            }
}

@Composable
fun Card(index: Int, name: String, avatar: Int) {
    Row(
        modifier = Modifier
            .padding(top = 15.dp)
            .fillMaxWidth(0.85f)
            .background(
                translucentBox,
                RoundedCornerShape(35, 35, 35, 35)
            ),
            verticalAlignment = Alignment.CenterVertically,
        )
    {
        Text(
            text = index.toString(),
            fontSize = 15.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier
                .padding(all = 20.dp)
                .padding(start = 20.dp)
                .fillMaxWidth(0.05f),
            color = white,
            fontFamily = FontFamily(Font(R.font.montserrat_regular))
        )
        Image(painter = painterResource(id = avatar), contentDescription = "avatar", modifier = Modifier
            .clip(
                RoundedCornerShape(100)
            )
            .padding(all = 15.dp)
            .size(40.dp))
        MarqueeText(
            text = name,
            fontSize = 15.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier
                .padding(all = 15.dp)
                .padding(start = 10.dp),
            color = white,
            fontFamily = FontFamily(Font(R.font.montserrat_regular))
        )
    }
}
