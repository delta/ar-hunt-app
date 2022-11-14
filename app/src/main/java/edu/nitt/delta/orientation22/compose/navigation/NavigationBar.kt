package edu.nitt.delta.orientation22.compose.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
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
import androidx.navigation.compose.currentBackStackEntryAsState
import edu.nitt.delta.orientation22.R
import edu.nitt.delta.orientation22.ui.theme.black
import edu.nitt.delta.orientation22.ui.theme.peach
import edu.nitt.delta.orientation22.ui.theme.white
import edu.nitt.delta.orientation22.ui.theme.yellow

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
        containerColor = black,
    ) {
        items.forEach {
            val selected = it.route == backStackEntry.value?.destination?.route
            NavigationBarItem(
                selected = selected,
                onClick = {
                    onItemClick(it)
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
                            Icon(painterResource(it.selectedIcon), it.name)
                        } else {
                            Icon(painterResource(it.unselectedIcon), it.name)
                        }
                        Text(
                            text = it.name,
                            textAlign = TextAlign.Center,
                            fontSize = 10.sp,
                        )
                    }
                }
            )
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
            items = NavigationList.NavList.navList,
            navController = navController,
            onItemClick = {
                navController.navigate(it.route){
                    launchSingleTop = true
                    popUpTo(NavigationRoutes.Dashboard.route)
                }
            }, modifier = Modifier.align(Alignment.BottomCenter),
            state = checkedState,
        )

        IconToggleButton(
            checked = checkedState.value,
            onCheckedChange = {
                if (!checkedState.value){
                    checkedState.value = !checkedState.value
                }
                navController.navigate(NavigationRoutes.Map.route) {
                    launchSingleTop = true
                    popUpTo(NavigationRoutes.Dashboard.route)
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