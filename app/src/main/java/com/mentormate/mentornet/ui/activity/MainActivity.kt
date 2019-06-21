package com.mentormate.mentornet.ui.activity

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mentormate.mentornet.R
import com.mentormate.mentornet.data.AppDatabase
import com.mentormate.mentornet.data.authentication.AuthCredentialsDao
import com.mentormate.mentornet.ui.viewmodel.MainActivityViewModel
import com.mentormate.mentornet.utilities.AppExecutors
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

var isBackPressed: Boolean = false

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModel: MainActivityViewModel
    @Inject
    lateinit var authCredentialsDao: AuthCredentialsDao
    @Inject
    lateinit var appExecutors: AppExecutors
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var googleSignInClient: GoogleSignInClient
    @Inject
    lateinit var appDatabase: AppDatabase

    private lateinit var toolbar: Toolbar

    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(
            this,
            viewModelFactory
        )[MainActivityViewModel::class.java]

        //Preloads Employees, GeneralInformation, StoreItems, RankLists
        if (viewModel.getConnectionStatus()) {
            viewModel.preloadData()
        } else {
            Toast.makeText(applicationContext, applicationContext.getString(R.string.offline_mode), Toast.LENGTH_SHORT)
                .show()
        }
        //Setting toolbar
        setupToolbar()
        setupNavigation()
    }

    override fun onPause() {
        isBackPressed = false
        super.onPause()
    }

    override fun onBackPressed() {
        if (navController.currentDestination?.id == navController.graph.startDestination) {
            if (isBackPressed) {
                moveTaskToBack(true)
            } else {
                Toast.makeText(
                    applicationContext,
                    R.string.press_back_again_string,
                    Toast.LENGTH_LONG
                ).show()
                isBackPressed = true
            }
        } else
            if (!navController.navigateUp()) {
                super.onBackPressed()
            }
    }

    private fun setupToolbar() {
        toolbar = findViewById(R.id.toolBar)
        setSupportActionBar(toolbar)
    }

    private fun setupNavigation() {
        navController = findNavController(R.id.main_fragment)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation_menu)
        bottomNavigation.setupWithNavController(navController)

        bottomNavigation.setOnNavigationItemSelectedListener(BottomNavigationItemSelectedListener(navController))
    }
}

class BottomNavigationItemSelectedListener(private val navController: NavController) :
    BottomNavigationView.OnNavigationItemSelectedListener {
    var currentItem: String = R.string.boards_string.toString()
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        isBackPressed = false
        if (!item.title.equals(currentItem)) {
            currentItem = item.title.toString()
            when (item.itemId) {
                R.id.peopleTab -> navController.navigate(R.id.action_global_employeeListFragment)
                R.id.kudosTab -> navController.navigate(R.id.action_global_kudosFragment)
                R.id.boardsTab -> navController.navigate(R.id.action_global_boardsFragment)
                R.id.profileTab -> navController.navigate(R.id.action_global_profileFragment)
            }
        }
        return true
    }
}
