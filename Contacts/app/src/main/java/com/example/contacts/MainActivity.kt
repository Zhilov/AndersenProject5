package com.example.contacts

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity(R.layout.activity_main), FragmentMain.LinearClickListener,
    FragmentDetails.OnSaveButtonClickListener {

    private var isPhone by Delegates.notNull<Boolean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        isPhone = !resources.getBoolean(R.bool.isTablet)

        if (supportFragmentManager.findFragmentByTag(FragmentMain.FRAGMENT_MAIN_TAG) == null) {
            supportFragmentManager.beginTransaction().run {
                replace(R.id.fragment_container,
                    FragmentMain.newInstance(),
                    FragmentMain.FRAGMENT_MAIN_TAG)
                commit()
            }
        }
    }

    override fun onBackPressed() {
        val fragment: Fragment =
            supportFragmentManager.findFragmentByTag(FragmentMain.FRAGMENT_MAIN_TAG)!!
        if (fragment.isAdded && fragment.isVisible) {
            finish()
        } else {
            supportFragmentManager.popBackStack()
        }
    }

    override fun onLinearClicked(index: Int, contacts: ArrayList<Contact>) {
        supportFragmentManager.beginTransaction().run {
            if (isPhone) {
                replace(R.id.fragment_container,
                    FragmentDetails.newInstance(index, contacts),
                    FragmentDetails.FRAGMENT_DETAILS_TAG)
                addToBackStack(FragmentDetails.FRAGMENT_DETAILS_TAG)
                commit()
            } else {
                replace(R.id.fragment_container_two,
                    FragmentDetails.newInstance(index, contacts),
                    FragmentDetails.FRAGMENT_DETAILS_TAG)
                commit()
            }

        }
    }

    override fun onSaveClicked(contacts: ArrayList<Contact>) {
        if (isPhone) {
            supportFragmentManager.beginTransaction().run {
                replace(R.id.fragment_container,
                    FragmentMain.newInstance(contacts),
                    FragmentMain.FRAGMENT_MAIN_TAG)
                commit()
            }
        } else {
            supportFragmentManager.beginTransaction().run {
                replace(R.id.fragment_container,
                    FragmentMain.newInstance(contacts),
                    FragmentMain.FRAGMENT_MAIN_TAG)
                commit()
            }
        }
    }
}