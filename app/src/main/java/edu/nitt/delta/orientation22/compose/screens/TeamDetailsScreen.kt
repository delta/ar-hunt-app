package edu.nitt.delta.orientation22.compose.screens

import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.gson.Gson
import edu.nitt.delta.orientation22.R
import edu.nitt.delta.orientation22.models.Team
import edu.nitt.delta.orientation22.models.TeamMember
import edu.nitt.delta.orientation22.ui.theme.*

@Composable
fun TeamDetailsScreen(
    modifier: Modifier = Modifier,
    navController: NavController
){
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
                    .verticalScroll(rememberScrollState())
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
                    fontSize = 45.sp,
                    letterSpacing = 0.15.em,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(all = 30.dp),
                    color = brightYellow,
                    fontFamily = FontFamily(Font(R.font.montserrat_regular))
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "Team Name",
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
                val nameLeader = remember { mutableStateOf("Rajesh") }
                val rollNumberLeader = remember {mutableStateOf("106121000")}
                val nameMembers = remember {
                    listOf(mutableStateOf(""), mutableStateOf(""), mutableStateOf(""))
                }
                val rollNumberMembers = remember {
                    listOf(mutableStateOf(""), mutableStateOf(""), mutableStateOf(""))
                }

                TextInput(title = "Team Leader", isEnabled = false, name = nameLeader, rollNumber = rollNumberLeader)
                for (i in 1..3){
                    TextInput(title = "Member - $i", isEnabled = true, name = nameMembers[i-1], rollNumber = rollNumberMembers[i-1])
                }
                Button(
                    onClick = {
                        val leader = TeamMember(nameLeader.value, rollNumberLeader.value.toInt(), true)
                        val members = mutableListOf<TeamMember>()
                        for (i in 0..2){
                            members.add(TeamMember(nameMembers[i].value, rollNumberMembers[i].value.toInt()))
                        }
                        val team = Team(leader = leader, members = members)
                        val bundle = Bundle()
                        bundle.putString("Team", Gson().toJson(team).toString())
                        navController.navigate(resId = navController.findDestination("dashboard")!!.id, args = bundle)
                    },
                    content = {
                        Text("Submit")
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = black,
                        contentColor = brightYellow,
                    ),
                    modifier = Modifier
                        .fillMaxHeight(0.15f)
                        .fillMaxWidth(0.4f)
                        .padding(20.dp)
                )
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextInput(
    title: String,
    isEnabled : Boolean,
    name: MutableState<String>,
    rollNumber: MutableState<String>,
) {
    Text(
        text = title,
        fontSize = 20.sp,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(top = 15.dp, bottom = 5.dp),
        color = brightYellow,
        fontFamily = FontFamily(Font(R.font.montserrat_regular))
    )
    TextField(
        value = name.value,
        onValueChange = { name.value = it },
        label = { Text("Name") },
        maxLines = 1,
        enabled = isEnabled,
        singleLine = true,
        textStyle = TextStyle(color = white, fontWeight = FontWeight.Normal,
            fontFamily = FontFamily(Font(R.font.montserrat_regular))
            ),
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(0.85f),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = translucentBox,
            focusedIndicatorColor = transparent,
            unfocusedIndicatorColor = transparent,
            disabledIndicatorColor = transparent,
        )
    )
    TextField(
        value = rollNumber.value,
        onValueChange = { rollNumber.value = it },
        label = { Text("Roll Number") },
        maxLines = 1,
        enabled = isEnabled,
        singleLine = true,
        textStyle = TextStyle(color = white, fontWeight = FontWeight.Normal,
            fontFamily = FontFamily(Font(R.font.montserrat_regular))
        ),
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(0.85f),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = translucentBox,
            focusedIndicatorColor = transparent,
            unfocusedIndicatorColor = transparent,
            disabledIndicatorColor = transparent,
        )
    )
}







