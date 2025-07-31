package com.daoranews.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.BiasAlignment
import androidx.lifecycle.ViewModel
import com.daoranews.db.fb.FBDatabase
import com.daoranews.db.fb.FBUser
import com.daoranews.model.User
import androidx.lifecycle.ViewModelProvider

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
        //TODO("Not yet implemented")
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
