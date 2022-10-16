package com.rsa.contacts.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rsa.contacts.viewModel.ContactsViewModel


class ContactViewModelFactory(private val repository: ContactRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ContactsViewModel::class.java)) {
            ContactsViewModel(repository) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}