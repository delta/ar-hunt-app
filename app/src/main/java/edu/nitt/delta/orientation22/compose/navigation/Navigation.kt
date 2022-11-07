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
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import edu.nitt.delta.orientation22.R
import edu.nitt.delta.orientation22.fragments.DashboardFragment
import edu.nitt.delta.orientation22.fragments.LeaderBoardFragment
import edu.nitt.delta.orientation22.fragments.LoginFragment
import edu.nitt.delta.orientation22.fragments.MapFragment
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
            LoginFragment()
        }
        composable(route = NavigationRoutes.Dashboard.route){
            DashboardFragment()
        }
        composable(route = NavigationRoutes.Map.route){
            MapFragment()
        }
        composable(route = NavigationRoutes.LeaderBoard.route){
            LeaderBoardFragment()
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
