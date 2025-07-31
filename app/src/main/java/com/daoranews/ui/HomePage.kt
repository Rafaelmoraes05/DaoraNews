package com.daoranews.ui

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.daoranews.model.Article
import com.daoranews.ui.theme.DaoraNewsTheme
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.runtime.collectAsState
import com.daoranews.model.mockArticles
import com.daoranews.viewModel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(
    viewModel: MainViewModel,
    onArticleClick: (Int) -> Unit
) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val categories = listOf(
        "Para Você", "Tecnologia", "Esportes", "Mundo", "Fórmula 1", "Política", "Ciência"
    )

    Scaffold() { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            ScrollableTabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.primary,
                edgePadding = 16.dp
            ) {
                categories.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = { Text(text = title, style = MaterialTheme.typography.bodyMedium) }
                    )
                }
            }

            // A lista agora usa a variável importada 'mockArticles'
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                items(mockArticles) { article ->
                    // A função onArticleClick é passada para cada item

                    val isFavorited by viewModel.isFavorited(article.id).collectAsState(initial = false)
                    NewsArticleItem(
                        article = article,
                        onArticleClick = onArticleClick,
                        isFavorited =isFavorited,
                        onBookmarkClick = { viewModel.toggleFavorite(article) }
                    )
                    Divider(color = MaterialTheme.colorScheme.outline, thickness = 1.dp)
                }
            }
        }
    }
}

@Composable
fun NewsArticleItem(
    article: Article,
    onArticleClick: (Int) -> Unit,
    isFavorited: Boolean,
    onBookmarkClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onArticleClick(article.id) }
            .padding(vertical = 16.dp)
    ) {
        Text(
            text = article.title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = article.snippet,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.secondary
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${article.author} · ${article.date} · ${article.readTimeInMinutes} min de leitura",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.weight(1f))

            IconButton(onClick = onBookmarkClick) {
                Icon(
                    imageVector = if (isFavorited) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                    contentDescription = if (isFavorited) "Remover dos favoritos" else "Salvar Artigo",
                    tint = if (isFavorited) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                )
            }
            IconButton(onClick = { /* TODO: Mostrar mais opções */ }) {
                Icon(
                    imageVector = Icons.Outlined.MoreVert,
                    contentDescription = "Mais Opções",
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}
