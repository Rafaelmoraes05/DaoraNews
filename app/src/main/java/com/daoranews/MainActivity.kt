package com.daoranews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.daoranews.db.fb.FBDatabase
import com.daoranews.ui.HomePage
import com.daoranews.ui.nav.BottomNavBar
import com.daoranews.ui.nav.BottomNavItem
import com.daoranews.ui.nav.MainNavHost
import com.daoranews.ui.theme.DaoraNewsTheme
import com.daoranews.ui.theme.KindleBlack
import com.daoranews.ui.theme.KindleWhite
import com.daoranews.viewModel.MainViewModel
import com.daoranews.viewModel.MainViewModelFactory
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val fbDB = remember { FBDatabase() }
            val viewModel : MainViewModel = viewModel(
                factory = MainViewModelFactory(fbDB)
            )
            val navController = rememberNavController()
            DaoraNewsTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                val name = viewModel.user?.name?:"[não logado]"
                                Text("Bem-vindo/a! $name")
                            },
                            actions = {
                                IconButton( onClick = {
                                    Firebase.auth.signOut()
                                } ) {
                                    Icon(
                                        imageVector =
                                            Icons.AutoMirrored.Filled.ExitToApp,
                                        contentDescription = "Localized description"
                                    )
                                }
                            }
                        )
                    },
                    bottomBar = {
                        val items = listOf(
                            BottomNavItem.HomeButton,
                            BottomNavItem.ListButton,
                            BottomNavItem.SearchButton,
                        )
                        BottomNavBar(navController = navController, items)
                    },
                    floatingActionButton = {
                        FloatingActionButton(onClick = { },
                            containerColor = KindleBlack,
                            contentColor = KindleWhite,
                            elevation = FloatingActionButtonDefaults.elevation(
                                defaultElevation = 2.dp,
                                pressedElevation = 4.dp)
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "Adicionar")
                        }
                    }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        MainNavHost(navController = navController)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePagePreview() {
    DaoraNewsTheme {
        HomePage(onArticleClick = {})
    }
}