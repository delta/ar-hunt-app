package edu.nitt.delta.orientation22.compose.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
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
        val painter = painterResource(id = R.drawable.background)
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
                            Column(
                                modifier=Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.skull_reg),
                                    contentDescription = "background",
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(bottom = 40.dp, start = 40.dp, end = 40.dp),
                                    contentScale = ContentScale.Crop
                                )
                                Row(){
                                    Box(
                                        modifier = Modifier.fillMaxWidth(0.3f),
                                    ) {

                                        Image(
                                            painter = painterResource(id = avatar!!),
                                            contentDescription = "",
                                            modifier = Modifier
                                                .clip(
                                                    CircleShape
                                                )
                                                .padding(all = 15.dp)
                                                .size(80.dp)
                                                .align(Alignment.Center)

                                        )
                                    }
                                    Column(
                                        horizontalAlignment = Alignment.Start
                                    ) {
                                        Text(
                                            text = team.members[0].name,
                                            fontSize = 45.sp,
                                            letterSpacing = 0.15.em,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier
                                                .padding(top = 10.dp),
                                            color = white,
                                            fontFamily = FontFamily(Font(R.font.fiddlerscovecondital)),
                                        )
                                        MarqueeText(
                                            text = team.teamName,
                                            fontSize = 30.sp,
                                            letterSpacing = 0.08.em,
                                            fontWeight = FontWeight.Light,
                                            color = white,
                                            fontFamily = FontFamily(Font(R.font.fiddlerscovecondital),),
                                        )
                                    }


                                }
                            }

                        }

                Spacer(modifier = Modifier.padding(10.dp))


                    Column(
                        modifier=Modifier.fillMaxSize().padding(start=20.dp),
                        horizontalAlignment = Alignment.Start
                    ){
                        Text(
                            text = "CREW",
                            fontSize = 40.sp,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier.padding(all = 10.dp),
                            color = white,
                            fontFamily = FontFamily(Font(R.font.fiddlerscovecondital))
                        )
                        Text(
                            text = team.members[0].name,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier.padding(all = 10.dp),
                            color = white,
                            fontFamily = FontFamily(Font(R.font.fiddlerscovecondital))
                        )
                        Text(
                            text = team.members[1].name,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier.padding(all = 10.dp),
                            color = white,
                            fontFamily = FontFamily(Font(R.font.fiddlerscovecondital))
                        )
                        Text(
                            text = team.members[2].name,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier.padding(all = 10.dp),
                            color = white,
                            fontFamily = FontFamily(Font(R.font.fiddlerscovecondital))
                        )
                        Text(
                            text = team.members[3].name,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier.padding(all = 10.dp),
                            color = white,
                            fontFamily = FontFamily(Font(R.font.fiddlerscovecondital))
                        )
                    }
                   }
                    }
                }
            }
