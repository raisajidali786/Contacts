package com.rsa.contacts.model

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract
import android.util.Log
import com.rsa.contacts.view.utils.Constants.REPOSITORY_TAG

class ContactRepository(private val context: Context) {

    //::::....FETCH NUMBERS....:::://
    fun getContactNumbers(): ArrayList<ContactsModel> {
        var lastNumber: String? = null
        val contactList = ArrayList<ContactsModel>()
        val contactCursor: Cursor? = context.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
        )
        if ((contactCursor != null) && (contactCursor.count > 0)) {
            val contactIdIndex =
                contactCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID)
            val numberIndex =
                contactCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
            val nameIndex =
                contactCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
            while (contactCursor.moveToNext()) {
                val contactId = contactCursor.getString(contactIdIndex)
                val number: String =
                    contactCursor.getString(numberIndex).filter { !it.isWhitespace() }
                var name: String = contactCursor.getString(nameIndex)
                if (name == number) {
                    name = "No Name"
                }
                if (!lastNumber.equals(number)) {
                    lastNumber = number.filter { !it.isWhitespace() }
                    val contactsModel = ContactsModel()
                    contactsModel.id = contactId
                    contactsModel.number = number
                    contactsModel.name = name
                    contactList.add(contactsModel)
                }
            }
            Log.d(REPOSITORY_TAG, "contactList : $contactList")
            Log.d(REPOSITORY_TAG, "contactList - size : ${contactList.size}")
            contactCursor.close()
        }
        return contactList
    }


    //::::....FETCH PHOTOS....:::://
    fun getPhotos(): ArrayList<ContactsModel> {
        val projection = arrayOf(
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.Contacts.Photo.PHOTO_URI,
        )
        val photoList = ArrayList<ContactsModel>()
        val photoCursor: Cursor? = context.contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            projection,
            null,
            null,
            null
        )
        if ((photoCursor != null) && (photoCursor.count > 0)) {
            val idIndex = photoCursor.getColumnIndex(ContactsContract.Contacts._ID)
            val photoIndex =
                photoCursor.getColumnIndex(ContactsContract.CommonDataKinds.Photo.PHOTO_URI)
            while (photoCursor.moveToNext()) {
                val id = photoCursor.getString(idIndex)
                val photo = photoCursor.getString(photoIndex)
                if (photo != null) {
                    val contactsModel = ContactsModel()
                    contactsModel.id = id
                    contactsModel.photo = Uri.parse(photo)
                    photoList.add(contactsModel)
                }
            }
            Log.d(REPOSITORY_TAG, "photoList : $photoList")
            Log.d(REPOSITORY_TAG, "photoList - size : ${photoList.size}")
            photoCursor.close()
        }
        return photoList
    }


    //::::....FETCH EMAILS....:::://
    fun getEmails(): ArrayList<ContactsModel> {
        val emailList = ArrayList<ContactsModel>()
        val emailCursor = context.contentResolver.query(
            ContactsContract.CommonDataKinds.Email.CONTENT_URI,
            null,
            null,
            null,
            null
        )
        if ((emailCursor != null) && (emailCursor.count > 0)) {
            val contactIdIndex =
                emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.CONTACT_ID)
            val emailIndex =
                emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS)
            while (emailCursor.moveToNext()) {
                val contactId = emailCursor.getString(contactIdIndex)
                val email = emailCursor.getString(emailIndex)
                val contactsModel = ContactsModel()
                contactsModel.id = contactId
                contactsModel.email = email
                emailList.add(contactsModel)
            }
            emailCursor.close()
        }
        Log.d(REPOSITORY_TAG, "emailList : $emailList")
        Log.d(REPOSITORY_TAG, "emailList - size : ${emailList.size}")
        return emailList
    }


    //::::....FETCH BIRTHDAYS....:::://
    fun getBirthdays(): ArrayList<ContactsModel> {
        val birthdayList = ArrayList<ContactsModel>()
        val projection = arrayOf(
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Event.CONTACT_ID,
            ContactsContract.CommonDataKinds.Event.START_DATE
        )
        val where =
            ContactsContract.Data.MIMETYPE + "= ? AND " +
                    ContactsContract.CommonDataKinds.Event.TYPE + "=" +
                    ContactsContract.CommonDataKinds.Event.TYPE_BIRTHDAY
        val selectionArgs = arrayOf(
            ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE
        )
        val birthdayCursor = context.contentResolver.query(
            ContactsContract.Data.CONTENT_URI,
            projection,
            where,
            selectionArgs,
            null
        )

        if (birthdayCursor != null && birthdayCursor.count > 0) {

            val idIndex =
                birthdayCursor.getColumnIndex(ContactsContract.Data.CONTACT_ID)
            val bDayColumn =
                birthdayCursor.getColumnIndex(ContactsContract.CommonDataKinds.Event.START_DATE)
            while (birthdayCursor.moveToNext()) {
                val id = birthdayCursor.getString(idIndex)
                val bDay = birthdayCursor.getString(bDayColumn)
                val model = ContactsModel()
                model.id = id
                model.birthday = bDay
                birthdayList.add(model)
            }
            birthdayCursor.close()
            Log.d(REPOSITORY_TAG, "birthdayList : $birthdayList")
            Log.d(REPOSITORY_TAG, "birthdayList - size : ${birthdayList.size}")
        }
        return birthdayList
    }

}