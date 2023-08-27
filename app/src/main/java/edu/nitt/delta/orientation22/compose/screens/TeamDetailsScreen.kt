package edu.nitt.delta.orientation22.compose.screens

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.*
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import edu.nitt.delta.orientation22.R
import edu.nitt.delta.orientation22.compose.LoadingIcon
import edu.nitt.delta.orientation22.compose.reverseAvatarList
import edu.nitt.delta.orientation22.compose.toast
import edu.nitt.delta.orientation22.di.viewModel.uiState.RegistrationState
import edu.nitt.delta.orientation22.models.Team
import edu.nitt.delta.orientation22.models.TeamMember
import edu.nitt.delta.orientation22.models.auth.Member
import edu.nitt.delta.orientation22.models.auth.TeamModel
import edu.nitt.delta.orientation22.ui.theme.*


@SuppressLint("MutableCollectionMutableState")
@Composable
fun TeamDetails(
    mContext: Context,
    teamDetails: TeamModel,
    registerTeam: (TeamModel) -> Unit,
    state: RegistrationState
) {

    var nameLeader by rememberSaveable { mutableStateOf(teamDetails.members[0].name) }

    var rollNumberLeader by rememberSaveable {mutableStateOf(if (teamDetails.members[0].rollNo != -1) teamDetails.members[0].rollNo.toString() else "")}
    var teamName by rememberSaveable { mutableStateOf(teamDetails.teamName) }

    var nameMember1 by rememberSaveable { mutableStateOf(teamDetails.members[1].name) }
    var rollNumberMember1 by rememberSaveable {mutableStateOf(if (teamDetails.members[1].rollNo != -1) teamDetails.members[0].rollNo.toString() else "")}
    var nameMember2 by rememberSaveable { mutableStateOf(teamDetails.members[2].name) }
    var rollNumberMember2 by rememberSaveable {mutableStateOf(if (teamDetails.members[2].rollNo != -1) teamDetails.members[0].rollNo.toString() else "")}
    var nameMember3 by rememberSaveable { mutableStateOf(teamDetails.members[3].name) }
    var rollNumberMember3 by rememberSaveable {mutableStateOf(if (teamDetails.members[3].rollNo != -1) teamDetails.members[0].rollNo.toString() else "")}

    var selectedAvatar by remember { mutableStateOf<Int>(R.drawable.ic_action_name) }

    TeamNameHeader(teamName = teamName, onValueChange = {teamName = it})

   TeamLeader(title = "Team Leader", isEnabled = true, data = nameLeader, header = "Name",keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text), onValueChange = {nameLeader = it})
    TeamLeader(title = "Team Leader", isEnabled = true, data = rollNumberLeader, header = "Roll Number",keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text), onValueChange = {rollNumberLeader = it})

    TextInput(title = "ONBOARD PIRATES", isEnabled = true, data = nameMember1, header = "Name", keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text), onValueChange = {nameMember1 = it})
    TextInput(title = "", isEnabled = true, data = rollNumberMember1, header = "Roll Number", keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number), onValueChange = {rollNumberMember1 = it})
    TextInput(title = "", isEnabled = true, data = nameMember2, header = "Name", keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text), onValueChange = {nameMember2 = it})
    TextInput(title = "", isEnabled = true, data = rollNumberMember2, header = "Roll Number", keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number), onValueChange = {rollNumberMember2 = it})
    TextInput(title = "", isEnabled = true, data = nameMember3, header = "Name", keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text), onValueChange = {nameMember3 = it})
    TextInput(title = "", isEnabled = true, data = rollNumberMember3, header = "Roll Number", keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number), onValueChange = {rollNumberMember3 = it})

    SubmitButton(
        teamName = teamName,
        nameLeader = nameLeader,
        rollNumberLeader = rollNumberLeader,
        nameMembers = listOf(nameMember1, nameMember2, nameMember3),
        rollNumberMembers = listOf(rollNumberMember1, rollNumberMember2, rollNumberMember3),
        mContext = mContext,
        registerTeam = registerTeam,
        selectedAvatar = reverseAvatarList[selectedAvatar]?:1,
        state = state
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TeamDetailsScreen(
    teamDetails: TeamModel,
    registerTeam: (TeamModel) -> Unit,
    state :RegistrationState
){
    Orientation22androidTheme {
       val mContext = LocalContext.current
        val painter = painterResource(id = R.drawable.bg)
        Box(modifier = Modifier.fillMaxSize()) {
            Spacer(

                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)

                )
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
                    .background(transparent)
                    ,
                horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.Top
                ) {
                    Column (
                        horizontalAlignment = Alignment.Start
                    ){
                        Text(
                            text = "PIRATES",
                            fontSize = 45.sp,
                            letterSpacing = 0.15.em,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 30.dp),
                            color = white,
                            fontFamily = FontFamily(Font(R.font.fiddlerscovecondital))
                        )
                        Text(
                            text = "REGISTRATION",
                            fontSize = 45.sp,
                            letterSpacing = 0.15.em,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 30.dp),
                            color = white,
                            fontFamily = FontFamily(Font(R.font.fiddlerscovecondital))
                        )

                    }


                    Image(painter = painterResource(id = R.drawable.skull_reg), contentDescription = "icon", modifier = Modifier.size(200.dp))
            }

                TeamDetails(mContext = mContext, teamDetails, registerTeam,state)
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
    Row(){
        Text(
            text = "SHIP NAME",
            fontSize = 30.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(top = 15.dp, bottom = 5.dp),
            color = white,
            fontFamily = FontFamily(Font(R.font.fiddlerscovecondital))
        )
        TextField(
            value = teamName,
            onValueChange = onValueChange,
            label = { Text("ENTER NAME", fontFamily = FontFamily(Font(R.font.fiddlerscovecondital)))},
            maxLines = 1,
            enabled = true,
            singleLine = true,
            textStyle = TextStyle(color = black, fontWeight = FontWeight.Normal, fontSize = 30.sp,
                fontFamily = FontFamily(Font(R.font.fiddlerscovecondital))
            ),


            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize()
                .padding(start = 10.dp),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = cyan,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                textColor = black,
                cursorColor = black,
                focusedLabelColor = black,
                unfocusedLabelColor = black,
            )
        )

    }

}
@Composable
fun SubmitButton(
    teamName: String,
    nameLeader: String,
    rollNumberLeader: String,
    nameMembers: List<String>,
    rollNumberMembers: List<String>,
    mContext: Context,
    registerTeam: (TeamModel) -> Unit,
    selectedAvatar : Int,
    state: RegistrationState
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

                val registerData = TeamModel(
                    teamName = team.teamName,
                    members = listOf<Member>(Member(name =team.members[0].name, rollNo =team.members[0].rollNo.toInt()),
                        Member(name =team.members[1].name, rollNo =team.members[1].rollNo.toInt()),
                        Member(name =team.members[2].name, rollNo =team.members[2].rollNo.toInt())),
                    avatar = selectedAvatar
                )
                registerTeam(registerData)


            }
        },
        content = {
            if (state == RegistrationState.LOADING) LoadingIcon()
            else Text(
                text = "Register",
                fontSize = 30.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(5.dp),
                color = black,
                fontFamily = FontFamily(Font(R.font.daysone_regular))
            )
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = darkpink,
            contentColor = black,
        ),
        modifier = Modifier
            .padding(20.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamLeader(
    title: String,
    isEnabled : Boolean,
    data: String,
    header: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions
) {
    Column (modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start){
        if (header == "Name"){
            Text(
                text = title,
                fontSize = 30.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(top = 15.dp, bottom = 5.dp),
                color = white,
                fontFamily = FontFamily(Font(R.font.fiddlerscovecondital)),
            )
        }
        Column {
            TextField(
                value = data,
                onValueChange = onValueChange,
                label = { Text(header) },
                maxLines = 1,
                enabled = isEnabled,
                singleLine = true,
                textStyle = TextStyle(color = black, fontWeight = FontWeight.Normal,
                    fontFamily = FontFamily(Font(R.font.fiddlerscovecondital)), fontSize = 30.sp
                ),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(0.85f),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = white,
                )
            )

        }
        }
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
            fontSize = 30.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(top = 15.dp, bottom = 5.dp),
            color = white,
            fontFamily = FontFamily(Font(R.font.fiddlerscovecondital))
        )
    }
    TextField(
        value = data,
        onValueChange = onValueChange,
        label = { Text(header) },
        maxLines = 1,
        enabled = isEnabled,
        singleLine = true,
        textStyle = TextStyle(color = black, fontWeight = FontWeight.Normal,
            fontFamily = FontFamily(Font(R.font.fiddlerscovecondital)), fontSize = 30.sp
            ),
        keyboardOptions = keyboardOptions,
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(0.85f),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = white,
            focusedIndicatorColor = transparent,
            unfocusedIndicatorColor = transparent,
            disabledIndicatorColor = transparent,
            textColor = transparent,
            cursorColor = black,
            focusedLabelColor = transparent,
            unfocusedLabelColor = transparent,
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







