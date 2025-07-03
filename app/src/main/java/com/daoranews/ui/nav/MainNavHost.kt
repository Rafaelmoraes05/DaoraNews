package com.daoranews.ui.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.daoranews.ui.HomePage
import com.daoranews.ui.ListPage
import com.daoranews.ui.SearchPage
import com.daoranews.ui.ArticleDetailPage
import androidx.navigation.toRoute

@Composable
fun MainNavHost(navController: NavHostController) {
    NavHost(navController, startDestination = Route.Home) {

        composable<Route.Home> {
            HomePage(
                onArticleClick = { articleId ->
                    navController.navigate(Route.ArticleDetail(articleId = articleId))
                }
            )
        }

        composable<Route.List> {
            ListPage(
                onArticleClick = { articleId ->
                    navController.navigate(Route.ArticleDetail(articleId = articleId))
                }
            )
        }

        composable<Route.Search> {
            SearchPage(
                onArticleClick = { articleId ->
                    navController.navigate(Route.ArticleDetail(articleId = articleId))
                }
            )
        }

        composable<Route.ArticleDetail> { backStackEntry ->
            val routeArgs = backStackEntry.toRoute<Route.ArticleDetail>()

            ArticleDetailPage(
                articleId = routeArgs.articleId,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}