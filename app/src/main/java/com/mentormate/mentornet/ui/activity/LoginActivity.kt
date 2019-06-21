package com.mentormate.mentornet.ui.activity

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.mentormate.mentornet.R
import dagger.android.support.DaggerAppCompatActivity


class LoginActivity : DaggerAppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        navController = findNavController(R.id.login_fragment)
    }
}