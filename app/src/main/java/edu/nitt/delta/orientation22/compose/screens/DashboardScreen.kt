package edu.nitt.delta.orientation22.compose.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import edu.nitt.delta.orientation22.R
import edu.nitt.delta.orientation22.compose.MarqueeText
import edu.nitt.delta.orientation22.compose.avatarList
import edu.nitt.delta.orientation22.constants.GameDescription
import edu.nitt.delta.orientation22.models.auth.TeamModel
import edu.nitt.delta.orientation22.ui.theme.*
import kotlin.text.Typography.bullet

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
   team: TeamModel,
    logout:() ->Unit
) {
    Orientation22androidTheme() {
        val painter = painterResource(id = R.drawable.background)
        val avatar = avatarList[team.avatar] ?: avatarList[1]!!

        val screenHeight = LocalConfiguration.current.screenHeightDp
        val screenWidth = LocalConfiguration.current.screenWidthDp

        val showDialog = remember { mutableStateOf(false) }

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
                    avatar = avatar,
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

            FilledIconButton(onClick = {
                showDialog.value = true
            },
                modifier= Modifier.size(50.dp).offset(20.dp, 20.dp),
                shape = CircleShape,
                colors = IconButtonDefaults.iconButtonColors(containerColor = red)
            ) {
                Icon(
                    Icons.Default.Info,
                    modifier = Modifier.scale(2f),
                    contentDescription = "Info Icon",
                    tint = black
                )
            }

            if (showDialog.value){
                GameInfo (
                    showDialog = showDialog.value,
                    onDismiss = { showDialog.value = false }
                )
            }
                    }
                }
            }

@Composable
fun InitialsCircle(initials: String, modifier: Modifier = Modifier, screenWidth: Int, screenHeight: Int, isLeader: Boolean) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .size((screenWidth / 8).dp)
            .background(if (isLeader) lightCyan else red)
            .padding()
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
                    )
                    .fillMaxWidth(0.9f),
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
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(5.dp),
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun GameInfo(showDialog: Boolean, onDismiss: () -> Unit) {
    val screenHeight = LocalConfiguration.current.screenHeightDp
    val paragraphStyle = ParagraphStyle(textIndent = TextIndent(restLine = 1.sp), textAlign = TextAlign.Justify)

    if (showDialog) {
        Dialog(onDismissRequest = onDismiss, properties = DialogProperties(usePlatformDefaultWidth = false)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(5))
                    .paint(
                        painter = painterResource(id = R.drawable.background),
                        contentScale = ContentScale.FillBounds
                    )
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height((screenHeight / 30).dp))
                Text(
                    text = "Welcome to AR Hunt!",
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Normal,
                    color = cyan,
                    fontFamily = FontFamily(Font(R.font.fiddlerscove)),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
                Spacer(modifier = Modifier.height((screenHeight / 30).dp))
                Text(
                    text = buildAnnotatedString {
                        GameDescription.textList.forEach {
                            withStyle(style = paragraphStyle) {
                                append(bullet)
                                append("\t\t")
                                append(it)
                                append("\n")
                            }
                        }
                    },
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Light,
                    color = white,
                    fontFamily = FontFamily(Font(R.font.daysone_regular)),
                    textAlign = TextAlign.Justify,
                    modifier = Modifier.padding(horizontal = 15.dp)
                )
                Text(
                    text = "May the force be with you !!",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                    color = lightGreen,
                    fontFamily = FontFamily(Font(R.font.daysone_regular)),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 20.dp),
                    fontStyle = FontStyle.Italic
                )
                Spacer(modifier = Modifier.height((screenHeight / 40).dp))
                TextButton(
                    onClick = onDismiss,
                    shape = RoundedCornerShape(5.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = cyan)
                ) {
                    Text(
                        text = "CLOSE",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = black,
                        fontFamily = FontFamily(Font(R.font.daysone_regular))
                    )
                }
                Spacer(modifier = Modifier.height((screenHeight / 40).dp))
            }
        }
    }
}
