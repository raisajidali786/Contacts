package com.rsa.contacts.model

import android.net.Uri

data class ContactsModel(
    var id: String? = null,
    var name: String = "No Name",
    var photo: Uri? = null,
    var number: String? = null,
    var email: String = "Not Added",
    var birthday: String = "Not Added"
) {
    var photos = ArrayList<ContactsModel>()
    var emails = ArrayList<ContactsModel>()
    var birthdays = ArrayList<ContactsModel>()
}
