package edu.nitt.delta.orientation22.compose.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import edu.nitt.delta.orientation22.R
import edu.nitt.delta.orientation22.ui.theme.Orientation22androidTheme
import edu.nitt.delta.orientation22.ui.theme.brightYellow
import edu.nitt.delta.orientation22.ui.theme.white
import kotlin.text.Typography

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier
) {
    Orientation22androidTheme() {
        val painter = painterResource(id = R.drawable.background_image)
        Box(modifier = modifier.fillMaxSize()) {
            Image(
                painter = painter,
                contentDescription = "background",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .padding(top = 50.dp)
                    .fillMaxSize()
                    .background(
                        Color.hsl(
                            0f, 0f, 0f, 0.3f
                        ),
                        RoundedCornerShape(4, 4)
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,

                ) {
                Text(
                    text = "AR HUNT",
                    fontSize = 50.sp,
                    letterSpacing = 0.2.em,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(all = 30.dp),
                    color = brightYellow,
                    fontFamily = FontFamily(Font(R.font.montserrat_regular))
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "Team Name",
                    fontSize = 35.sp,
                    letterSpacing = 0.1.em,
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
                Card(1, "Sarvesh")
                Card(2, "Sarvesh")
                Card(3, "Sarvesh")
                Card(4, "Sarvesh")
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
                Color.hsl(
                    0f, 0f, 0f, 0.2f
                ),
                RoundedCornerShape(35, 35, 35, 35)
            ),
            verticalAlignment = Alignment.CenterVertically,
        )
    {
        Text(
            text = index.toString(),
            fontSize = 15.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(all = 20.dp).padding(start = 20.dp),
            color = white,
            fontFamily = FontFamily(Font(R.font.montserrat_regular))
        )
        Image(painter = painterResource(id = R.drawable.profile), contentDescription = "", modifier = Modifier.clip(
            RoundedCornerShape(100)
        ).padding(all = 15.dp).scale(1.5f))
        Text(
            text = name,
            fontSize = 15.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(all = 15.dp).padding(start = 10.dp),
            color = white,
            fontFamily = FontFamily(Font(R.font.montserrat_regular))
        )
    }
}

