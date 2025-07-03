@file:OptIn(ExperimentalMaterial3Api::class)

package com.daoranews.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.daoranews.model.getArticleById
import com.daoranews.ui.theme.DaoraNewsTheme

@Composable
fun ArticleDetailPage(
    articleId: Int,
    onNavigateBack: () -> Unit // Função para ser chamada ao clicar no botão de voltar
) {
    // Simulando a busca do artigo. No app real, isso viria de um ViewModel.
    val article = remember { getArticleById(articleId) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { /* Título pode ficar vazio para mais minimalismo */ },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO: Lógica para salvar */ }) {
                        Icon(Icons.Outlined.BookmarkBorder, contentDescription = "Salvar")
                    }
                    IconButton(onClick = { /* TODO: Lógica para compartilhar */ }) {
                        Icon(Icons.Outlined.Share, contentDescription = "Compartilhar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { innerPadding ->
        if (article != null) {
            // Usamos LazyColumn para o conteúdo ser rolável e performático
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                // 1. Título do Artigo
                item {
                    Text(
                        text = article.title,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                // 2. Metadados (Autor, Data)
                item {
                    Text(
                        text = "${article.author} · ${article.date}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // 3. Divisor sutil
                item {
                    Divider(color = MaterialTheme.colorScheme.outline)
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // 4. Conteúdo Completo do Artigo
                item {
                    Text(
                        text = article.fullContent,
                        style = MaterialTheme.typography.bodyLarge,
                        // A altura da linha é CRUCIAL para a legibilidade!
                        lineHeight = 24.sp
                    )
                    Spacer(modifier = Modifier.height(32.dp)) // Espaço no final
                }
            }
        } else {
            // Estado de erro caso o artigo não seja encontrado
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Artigo não encontrado.")
            }
        }
    }
}

// --- Preview ---
@Preview(showBackground = true, name = "Article Detail Page")
@Composable
fun ArticleDetailPagePreview() {
    DaoraNewsTheme {
        ArticleDetailPage(articleId = 1, onNavigateBack = {})
    }
}