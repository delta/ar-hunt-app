package edu.nitt.delta.orientation22.compose.screens

import android.widget.Toast
import edu.nitt.delta.orientation22.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.nitt.delta.orientation22.ui.theme.Orientation22androidTheme

@Composable
fun LoginScreen(
    painter: Painter, contentDescription: String, modifier: Modifier =Modifier)
{
    val mContext= LocalContext.current
    val fontFamily= FontFamily(
        Font(R.font.montserrat_regular)
    )
    Box(modifier = modifier.fillMaxSize())
    {
        Image(painter = painter, contentDescription = contentDescription,modifier=Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
        Column(modifier=Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,) {
            Box(){Text(
                text= buildAnnotatedString {
                    withStyle(
                        style= SpanStyle(color = Color.hsl(43f,0.52f,0.54f,1f),
                            fontSize = 56.sp, fontFamily=fontFamily, fontWeight = FontWeight(600),
                        )
                    ){append("AR")}
                    append(" HUNT")}, fontSize = 48.sp, color = Color.hsl(43f,0.52f,0.54f,1f)
                ,modifier=Modifier.padding(top=170.dp).blur(0.5.dp), fontWeight = FontWeight(400), fontFamily = fontFamily
            )
                Text(
                    text= buildAnnotatedString {
                        withStyle(
                            style= SpanStyle(color = Color.hsl(43f,0.52f,0.54f,1f),
                                fontSize = 56.sp, fontFamily=fontFamily, fontWeight = FontWeight(600)
                            )
                        ){append("AR")}
                        append(" HUNT")}, fontSize = 48.sp, color = Color.hsl(43f,0.52f,0.54f,1f)
                    ,modifier=Modifier.padding(top=162.dp).blur(0.5.dp).offset(1.dp,2.dp).alpha(0.25f), fontWeight = FontWeight(400), fontFamily = fontFamily
                )}
            Spacer(modifier = Modifier.height(120.dp))
            Button(modifier=Modifier.height(70.dp).width(261.dp),colors= ButtonDefaults.buttonColors(containerColor
            = Color.hsl(0f,0f,0f,0.25f), contentColor = Color.hsl(43f,0.52f,0.54f,1f)
            ),onClick = { Toast.makeText(mContext, "Works!", Toast.LENGTH_LONG).show()}) {
                Column(modifier=Modifier.align(Alignment.CenterVertically)){
                    Text(text = "LOGIN WITH", fontSize = 20.sp, fontFamily = fontFamily,fontWeight = FontWeight(400))
                    Text(modifier=Modifier.absolutePadding(25.dp,0.dp,0.dp,0.dp),text = "DAUTH", fontSize = 20.sp,fontFamily=fontFamily,fontWeight = FontWeight(400))
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    Orientation22androidTheme{
        //LoginScreen()
    }
}
