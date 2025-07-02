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

// Dados Mockados para o Preview
val mockArticles = listOf(
    Article(1, "Nova Era da F1: Carros de 2025 Revelados!", "As mudanças aerodinâmicas prometem corridas mais disputadas...", "Ana R.", "20 de Maio", 5),
    Article(2, "Nando Norris, da McLaren, Domina em GP de Mônaco", "Uma vitória histórica nas ruas do principado para o jovem piloto.", "Carlos S.", "19 de Maio", 7),
    Article(3, "O Impacto do 5G na Indústria 4.0", "Velocidade e baixa latência estão transformando as fábricas.", "Tech Journal", "18 de Maio", 8),
    Article(4, "Como a IA está redefinindo o mercado de trabalho", "Novas profissões surgem enquanto outras se adaptam à automação.", "Bia S.", "17 de Maio", 6)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage() {
    var selectedTabIndex by remember { mutableStateOf(0) }

    val categories = listOf(
        "Para Você",
        "Tecnologia",
        "Esportes",
        "Mundo",
        "Fórmula 1",
        "Política",
        "Ciência",
        "Economia",
        "Cultura"
    )

    Scaffold(
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            // Abas de Categoria em carrosel.
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


            // Lista de Notícias
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                items(mockArticles) { article ->
                    NewsArticleItem(article = article)
                    Divider(color = MaterialTheme.colorScheme.outline, thickness = 1.dp)
                }
            }
        }
    }
}

@Composable
fun NewsArticleItem(article: Article) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* TODO: Navegar para os detalhes do artigo */ }
            .padding(vertical = 16.dp)
    ) {
        // Título da Notícia
        Text(
            text = article.title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Snippet
        Text(
            text = article.snippet,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.secondary
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Metadados e Ações
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Autor e Tempo de Leitura
            Text(
                text = "${article.author} · ${article.date} · ${article.readTimeInMinutes} min de leitura",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )

            Spacer(modifier = Modifier.weight(1f))

            IconButton(onClick = { /* TODO: Salvar artigo */ }) {
                Icon(
                    imageVector = Icons.Outlined.BookmarkBorder,
                    contentDescription = "Salvar Artigo",
                    tint = MaterialTheme.colorScheme.secondary
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


@Preview(showBackground = true, name = "Home Page Light")
@Composable
fun HomePageLightPreview() {
    DaoraNewsTheme {
        HomePage()
    }
}

@Preview(showBackground = true, name = "Home Page Dark")
@Composable
fun HomePageDarkPreview() {
    DaoraNewsTheme(darkTheme = true) {
        HomePage()
    }
}