package edu.nitt.delta.orientation22.compose.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.gson.Gson
import edu.nitt.delta.orientation22.R
import edu.nitt.delta.orientation22.fragments.*
import edu.nitt.delta.orientation22.models.Team
import edu.nitt.delta.orientation22.models.TeamMember
import edu.nitt.delta.orientation22.ui.theme.black
import edu.nitt.delta.orientation22.ui.theme.peach
import edu.nitt.delta.orientation22.ui.theme.white
import edu.nitt.delta.orientation22.ui.theme.yellow


@Composable
fun Navigation(navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = NavigationRoutes.Login.route,
    ) {
        composable(route = NavigationRoutes.Login.route){
            LoginFragment(navController)
        }
        composable(route = NavigationRoutes.Dashboard.route){

            var data = Gson().fromJson(it.arguments!!.getString("Team"), Team::class.java)
            if (data == null){
                data = Team(leader = TeamMember("Leader", 106121000, true), members = mutableListOf(
                    TeamMember("Member - 1", 106121000),
                    TeamMember("Member - 2", 106121000),
                    TeamMember("Member - 3", 106121000),
                ))
            }
            DashboardFragment(data)
        }
        composable(route = NavigationRoutes.Map.route){
            MapFragment()
        }
        composable(route = NavigationRoutes.LeaderBoard.route){
            LeaderBoardFragment()
        }
        composable(route = NavigationRoutes.TeamDetails.route){
            TeamDetailsFragment(navController)
        }
    }
}

@Composable
fun BottomNavBar (
    items: List<BottomNavItem>,
    navController: NavController,
    modifier: Modifier,
    onItemClick: (BottomNavItem) -> Unit,
    state: MutableState<Boolean>
) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    NavigationBar(
        modifier = modifier.clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)),
        containerColor = Color.Black,
    ) {
        for (item in items){
            when (item.name) {
                "Home" -> {
                    val selected = item.route == backStackEntry.value?.destination?.route
                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            onItemClick(item)
                            state.value = false
                        },
                        colors = NavigationBarItemDefaults.colors(
                            unselectedIconColor = yellow,
                            selectedIconColor = white,
                            indicatorColor = black,
                        ),
                        icon = {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                if (selected){
                                    Icon(painterResource(R.drawable.home_screen_selected), item.name)
                                } else {
                                    Icon(painterResource(R.drawable.home_screen_unselected), item.name)
                                }
                                Text(
                                    text = item.name,
                                    textAlign = TextAlign.Center,
                                    fontSize = 10.sp,
                                )
                            }
                        }
                    )
                }
                "Leaderboard" -> {
                    val selected = item.route == backStackEntry.value?.destination?.route
                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            onItemClick(item)
                            state.value = false
                        },
                        colors = NavigationBarItemDefaults.colors(
                            unselectedIconColor = yellow,
                            selectedIconColor = white,
                            indicatorColor = black,
                        ),
                        icon = {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                if (selected){
                                    Icon(painterResource(R.drawable.leaderboard_selected), item.name)
                                } else {
                                    Icon(painterResource(R.drawable.leaderboard_unselected), item.name)
                                }
                                Text(
                                    text = item.name,
                                    textAlign = TextAlign.Center,
                                    fontSize = 10.sp,
                                )
                            }
                        }
                    )

                }
            }
        }
    }
}

@Composable
fun BottomBar(
    checkedState: MutableState<Boolean>,
    navController: NavController,
) {
    Box(
        modifier = Modifier.fillMaxHeight(0.12f)
    ) {
        BottomNavBar(
            items = listOf(
                BottomNavItem(
                    name = "Home",
                    route = "dashboard",
                ),
                BottomNavItem(
                    name = "Map",
                    route = "maps",
                ),
                BottomNavItem(
                    name = "Leaderboard",
                    route = "leaderBoard",
                ),
            ),
            navController = navController,
            onItemClick = {
                navController.navigate(it.route)
            }, modifier = Modifier.align(Alignment.BottomCenter),
            state = checkedState,
        )

        IconToggleButton(
            checked = checkedState.value,
            onCheckedChange = {
                if (!checkedState.value){
                    checkedState.value = !checkedState.value
                }
                navController.navigate("maps") {
                    popUpTo("dashboard")
                }
            },
            modifier = Modifier
                .then(
                    Modifier
                        .size(90.dp)
                        .align(Alignment.TopCenter)
                )
                .clip(
                    CircleShape
                )
                .background(yellow)
                .border(5.dp, peach, shape = CircleShape),
        ) {
            Icon(
                painter =
                if (checkedState.value)
                    painterResource(R.drawable.map_screen_selected)
                else
                    painterResource(R.drawable.map_screen_unselected),
                contentDescription = "Map Screen",
                tint = black,
                modifier = Modifier.size(30.dp)
            )
        }
    }
}
