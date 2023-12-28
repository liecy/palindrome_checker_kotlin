package com.rahmi.palindromechecker.views

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rahmi.palindromechecker.data.Repository
import com.rahmi.palindromechecker.data.User

class ThirdViewModel(repository: Repository): ViewModel() {
    val users: LiveData<PagingData<User>> =
        repository.getUsers().cachedIn(viewModelScope)
}