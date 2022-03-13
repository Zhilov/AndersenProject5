package com.example.contacts

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment

class FragmentDetails : Fragment(R.layout.fragment_details) {

    private lateinit var editName: EditText
    private lateinit var editPhone: EditText
    private lateinit var editDescription: EditText

    private lateinit var onSaveButtonClickListener: OnSaveButtonClickListener

    override fun onAttach(context: Context) {
        super.onAttach(context)

        onSaveButtonClickListener = context as OnSaveButtonClickListener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editName = view.findViewById(R.id.edit_name)
        editPhone = view.findViewById(R.id.edit_phone)
        editDescription = view.findViewById(R.id.edit_description)

        val contacts: ArrayList<Contact> = ArrayList()
        for (index in 0..2) {
            contacts.add(requireArguments().getParcelable(CONTACT_EXTRA + index)!!)
        }

        val contact: Contact = contacts[requireArguments().getInt(CONTACT_EXTRA)]
        editName.setText(contact.name)
        editPhone.setText(contact.phone.toString())
        editDescription.setText(contact.description)

        view.findViewById<Button>(R.id.button_save).setOnClickListener {
            contacts[requireArguments().getInt(CONTACT_EXTRA)].name = editName.text.toString()
            contacts[requireArguments().getInt(CONTACT_EXTRA)].phone =
                editPhone.text.toString().toLong()
            contacts[requireArguments().getInt(CONTACT_EXTRA)].description =
                editDescription.text.toString()
            onSaveButtonClickListener.onSaveClicked(contacts)
        }
    }

    interface OnSaveButtonClickListener {
        fun onSaveClicked(contacts: ArrayList<Contact>)
    }

    companion object {
        const val FRAGMENT_DETAILS_TAG = "FRAGMENT_DETAILS_TAG"
        private const val CONTACT_EXTRA = "CONTACT_EXTRA"

        fun newInstance(index: Int, contacts: ArrayList<Contact>) = FragmentDetails().also {
            it.arguments = Bundle().apply {
                for (cont: Contact in contacts) {
                    putParcelable(CONTACT_EXTRA + cont.id, cont)
                }
                putInt(CONTACT_EXTRA, index)
            }
        }
    }
}