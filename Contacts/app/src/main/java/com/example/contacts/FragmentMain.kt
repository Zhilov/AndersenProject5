package com.example.contacts

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment

class FragmentMain : Fragment(R.layout.fragment_main), View.OnClickListener {

    private lateinit var contacts: ArrayList<Contact>
    private lateinit var linearClickListener: LinearClickListener

    private lateinit var linearMom: LinearLayout
    private lateinit var nameMom: TextView
    private lateinit var phoneMom: TextView

    private lateinit var linearDad: LinearLayout
    private lateinit var nameDad: TextView
    private lateinit var phoneDad: TextView

    private lateinit var linearFriend: LinearLayout
    private lateinit var nameFriend: TextView
    private lateinit var phoneFriend: TextView

    override fun onAttach(context: Context) {
        super.onAttach(context)

        linearClickListener = context as LinearClickListener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        contacts = ArrayList()

        linearMom = view.findViewById(R.id.linear_mom)
        nameMom = view.findViewById(R.id.name_mom)
        phoneMom = view.findViewById(R.id.phone_mom)

        linearDad = view.findViewById(R.id.linear_dad)
        nameDad = view.findViewById(R.id.name_dad)
        phoneDad = view.findViewById(R.id.phone_dad)

        linearFriend = view.findViewById(R.id.linear_friend)
        nameFriend = view.findViewById(R.id.name_friend)
        phoneFriend = view.findViewById(R.id.phone_friend)

        linearMom.setOnClickListener(this)
        linearDad.setOnClickListener(this)
        linearFriend.setOnClickListener(this)

        if (arguments != null) {
            for (index in 0..2) {
                contacts.add(requireArguments().getParcelable(CONTACT_EXTRA + index)!!)
            }
        } else {
            contacts.add(Contact(0,
                "Mom",
                resources.getString(R.string._89738452346).toLong(),
                "This is my mom"))
            contacts.add(Contact(1,
                "Dad",
                resources.getString(R.string._87869542985).toLong(),
                "This is my dad"))
            contacts.add(Contact(2,
                "Friend",
                resources.getString(R.string._89145469174).toLong(),
                "This is my friend"))
        }

        nameMom.text = contacts[0].name
        phoneMom.text = contacts[0].phone.toString()

        nameDad.text = contacts[1].name
        phoneDad.text = contacts[1].phone.toString()

        nameFriend.text = contacts[2].name
        phoneFriend.text = contacts[2].phone.toString()

    }


    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.linear_mom -> linearClickListener.onLinearClicked(0, contacts)
            R.id.linear_dad -> linearClickListener.onLinearClicked(1, contacts)
            R.id.linear_friend -> linearClickListener.onLinearClicked(2, contacts)
        }
    }


    interface LinearClickListener {
        fun onLinearClicked(index: Int, contacts: ArrayList<Contact>)
    }

    companion object {
        const val FRAGMENT_MAIN_TAG = "FRAGMENT_MAIN_TAG"
        private const val CONTACT_EXTRA = "CONTACT_EXTRA"

        fun newInstance() = FragmentMain()

        fun newInstance(contacts: ArrayList<Contact>) = FragmentMain().also {
            it.arguments = Bundle().apply {
                for (cont: Contact in contacts) {
                    putParcelable(CONTACT_EXTRA + cont.id, cont)
                }
            }
        }
    }
}