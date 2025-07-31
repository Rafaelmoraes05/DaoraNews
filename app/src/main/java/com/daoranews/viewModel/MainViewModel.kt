package com.daoranews.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.BiasAlignment
import androidx.lifecycle.ViewModel
import com.daoranews.db.fb.FBDatabase
import com.daoranews.db.fb.FBUser
import com.daoranews.model.User
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.daoranews.model.Article
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel (private val db: FBDatabase): ViewModel(),
    FBDatabase.Listener {

    private val _user = mutableStateOf<User?> (null)
    val user : User?
        get() = _user.value
    init {
        db.setListener(this)
    }
    override fun onUserLoaded(user: FBUser) {
        _user.value = user.toUser()
    }
    override fun onUserSignOut() {
//        _user.value = null
//        _favoritedArticles.value = emptyList()
    }
    private val _favoritedArticles = MutableStateFlow<List<Article>>(emptyList())

    val favoritedArticles = _favoritedArticles.asStateFlow()

    fun toggleFavorite(article: Article) {
        viewModelScope.launch {
            _favoritedArticles.update { currentList ->
                val isAlreadyFavorited = currentList.any { it.id == article.id }

                if (isAlreadyFavorited) {
                    // Se o artigo já existe, retorna uma nova lista sem ele.
                    // TODO: chamar função do Firebase -> db.removeFavorite(article)
                    currentList.filterNot { it.id == article.id }
                } else {
                    // Se o artigo não existe, retorna uma nova lista com ele adicionado.
                    // TODO: chamar função do Firebase -> db.addFavorite(article)
                    currentList + article
                }
            }
        }
    }

    fun isFavorited(articleId: Int) = favoritedArticles.map { list ->
        list.any { it.id == articleId }
    }
}

class MainViewModelFactory(private val db : FBDatabase) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(db) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
