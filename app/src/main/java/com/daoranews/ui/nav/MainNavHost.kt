package com.daoranews.ui.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.daoranews.ui.HomePage
import com.daoranews.ui.ListPage
import com.daoranews.ui.SearchPage

@Composable
fun MainNavHost(navController: NavHostController) {
    NavHost(navController, startDestination = Route.Home) {
        composable<Route.Home> { HomePage() }
        composable<Route.List> { ListPage() }
        composable<Route.Search> { SearchPage() }
    }
}