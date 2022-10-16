package com.rsa.contacts.view.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rsa.contacts.R
import com.rsa.contacts.databinding.ActivityMainBinding
import com.rsa.contacts.model.ContactRepository
import com.rsa.contacts.model.ContactViewModelFactory
import com.rsa.contacts.model.ContactsModel
import com.rsa.contacts.view.adapter.ContactAdapter
import com.rsa.contacts.view.utils.Constants.READ_CONTACT_REQUEST_CODE
import com.rsa.contacts.viewModel.ContactsViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var repository: ContactRepository

    private lateinit var contactViewModel: ContactsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        inIt()
        runtimePermission()
    }

    private fun runtimePermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS),
            READ_CONTACT_REQUEST_CODE
        )
    }

    private fun inIt() {
        repository = ContactRepository(this)
        contactViewModel = ViewModelProvider(
            this,
            ContactViewModelFactory(repository)
        )[ContactsViewModel::class.java]
    }

    private fun getAllContact() {
        contactViewModel.fetchContacts()
        contactViewModel.contactsLiveData.observe(this) {
            if (it.isNotEmpty()) {
                setAdapter(it)
            }
        }

    }

    private fun setAdapter(list: ArrayList<ContactsModel>) {
        val contactAdapter = ContactAdapter(list)
        binding.contactRecycler.layoutManager = LinearLayoutManager(this)
        binding.contactRecycler.adapter = contactAdapter
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            READ_CONTACT_REQUEST_CODE -> if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getAllContact()
            } else {
                Toast.makeText(this, "Permission Not Granted", Toast.LENGTH_SHORT).show()
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

}

