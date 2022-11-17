package edu.nitt.delta.orientation22.compose.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import edu.nitt.delta.orientation22.R
import edu.nitt.delta.orientation22.models.Team
import edu.nitt.delta.orientation22.ui.theme.*

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    team: Team,
) {
    Orientation22androidTheme() {
        val painter = painterResource(id = R.drawable.background_image)
        val annotatedString = buildAnnotatedString {
            val str = "MADE WITH ❤ BY DELTA FORCE AND ORIENTATION"
            val indexStartDelta = str.indexOf("DELTA FORCE")
            val indexStartOrientation = str.indexOf("ORIENTATION")
            append(str)
            addStyle(
                style = SpanStyle(
                    color = yellow,
                    fontFamily = FontFamily(Font(R.font.montserrat_regular))
                ),
                start = 0,
                end = 42
            )
            addStyle(
                style = SpanStyle(
                    color = yellow,
                    fontFamily = FontFamily(Font(R.font.montserrat_regular)),
                    textDecoration = TextDecoration.Underline
                ),
                start = indexStartDelta,
                end = indexStartDelta+11
            )
            addStyle(
                style = SpanStyle(
                    color = yellow,
                    fontFamily = FontFamily(Font(R.font.montserrat_regular)),
                    textDecoration = TextDecoration.Underline
                ),
                start = indexStartOrientation,
                end = indexStartOrientation + 11
            )
            addStringAnnotation(
                tag = "URL",
                annotation = "https://delta.nitt.edu/",
                start = indexStartDelta,
                end = indexStartDelta + 11
            )
            addStringAnnotation(
                tag = "URL",
                annotation = "https://www.instagram.com/nitt.orientation/",
                start = indexStartOrientation,
                end = indexStartOrientation + 11
            )
        }
        val uriHandler = LocalUriHandler.current
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
                    .background(
                        translucentBackground,
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,

                ) {
                Text(
                    text = "AR HUNT",
                    fontSize = 45.sp,
                    letterSpacing = 0.15.em,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(all = 30.dp),
                    color = brightYellow,
                    fontFamily = FontFamily(Font(R.font.montserrat_regular))
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = team.teamName,
                    fontSize = 30.sp,
                    letterSpacing = 0.08.em,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier.padding(all = 15.dp),
                    color = brightYellow,
                    fontFamily = FontFamily(Font(R.font.montserrat_regular))
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .height((0.5).dp)
                        .background(
                            color = white,
                        )
                )
                Spacer(modifier = Modifier.height(30.dp))
                Card(1, team.leader.name)
                Card(2, team.members[0].name)
                Card(3, team.members[1].name)
                Card(4, team.members[2].name)
                Spacer(modifier = Modifier.fillMaxSize(0.65f))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    ClickableText(
                        text = annotatedString,
                        onClick = {
                            annotatedString.getStringAnnotations("URL", it, it).firstOrNull()?.let { stringAnnotation ->
                                uriHandler.openUri(stringAnnotation.item)
                            }
                        }
                    )
                }
                }
            }
        }
    }



@Composable
fun Card(index: Int, name: String) {
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
        Image(painter = painterResource(id = R.drawable.profile), contentDescription = "", modifier = Modifier
            .clip(
                RoundedCornerShape(100)
            )
            .padding(all = 15.dp)
            .scale(1.5f))
        Text(
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

