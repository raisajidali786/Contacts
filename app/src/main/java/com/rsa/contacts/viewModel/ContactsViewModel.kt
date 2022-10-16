package com.rsa.contacts.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rsa.contacts.model.ContactRepository
import com.rsa.contacts.model.ContactsModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ContactsViewModel(private val repository: ContactRepository) :
    AndroidViewModel(Application()) {

    private val contactsMutableLiveData = MutableLiveData<ArrayList<ContactsModel>>()
    val contactsLiveData: LiveData<ArrayList<ContactsModel>> = contactsMutableLiveData

    fun fetchContacts() {
        viewModelScope.launch {
            val contactNumbersAsync = async { repository.getContactNumbers() }
            val contactEmailAsync = async { repository.getEmails() }
            val contactPhotosAsync = async { repository.getPhotos() }
            val contactBirthdayAsync = async { repository.getBirthdays() }

            val contactNumbers = contactNumbersAsync.await()
            val contactEmails = contactEmailAsync.await()
            val contactPhotos = contactPhotosAsync.await()
            val contactBirthdays = contactBirthdayAsync.await()

            contactNumbers.forEach {
                it.emails = contactEmails
                it.photos = contactPhotos
                it.birthdays = contactBirthdays
            }
            contactsMutableLiveData.postValue(contactNumbers)
        }
    }

}