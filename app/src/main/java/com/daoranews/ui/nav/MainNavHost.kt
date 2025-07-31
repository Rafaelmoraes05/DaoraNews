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
import com.daoranews.viewModel.MainViewModel

@Composable
fun MainNavHost(navController: NavHostController, viewModel: MainViewModel) {
    NavHost(navController, startDestination = Route.Home) {

        composable<Route.Home> {
            HomePage(
                viewModel = viewModel,
                onArticleClick = { articleId ->
                    navController.navigate(Route.ArticleDetail(articleId = articleId))
                }
            )
        }

        composable<Route.List> {
            ListPage(
                viewModel = viewModel,
                onArticleClick = { articleId ->
                    navController.navigate(Route.ArticleDetail(articleId = articleId))
                }
            )
        }

        composable<Route.Search> {
            SearchPage(
                viewModel = viewModel,
                onArticleClick = { articleId ->
                    navController.navigate(Route.ArticleDetail(articleId = articleId))
                }
            )
        }

        composable<Route.ArticleDetail> { backStackEntry ->
            val routeArgs = backStackEntry.toRoute<Route.ArticleDetail>()

            ArticleDetailPage(
                articleId = routeArgs.articleId,
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}