package com.daoranews.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.daoranews.model.Article // Importe sua classe Article
import com.daoranews.model.mockArticles
import com.daoranews.ui.theme.DaoraNewsTheme
import com.daoranews.viewModel.MainViewModel
import kotlinx.coroutines.delay

// Estados para a UI da busca, deixando o código mais limpo
sealed class SearchUiState {
    object Idle : SearchUiState() // Estado inicial
    object Loading : SearchUiState() // Buscando...
    data class Success(val articles: List<Article>) : SearchUiState() // Resultados encontrados
    object NoResults : SearchUiState() // Nenhum resultado
}

// Tipos de filtro
enum class FilterType {
    TOPIC, SOURCE
}

@Composable
fun SearchPage(
    viewModel: MainViewModel,
    onArticleClick: (Int) -> Unit
) {
    var searchQuery by rememberSaveable { mutableStateOf("") }
    var activeFilter by remember { mutableStateOf(FilterType.TOPIC) }
    var uiState by remember { mutableStateOf<SearchUiState>(SearchUiState.Idle) }
    val focusManager = LocalFocusManager.current

    // Efeito para simular a busca. No app real, aqui entraria a chamada de API.
    LaunchedEffect(key1 = searchQuery, key2 = activeFilter) {
        if (searchQuery.length > 2) {
            uiState = SearchUiState.Loading
            delay(1000) // Simula a latência da rede
            val results = mockArticles.filter {
                // Simula a lógica do filtro
                when (activeFilter) {
                    FilterType.TOPIC -> it.title.contains(searchQuery, ignoreCase = true)
                    FilterType.SOURCE -> it.author.contains(searchQuery, ignoreCase = true)
                }
            }
            uiState = if (results.isEmpty()) SearchUiState.NoResults else SearchUiState.Success(results)
        } else {
            uiState = SearchUiState.Idle // Volta ao estado inicial se a busca for muito curta
        }
    }

    Scaffold() { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            // Campo de Busca
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                label = { Text("Buscar por ${if (activeFilter == FilterType.TOPIC) "tópicos" else "bancas"}...") },
                leadingIcon = { Icon(Icons.Outlined.Search, "Ícone de busca") },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(Icons.Default.Close, "Limpar busca")
                        }
                    }
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = { focusManager.clearFocus() }) // Esconde o teclado
            )

            // Filtros
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start)
            ) {
                FilterChip(
                    selected = activeFilter == FilterType.TOPIC,
                    onClick = { activeFilter = FilterType.TOPIC },
                    label = { Text("Por Tópico") }
                )
                FilterChip(
                    selected = activeFilter == FilterType.SOURCE,
                    onClick = { activeFilter = FilterType.SOURCE },
                    label = { Text("Por Banca") }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Conteúdo dinâmico baseado no estado da UI
            when (val state = uiState) {
                is SearchUiState.Idle -> SearchPromptMessage()
                is SearchUiState.Loading -> LoadingIndicator()
                is SearchUiState.NoResults -> NoResultsMessage(query = searchQuery)
                is SearchUiState.Success -> {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(state.articles) { article ->
                            val isFavorited by viewModel.isFavorited(article.id).collectAsState(initial = false)

                            NewsArticleItem(
                                article = article,
                                onArticleClick = onArticleClick,
                                isFavorited = isFavorited,
                                onBookmarkClick = { viewModel.toggleFavorite(article) }
                            )
                            Divider(color = MaterialTheme.colorScheme.outline, thickness = 1.dp)
                        }
                    }
                }
            }
        }
    }
}

// --- Componentes Auxiliares ---

@Composable
fun SearchPromptMessage() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            "Busque por tópicos, pessoas ou fontes de notícias.",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}

@Composable
fun LoadingIndicator() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun NoResultsMessage(query: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            "Nenhum resultado encontrado para \"$query\".\n\nTente buscar por outros termos.",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}
