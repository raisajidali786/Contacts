package com.rsa.contacts.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.rsa.contacts.R
import com.rsa.contacts.databinding.CustomContactsItemLayoutBinding
import com.rsa.contacts.model.ContactsModel


class ContactAdapter(private var contactList: ArrayList<ContactsModel>) :
    RecyclerView.Adapter<ContactAdapter.ContactVH>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ContactVH {
        val mDeveloperListItemBinding = DataBindingUtil.inflate<CustomContactsItemLayoutBinding>(
            LayoutInflater.from(viewGroup.context),
            R.layout.custom_contacts_item_layout, viewGroup, false
        )
        return ContactVH(mDeveloperListItemBinding)
    }

    override fun onBindViewHolder(holder: ContactVH, i: Int) {
        val contactsModel = contactList[i]

        with(holder.binding) {
            contactsModel.emails.forEach {
                if (contactsModel.id == it.id) {
                    contactsModel.email = it.email
                }
            }
            contactsModel.photos.forEach {
                if (contactsModel.id == it.id) {
                    contactsModel.photo = it.photo
                }
            }
            contactsModel.birthdays.forEach {
                if (contactsModel.id == it.id) {
                    contactsModel.birthday = it.birthday
                }
            }
        }

        holder.binding.contactModel = contactList[i]

    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    inner class ContactVH(var binding: CustomContactsItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

}