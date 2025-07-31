package com.daoranews.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.daoranews.model.Article
import com.daoranews.model.mockArticles
import com.daoranews.model.savedArticlesMock
import com.daoranews.ui.theme.DaoraNewsTheme
import com.daoranews.viewModel.MainViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListPage(
    viewModel: MainViewModel,
    onArticleClick: (Int) -> Unit
) {
    val favoritedArticles by viewModel.favoritedArticles.collectAsState()

    Scaffold() { innerPadding ->
        if (favoritedArticles.isEmpty()) {
            EmptyFavoritesState(modifier = Modifier.padding(innerPadding))
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                items(favoritedArticles, key = { it.id }) { article ->
                    NewsArticleItem(
                        article = article,
                        onArticleClick = onArticleClick,
                        isFavorited = true,
                        onBookmarkClick = { viewModel.toggleFavorite(article) }
                    )
                    Divider(color = MaterialTheme.colorScheme.outline, thickness = 1.dp)
                }
            }
        }
    }
}
@Composable
fun EmptyFavoritesState(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Você ainda não salvou nenhuma notícia.\n\nToque no ícone de marcador em um artigo para guardá-lo aqui.",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, name = "Favoritos - Vazio")
@Composable
fun ListPageEmptyPreview() {
    DaoraNewsTheme {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text("Favoritos", fontWeight = FontWeight.Bold) },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background)
                )
            }
        ) { padding ->
            EmptyFavoritesState(modifier = Modifier.padding(padding))
        }
    }
}