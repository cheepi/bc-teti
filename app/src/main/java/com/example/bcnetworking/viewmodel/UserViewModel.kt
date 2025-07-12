package com.example.bcnetworking.viewmodel

import androidx.lifecycle.*
import com.example.bcnetworking.model.User
import com.example.bcnetworking.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(private val repo: UserRepository) : ViewModel() {

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users

    fun fetchUsers() {
        viewModelScope.launch {
            _users.value = repo.getUsers()
        }
    }

    fun addUser(name: String, email: String) {
        viewModelScope.launch {
            repo.addUser(User(name = name, email = email))
            fetchUsers()
        }
    }

    fun deleteUser(id: String) {
        viewModelScope.launch {
            repo.deleteUser(id)
            fetchUsers()
        }
    }
}
