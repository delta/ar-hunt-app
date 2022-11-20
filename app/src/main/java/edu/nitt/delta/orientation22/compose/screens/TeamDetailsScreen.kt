package edu.nitt.delta.orientation22.compose.screens

import android.annotation.SuppressLint
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
import androidx.compose.runtime.saveable.rememberSaveable
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
import edu.nitt.delta.orientation22.di.viewModel.actions.TeamAction
import edu.nitt.delta.orientation22.models.Team
import edu.nitt.delta.orientation22.models.TeamMember
import edu.nitt.delta.orientation22.models.auth.RegisterTeamRequest
import edu.nitt.delta.orientation22.models.auth.RegisterTeamResponse
import edu.nitt.delta.orientation22.models.auth.TeamModel
import edu.nitt.delta.orientation22.ui.theme.*


@SuppressLint("MutableCollectionMutableState")
@Composable
fun TeamDetails(
    mContext: Context,
    teamDetails: TeamModel,
    registerTeam: (Map<String, String>) -> Unit,
) {
    var nameLeader by rememberSaveable { mutableStateOf(teamDetails.members[0].name) }
    var rollNumberLeader by rememberSaveable {mutableStateOf(teamDetails.members[0].rollNo.toString())}
    var teamName by rememberSaveable { mutableStateOf(teamDetails.teamName) }

    var nameMember1 by rememberSaveable { mutableStateOf(teamDetails.members[1].name) }
    var rollNumberMember1 by rememberSaveable {mutableStateOf(teamDetails.members[1].rollNo.toString())}
    var nameMember2 by rememberSaveable { mutableStateOf(teamDetails.members[2].name) }
    var rollNumberMember2 by rememberSaveable {mutableStateOf(teamDetails.members[2].rollNo.toString())}
    var nameMember3 by rememberSaveable { mutableStateOf(teamDetails.members[3].name) }
    var rollNumberMember3 by rememberSaveable {mutableStateOf(teamDetails.members[3].rollNo.toString())}

    TeamNameHeader(teamName = teamName, onValueChange = {teamName = it})

    TextInput(title = "Team Leader", isEnabled = false, data = nameLeader, header = "Name", keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text), onValueChange = {nameLeader = it})
    TextInput(title = "Team Leader", isEnabled = false, data = rollNumberLeader, header = "Roll Number", keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number), onValueChange = {rollNumberLeader = it})

    TextInput(title = "Member - 1", isEnabled = true, data = nameMember1, header = "Name", keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text), onValueChange = {nameMember1 = it})
    TextInput(title = "Member - 1", isEnabled = true, data = rollNumberMember1, header = "Roll Number", keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number), onValueChange = {rollNumberMember1 = it})
    TextInput(title = "Member - 2", isEnabled = true, data = nameMember2, header = "Name", keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text), onValueChange = {nameMember2 = it})
    TextInput(title = "Member - 2", isEnabled = true, data = rollNumberMember2, header = "Roll Number", keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number), onValueChange = {rollNumberMember2 = it})
    TextInput(title = "Member - 3", isEnabled = true, data = nameMember3, header = "Name", keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text), onValueChange = {nameMember3 = it})
    TextInput(title = "Member - 3", isEnabled = true, data = rollNumberMember3, header = "Roll Number", keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number), onValueChange = {rollNumberMember3 = it})

    SubmitButton(
        teamName = teamName,
        nameLeader = nameLeader,
        rollNumberLeader = rollNumberLeader,
        nameMembers = listOf(nameMember1, nameMember2, nameMember3),
        rollNumberMembers = listOf(rollNumberMember1, rollNumberMember2, rollNumberMember3),
        mContext = mContext,
        registerTeam = registerTeam,
    )
}

@Composable
fun TeamDetailsScreen(
    teamDetails: TeamModel,
    registerTeam: (Map<String, String>) -> Unit,
){
    Orientation22androidTheme {
        val mContext = LocalContext.current
        val painter = painterResource(id = R.drawable.background_image)
        Box(modifier = Modifier.fillMaxSize()) {
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

                TeamDetails(mContext = mContext, teamDetails, registerTeam)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamNameHeader(
    teamName: String,
    onValueChange: (String) -> Unit,
){
    Text(
        text = "Team Name",
        fontSize = 20.sp,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(top = 15.dp, bottom = 5.dp),
        color = brightYellow,
        fontFamily = FontFamily(Font(R.font.montserrat_regular))
    )
    TextField(
        value = teamName,
        onValueChange = onValueChange,
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
}

@Composable
fun SubmitButton(
    teamName: String,
    nameLeader: String,
    rollNumberLeader: String,
    nameMembers: List<String>,
    rollNumberMembers: List<String>,
    mContext: Context,
    registerTeam: (Map<String, String>) -> Unit,
){

    Button(
        onClick = {

            val leader = TeamMember(nameLeader, rollNumberLeader, true)
            val members = mutableListOf<TeamMember>()
            for (i in nameMembers.indices){
                members.add(TeamMember(nameMembers[i], rollNumberMembers[i]))
            }
            val team = Team(leader = leader, members = members, teamName = teamName)

            if (validate(team, mContext)){

                val registerData = RegisterTeamRequest(
                    teamName = team.teamName,
                    member2Name = team.members[0].name,
                    member2RollNo = team.members[0].rollNo.toInt(),
                    member3Name = team.members[1].name,
                    member3RollNo = team.members[1].rollNo.toInt(),
                    member4Name = team.members[2].name,
                    member4RollNo = team.members[2].rollNo.toInt(),
                )
                registerTeam(registerData.toMap())

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextInput(
    title: String,
    isEnabled : Boolean,
    data: String,
    header: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
) {
    if (header == "Name"){
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(top = 15.dp, bottom = 5.dp),
            color = brightYellow,
            fontFamily = FontFamily(Font(R.font.montserrat_regular))
        )
    }
    TextField(
        value = data,
        onValueChange = onValueChange,
        label = { Text(header) },
        maxLines = 1,
        enabled = isEnabled,
        singleLine = true,
        textStyle = TextStyle(color = peach, fontWeight = FontWeight.Normal,
            fontFamily = FontFamily(Font(R.font.montserrat_regular))
            ),
        keyboardOptions = keyboardOptions,
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
    if (team.teamName.trim() == ""){
        mContext.toast("Team Name is empty!")
        return false
    }
    else if (team.teamName.trim().count() < 3){
        mContext.toast("Team Name is too short!")
        return false
    }
    for (member in team.members){
        if (member.name.trim() == ""){
            mContext.toast("Member - ${team.members.indexOf(member) + 1}'s Name is empty!")
            return false
        }
        else if (member.name.trim().count() < 3){
            mContext.toast("Member - ${team.members.indexOf(member) + 1}'s Name is too short!")
            return false
        }
        if (!(member.rollNo.isDigitsOnly() && member.rollNo.count() == 9)){
            mContext.toast("Member - ${team.members.indexOf(member) + 1}'s Roll Number is invalid!")
            return false
        }
    }
    var countMembers = 0
    for (member1 in team.members){
        for (member2 in team.members){
            if (member1.rollNo == member2.rollNo){
                countMembers ++
            }
        }
    }
    if (countMembers == team.members.size){
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







