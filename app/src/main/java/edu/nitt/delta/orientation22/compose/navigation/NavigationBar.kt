package edu.nitt.delta.orientation22.compose.navigation

import android.Manifest
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import edu.nitt.delta.orientation22.compose.LocationPermissionGetter
import edu.nitt.delta.orientation22.ui.theme.black
import edu.nitt.delta.orientation22.ui.theme.*

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
        modifier = modifier.clip(RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp)),
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
                    selectedIconColor = it.color,
                    unselectedIconColor = grey,
                    indicatorColor = black
                ),
                icon = {
                    Icon(
                        painter = painterResource(it.icon),
                        contentDescription = it.name,
                        modifier = modifier.scale(if (selected) 2.4f else 2.0f)
                    )
                }
            )
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun BottomBar(
    checkedState: MutableState<Boolean>,
    navController: NavController,
) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    val screenWidth = LocalConfiguration.current.screenWidthDp

    val permissionState = rememberMultiplePermissionsState(permissions = listOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
    ))

    LocationPermissionGetter(permissionState)

    Box(
        modifier = Modifier.height((screenWidth/3.0).dp)
    ) {
        BottomNavBar(
            items = NavigationList.NavList.navList,
            navController = navController,
            onItemClick = {
                if (backStackEntry.value!!.destination.route!! != it.route) {
                    navController.navigate(it.route) {
                        launchSingleTop = true
                        popUpTo(NavigationRoutes.Dashboard.route)
                    }
                }
            }, modifier = Modifier.align(Alignment.BottomCenter),
            state = checkedState,
        )
    }
}