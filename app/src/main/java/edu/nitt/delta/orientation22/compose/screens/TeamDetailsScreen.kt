package edu.nitt.delta.orientation22.compose.screens

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import com.google.gson.Gson
import edu.nitt.delta.orientation22.MainActivity
import edu.nitt.delta.orientation22.R
import edu.nitt.delta.orientation22.compose.toast
import edu.nitt.delta.orientation22.models.Team
import edu.nitt.delta.orientation22.models.TeamMember
import edu.nitt.delta.orientation22.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamDetailsScreen(
    modifier: Modifier = Modifier,
){
    Orientation22androidTheme() {
        val mContext = LocalContext.current
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
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .background(
                        translucentBackground
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
                    text = "Team Details",
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
                val teamName = remember { mutableStateOf("") }
                val nameMembers = remember {
                    listOf(mutableStateOf(""), mutableStateOf(""), mutableStateOf(""))
                }
                val rollNumberMembers = remember {
                    listOf(mutableStateOf(""), mutableStateOf(""), mutableStateOf(""))
                }
                Text(
                    text = "Team Name",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(top = 15.dp, bottom = 5.dp),
                    color = brightYellow,
                    fontFamily = FontFamily(Font(R.font.montserrat_regular))
                )
                TextField(
                    value = teamName.value,
                    onValueChange = { teamName.value = it },
                    label = { Text("Team Name") },
                    maxLines = 1,
                    enabled = true,
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
                        textColor = yellow,
                        cursorColor = yellow,
                        focusedLabelColor = yellow,
                        unfocusedLabelColor = peach,
                    )
                )
                TextInput(title = "Team Leader", isEnabled = false, name = nameLeader, rollNumber = rollNumberLeader)
                for (i in 1..3){
                    TextInput(title = "Member - $i", isEnabled = true, name = nameMembers[i-1], rollNumber = rollNumberMembers[i-1])
                }
                Button(
                    onClick = {
                        val leader = TeamMember(nameLeader.value, rollNumberLeader.value, true)
                        val members = mutableListOf<TeamMember>()
                        for (i in 0..2){
                            members.add(TeamMember(nameMembers[i].value, rollNumberMembers[i].value))
                        }
                        val team = Team(leader = leader, members = members, teamName = teamName.value)

                        if (validate(team, mContext)){
                            val bundle = Bundle()
                            bundle.putString("Team", Gson().toJson(team).toString())
                            val intent = Intent(mContext, MainActivity::class.java)
                            mContext.startActivity(intent)
                        }
                    },
                    content = {
                        Text(
                            text = "Submit",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(5.dp),
                            color = brightYellow,
                            fontFamily = FontFamily(Font(R.font.montserrat_regular))
                        )
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = black,
                        contentColor = brightYellow,
                    ),
                    modifier = Modifier
                        .padding(20.dp)
                )
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
        textStyle = TextStyle(color = peach, fontWeight = FontWeight.Normal,
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
            textColor = yellow,
            cursorColor = yellow,
            focusedLabelColor = yellow,
            unfocusedLabelColor = peach,
        )
    )
    TextField(
        value = rollNumber.value,
        onValueChange = { rollNumber.value = it },
        label = { Text("Roll Number") },
        maxLines = 1,
        enabled = isEnabled,
        singleLine = true,
        textStyle = TextStyle(color = peach, fontWeight = FontWeight.Normal,
            fontFamily = FontFamily(Font(R.font.montserrat_regular))
        ),
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(0.85f),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = translucentBox,
            focusedIndicatorColor = transparent,
            unfocusedIndicatorColor = transparent,
            disabledIndicatorColor = transparent,
            textColor = yellow,
            cursorColor = yellow,
            focusedLabelColor = yellow,
            unfocusedLabelColor = peach,
        )
    )
}

fun validate(team: Team, mContext: Context): Boolean {
    if (team.teamName == ""){
        mContext.toast("Team Name is empty!")
        return false
    }
    else if (team.teamName.count() < 3){
        mContext.toast("Team Name is too short!")
        return false
    }
    for (member in team.members){
        val regex = "^[A-Za-z ]+$".toRegex()
        if (!regex.matches(member.name)){
            if (!regex.matches(member.name.replace('.', ' '))){
                mContext.toast("Member - ${team.members.indexOf(member) + 1}'s Name is invalid!")
                return false
            }
        }
        if (!(member.rollNo.isDigitsOnly() && member.rollNo.count() == 9)){
            mContext.toast("Member - ${team.members.indexOf(member) + 1}'s Roll Number is invalid!")
            return false
        }
    }
    var count = 0
    for (member1 in team.members){
        for (member2 in team.members){
            if (member1.rollNo == member2.rollNo){
                count ++
            }
        }
    }
    if (count == 3){
        for (member in team.members){
            if (member.rollNo == team.leader.rollNo){
                mContext.toast("Roll Numbers must be unique!")
                return false
            }
        }
    }
    else {
        mContext.toast("Roll Numbers must be unique!")
        return false
    }
    return true
}







