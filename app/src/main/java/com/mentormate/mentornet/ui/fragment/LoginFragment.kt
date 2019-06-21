package com.mentormate.mentornet.ui.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.mentormate.mentornet.R
import com.mentormate.mentornet.adapter.retrofit.authentication.AuthenticationNetworkBoundRepository
import com.mentormate.mentornet.data.authentication.AuthCredentials
import com.mentormate.mentornet.data.authentication.AuthCredentialsDao
import com.mentormate.mentornet.ui.activity.MainActivity
import com.mentormate.mentornet.ui.viewmodel.LoginFragmentViewModel
import com.mentormate.mentornet.utilities.CRASH
import com.mentormate.mentornet.utilities.OAUTH_CLIENT_ID
import com.mentormate.mentornet.utilities.RC_SIGN_IN
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class LoginFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var authenticationNetworkBounRepo: AuthenticationNetworkBoundRepository

    @Inject
    lateinit var googleSignInClient: GoogleSignInClient

    @Inject
    lateinit var authDao: AuthCredentialsDao

    private lateinit var mAuth: FirebaseAuth
    private lateinit var gso: GoogleSignInOptions
    private lateinit var signInButton: SignInButton
    private lateinit var navController: NavController
    private lateinit var fireBaseDb: DatabaseReference
    private var account: GoogleSignInAccount? = null

    lateinit var viewModel: LoginFragmentViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       if ((account != null) && (account?.isExpired!!)) {
           startActivity(Intent(activity, MainActivity::class.java))
        }
    }

    private fun signInGoogle() {
        val signinIntent: Intent = googleSignInClient.signInIntent
     startActivityForResult(signinIntent, RC_SIGN_IN)



    }

    private fun handleResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            account = completedTask.getResult(ApiException::class.java)!!
            account!!.requestExtraScopes()

            if (account!!.email!!.endsWith(context!!.getString(R.string.mentormate_suffix))) {
                AuthCredentials.instance.googleToken = account!!.serverAuthCode
                viewModel.insertGoogleToken(AuthCredentials.instance)
                authenticationNetworkBounRepo.fetchData()
            } else {
                googleSignInClient.signOut()
                Toast.makeText(context, R.string.not_authorized, Toast.LENGTH_LONG).show()
            }


        } catch (e: ApiException) {
            fireBaseDb.child(CRASH).child(account?.displayName.toString()).setValue(e.printStackTrace())
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN && resultCode == Activity.RESULT_OK) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleResult(task)

        }else{
            Toast.makeText(context, R.string.offline, Toast.LENGTH_LONG).show()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_login, container, false)
        signInButton = rootView.findViewById(R.id.sign_in_button)

        navController = activity!!.findNavController(R.id.login_fragment)
        FirebaseApp.initializeApp(this.context)
        mAuth = FirebaseAuth.getInstance()
        fireBaseDb = FirebaseDatabase.getInstance().reference
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(OAUTH_CLIENT_ID)
            .requestServerAuthCode(OAUTH_CLIENT_ID)
            .requestProfile()
            .requestEmail()
            .build()


        viewModel = ViewModelProviders.of(activity!!, viewModelFactory)[LoginFragmentViewModel::class.java]

        viewModel.getAuthData().observe(this, Observer { authCredentials ->
            if (authCredentials != null) {
                if (authCredentials.newToken!!.isNotEmpty()) {

                    navController.navigate(R.id.mainActivity)
                }
            }
        })


        signInButton.setOnClickListener { view: View? ->
            try {
                signInGoogle()
            } catch (e: Exception) {
                Toast.makeText(context, R.string.not_authorized, Toast.LENGTH_LONG).show()
            }
        }
        return rootView
    }

}


