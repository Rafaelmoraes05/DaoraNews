@file:OptIn(ExperimentalMaterial3Api::class)

package com.daoranews.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.daoranews.model.getArticleById
import com.daoranews.ui.theme.DaoraNewsTheme
import com.daoranews.ui.theme.KindleBlack
import com.daoranews.ui.theme.KindleWhite
import com.daoranews.viewModel.MainViewModel

@Composable
fun ArticleDetailPage(
    articleId: Int,
    viewModel: MainViewModel,
    onNavigateBack: () -> Unit
) {
    val article = remember { getArticleById(articleId) }
    val isFavorited by viewModel.isFavorited(articleId).collectAsState(initial = false)
    var isFabMenuExpanded by remember { mutableStateOf(false) }

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
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        floatingActionButton = {
            if (article != null) {
                Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    AnimatedVisibility(
                        visible = isFabMenuExpanded,
                        enter = fadeIn() + slideInVertically(initialOffsetY = { it / 2 }),
                        exit = fadeOut() + slideOutVertically(targetOffsetY = { it / 2 })
                    ) {
                        Column(
                            horizontalAlignment = Alignment.End,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            // Botão de Compartilhar
                            SmallFloatingActionButton(
                                onClick = {
                                    // TODO: Lógica para compartilhar o artigo
                                    isFabMenuExpanded = false
                                },
                                containerColor = KindleBlack,
                                contentColor = KindleWhite
                            ) {
                                Icon(Icons.Outlined.Share, "Compartilhar Artigo")
                            }

                            // Botão de Favoritar
                            SmallFloatingActionButton(
                                onClick = {
                                    viewModel.toggleFavorite(article)
                                    isFabMenuExpanded = false
                                },
                                containerColor = KindleBlack,
                                contentColor = KindleWhite
                            ) {
                                Icon(
                                    imageVector = if (isFavorited) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                                    contentDescription = "Favoritar Artigo"
                                )
                            }
                        }
                    }

                    // Botão principal que expande/recolhe o menu
                    FloatingActionButton(
                        onClick = { isFabMenuExpanded = !isFabMenuExpanded },
                        containerColor = KindleBlack,
                        contentColor = KindleWhite
                    ) {
                        Icon(
                            // Muda o ícone para 'X' quando o menu está aberto
                            imageVector = if (isFabMenuExpanded) Icons.Default.Close else Icons.Default.Add,
                            contentDescription = "Abrir menu de ações"
                        )
                    }
                }
            }
        }
    )
    { innerPadding ->
        if (article != null) {
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
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Artigo não encontrado.")
            }
        }
    }
}
