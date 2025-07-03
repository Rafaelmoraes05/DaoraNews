package com.daoranews.ui.nav

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme // <<< Importe o MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults // <<< Importe os padrões do item
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy // <<< Importe para a hierarquia
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.serializer

@Composable
fun BottomNavBar(navController: NavHostController, items: List<BottomNavItem>) {

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.secondaryContainer,

        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        items.forEach { item ->
            val selected = currentDestination?.hierarchy?.any {
                it.route == serializer(item.route::class.java).descriptor.serialName
            } == true

            NavigationBarItem(
                label = { Text(text = item.title, fontSize = 12.sp) },
                icon = { Icon(imageVector = item.icon, contentDescription = item.title) },
                selected = selected,
                onClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) { saveState = true }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                    selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                    indicatorColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.6f),
                    unselectedTextColor = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.6f)
                )
            )
        }
    }
}