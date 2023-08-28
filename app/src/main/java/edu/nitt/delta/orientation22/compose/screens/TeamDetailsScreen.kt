package edu.nitt.delta.orientation22.compose.screens

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.*
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.text.isDigitsOnly
import edu.nitt.delta.orientation22.R
import edu.nitt.delta.orientation22.compose.LoadingIcon
import edu.nitt.delta.orientation22.compose.avatarList
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
    var nameLeader = rememberSaveable { mutableStateOf(teamDetails.members[0].name) }
    var rollNumberLeader = rememberSaveable {mutableStateOf(teamDetails.members[0].rollNo.toString())}
    var teamName = rememberSaveable { mutableStateOf(teamDetails.teamName) }

    var nameMember1 = rememberSaveable { mutableStateOf(teamDetails.members[1].name) }
    var rollNumberMember1 = rememberSaveable {mutableStateOf(teamDetails.members[1].rollNo.toString())}
    var nameMember2 = rememberSaveable { mutableStateOf(teamDetails.members[2].name) }
    var rollNumberMember2 = rememberSaveable {mutableStateOf(teamDetails.members[2].rollNo.toString())}
    var nameMember3 = rememberSaveable { mutableStateOf(teamDetails.members[3].name) }
    var rollNumberMember3 = rememberSaveable {mutableStateOf(teamDetails.members[3].rollNo.toString())}

    var names = rememberSaveable {
        mutableStateOf(
            listOf(
                mutableStateOf(nameMember1.value),
                mutableStateOf(nameMember2.value),
                mutableStateOf(nameMember3.value)
            )
        )
    }

    var rollNumbers = rememberSaveable {
        mutableStateOf(
            listOf(
                mutableStateOf(rollNumberMember1.value),
                mutableStateOf(rollNumberMember2.value),
                mutableStateOf(rollNumberMember3.value)
            )
        )
    }

    var selectedAvatar by remember { mutableStateOf<Int>(R.drawable.baseline_add_circle_24) }
    var memberCount by remember { mutableStateOf (0) }

    PlainInput(inputContent = teamName.value, field = "SHIP Name", onValueChange = {teamName.value = it})

    Spacer(modifier = Modifier.height(16.dp))

    TextInput(title = "CAPTAIN", isEnabled = false, data = nameLeader.value, header = "Name", keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text), onValueChange = {nameLeader.value = it})
    TextInput(title = "Team Leader", isEnabled = false, data = if (rollNumberLeader.value == "-1") "" else rollNumberLeader.value, header = "Roll Number", keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number), onValueChange = {rollNumberLeader.value = it})

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        repeat(memberCount){ index ->
            TextInput(
                title = "Pirate - ${index + 1}",
                isEnabled = true,
                data = names.value[index].value,
                header = "Name",
                onValueChange = {
                    names.value[index].value = it
                                },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
            )
            TextInput(
                title = "Pirate - ${index + 1}",
                isEnabled = true,
                data = if (rollNumbers.value[index].value == "-1") "" else rollNumbers.value[index].value,
                header = "Roll Number",
                onValueChange = {rollNumbers.value[index].value = it},
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ModifyMember(
                onClick = {
                    if (memberCount >= 3) {
                        Toast.makeText(
                            mContext,
                            "A team can have a maximum of only 4 members.",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        memberCount ++
                    }
                },
                icon = Icons.Default.Add
            )
            ModifyMember(
                onClick = {
                    if (memberCount > 0){
                        memberCount --
                    }
                },
                icon = Icons.Default.Delete
            )
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

    SubmitButton(
        teamName = teamName.value,
        nameLeader = nameLeader.value,
        rollNumberLeader = rollNumberLeader.value,
        nameMembers = listOf(names.value[0].value, names.value[1].value, names.value[2].value),
        rollNumberMembers = listOf(rollNumbers.value[0].value, rollNumbers.value[1].value, rollNumbers.value[2].value),
        mContext = mContext,
        registerTeam = registerTeam,
        selectedAvatar = reverseAvatarList[selectedAvatar]?:1,
        state = state,
        memberCount = memberCount,
    )
}

@Composable
fun ModifyMember(
    onClick: () -> Unit,
    icon: ImageVector
) {
    OutlinedButton(onClick = onClick,
        modifier= Modifier.size(50.dp),
        shape = CircleShape,
        border= BorderStroke(2.dp, lightGrey),
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.outlinedButtonColors(contentColor =  lightGrey)
    ) {
        androidx.compose.material3.Icon(icon, contentDescription = "Modify", tint = lightGrey)
    }
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
        val painter = painterResource(id = R.drawable.background)
        var chooseAvatar by remember { mutableStateOf(false) }
        var selectedAvatar by remember { mutableStateOf<Int>(R.drawable.baseline_add_circle_24) }
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
                Row(
                    modifier = Modifier.padding(all = 20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "PIRATES\n\nREGISTRATION",
                        fontSize = 40.sp,
                        modifier = Modifier.padding(end = 20.dp),
                        color = white,
                        fontFamily = FontFamily(Font(R.font.fiddlerscove))
                    )
                    Image(
                        painter = painterResource(id = R.drawable.splash_skull),
                        contentDescription = "Skull",
                    )
                }
                Spacer(modifier = Modifier.height(5.dp))

                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "PICK AVATAR",
                        fontSize = 30.sp,
                        modifier = Modifier.padding(start = 20.dp, end = 15.dp),
                        color = white,
                        fontFamily = FontFamily(Font(R.font.fiddlerscove)),
                        textAlign = TextAlign.Right
                    )

                    Card(
                        modifier = Modifier
                            .size(100.dp)
                            .padding(end = 20.dp),
                        elevation = 0.dp,
                        backgroundColor = Color.Transparent
                    ) {
                        Row() {
                            Image(
                                painter = painterResource(selectedAvatar),
                                contentDescription = "",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clickable { chooseAvatar = true }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(15.dp))

                TeamDetails(mContext = mContext, teamDetails, registerTeam,state)
            }
            if (chooseAvatar) {
                Dialog(
                    onDismissRequest = {
                        chooseAvatar = false
                    },
                    content = {

                        val state = rememberLazyListState()
                        val layoutInfo = remember { derivedStateOf { state.layoutInfo } }

                        layoutInfo.value.afterContentPadding

                        Card(
                            elevation = 0.dp,
                            modifier = Modifier
                                .height(150.dp)
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(15.dp),
                            border = BorderStroke(3.dp, brightYellow),
                            content = {
                                LazyRow(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(darkBlue),
                                    verticalAlignment = Alignment.CenterVertically,
                                    state = state,
                                    flingBehavior = rememberSnapFlingBehavior(lazyListState = state),
                                    contentPadding = PaddingValues(10.dp),
                                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                                ) {
                                    items(avatarList.values.toList()) { avatar ->
                                        Card(
                                            modifier = Modifier.size(100.dp),
                                            shape = CircleShape,
                                            backgroundColor = darkBlue
                                        ) {
                                            Image(
                                                painterResource(avatar),
                                                contentDescription = "",
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .selectable(
                                                        selected = false,
                                                        onClick = {
                                                            selectedAvatar = avatar
                                                            chooseAvatar = false
                                                        }
                                                    )
                                                    .size(50.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        )
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlainInput(
    inputContent: String,
    field: String,
    onValueChange: (String) -> Unit,
){
    Row(
        modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically,
    ){
        Text(
            text = field,
            fontSize = 30.sp,
            modifier = Modifier.padding(start = 20.dp, end = 15.dp),
            color = white,
            fontFamily = FontFamily(Font(R.font.fiddlerscove)),
            textAlign = TextAlign.Right
        )

        TextField(
            value = inputContent,
            onValueChange = onValueChange,
            maxLines = 1,
            enabled = true,
            singleLine = true,
            textStyle = TextStyle(color = black, fontWeight = FontWeight.Normal,
                fontFamily = FontFamily(Font(R.font.fiddlerscove)),
                fontSize = 25.sp
            ),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .padding(end = 20.dp)
                .fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = lightCyan,
                focusedIndicatorColor = transparent,
                unfocusedIndicatorColor = transparent,
                disabledIndicatorColor = transparent,
                textColor = black,
                cursorColor = black,
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
    selectedAvatar: Int,
    state: RegistrationState,
    memberCount: Int
){

    Button(
        onClick = {

            val leader = TeamMember(nameLeader, rollNumberLeader, true)
            val members = mutableListOf<TeamMember>()
            for (i in 0 until memberCount){
                members.add(TeamMember(nameMembers[i], rollNumberMembers[i]))
            }

            val team = Team(leader = leader, members = members, teamName = teamName)

            if (validate(team, mContext)){

                val membersList = mutableListOf<Member>()

                for (i in 0 until memberCount) {
                    membersList.add(Member(name = team.members[i].name, rollNo = team.members[i].rollNo.toInt()))
                }

                val registerData = TeamModel(
                    teamName = team.teamName,
                    members = membersList,
                    avatar = selectedAvatar
                )

                registerTeam(registerData)
            }
        },
        content = {
            if (state == RegistrationState.LOADING) LoadingIcon()
            else Text(
                text = "REGISTER",
                fontSize = 25.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(5.dp),
                color = black,
                fontFamily = FontFamily(Font(R.font.daysone_regular))
            )
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = red,
            contentColor = black,
        ),
        modifier = Modifier
            .padding(20.dp).fillMaxWidth(0.8f),
        shape = RoundedCornerShape(25)
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
            fontSize = 30.sp,
            modifier = Modifier.padding(top = 15.dp, bottom = 5.dp),
            color = white,
            fontFamily = FontFamily(Font(R.font.fiddlerscove))
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
            fontFamily = FontFamily(Font(R.font.fiddlerscove)),
            fontSize = 20.sp
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
            textColor = black,
            cursorColor = black,
            focusedLabelColor = darkBlue,
            unfocusedLabelColor = darkBlue,
            disabledLabelColor = red,
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






