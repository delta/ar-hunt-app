package edu.nitt.delta.orientation22.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.nitt.delta.orientation22.R
import edu.nitt.delta.orientation22.models.Person
import edu.nitt.delta.orientation22.ui.theme.black
import edu.nitt.delta.orientation22.ui.theme.red
import edu.nitt.delta.orientation22.ui.theme.white


@Composable
fun CustomItem(person: Person, isCurrentTeam: Boolean){
    val configuration= LocalConfiguration.current
Row(modifier = Modifier
    .fillMaxWidth()
    .background(
        color = if (isCurrentTeam) red else black,
        shape = RoundedCornerShape(20.dp, 20.dp, 20.dp, 20.dp)
    ), verticalAlignment = Alignment.CenterVertically){

Text(text = "${person.position}", modifier = Modifier
    .fillMaxWidth(0.18f)
    .padding(top = 18.dp, bottom = 18.dp),
    style = TextStyle(
    fontSize = 13.sp,
    fontFamily = FontFamily(Font(R.font.daysone_regular)),
    color = white,
    textAlign = TextAlign.Center,
),
)
    Image(painter = painterResource(id = person.avatar), contentDescription = "profile", modifier = Modifier
        .clip(
            RoundedCornerShape(100)
        )
        .padding(all = 15.dp)
        .size(40.dp))
    MarqueeText(text = person.name,modifier=Modifier.fillMaxWidth(0.5f),
        style = TextStyle(
            fontSize = 13.sp,
            fontFamily = FontFamily(Font(R.font.daysone_regular)),
            color = white,
            textAlign = TextAlign.Center,
        ),
        )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Text(
            text = "${person.points}",
            modifier = Modifier.padding(end = 15.dp),
            style = TextStyle(
                fontSize = 13.sp,
                fontFamily = FontFamily(Font(R.font.daysone_regular)),
                color = white,
            ),
        )
        Icon(
            modifier = Modifier.scale(2f),
            painter = painterResource(id = R.drawable.coins),
            contentDescription = "Coins Icon",
            tint = Color.Unspecified
        )
    }
}
}
@Preview(showBackground = true)
@Composable
fun CustomItemPreview()
{
    CustomItem(person = Person(24,R.drawable.item2,"Kunal",3000), false)
}
