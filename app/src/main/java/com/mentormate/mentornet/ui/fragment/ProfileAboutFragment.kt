package com.mentormate.mentornet.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.transition.Explode
import android.transition.Slide
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.mentormate.mentornet.R
import com.mentormate.mentornet.adapter.retrofit.employee.CompleteEmployee
import com.mentormate.mentornet.ui.viewmodel.ProfileViewModel
import com.mentormate.mentornet.utilities.AppExecutors
import com.mentormate.mentornet.utilities.EMPLOYEE_BUNDLE_KEY
import com.mentormate.mentornet.utilities.EMPLOYEE_LOCATION
import dagger.android.support.DaggerFragment
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class ProfileAboutFragment : DaggerFragment() {
    @Inject
    lateinit var appExecutors: AppExecutors
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var viewModel: ProfileViewModel

    lateinit var navController: NavController

    lateinit var viewedEmployee: CompleteEmployee

    lateinit var employeeNamePosition: TextView

    lateinit var employeeEmail: TextView

    lateinit var deskLocation: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profile_about, container, false)
        navController = findNavController()

        viewModel = ViewModelProviders.of(
            activity!!,
            viewModelFactory
        )[ProfileViewModel::class.java]

        viewedEmployee = arguments?.getSerializable(EMPLOYEE_BUNDLE_KEY) as CompleteEmployee
        employeeNamePosition = view.findViewById(R.id.start_date)
        employeeEmail = view.findViewById(R.id.employee_email)
        deskLocation = view.findViewById(R.id.desk_location)

        deskLocation.setOnClickListener {
            deskRedirect(viewedEmployee.email)
        }
        val output = SimpleDateFormat(context?.getString(R.string.date_format_output_about), Locale.getDefault())
            .format(SimpleDateFormat(context?.getString(R.string.date_format_input_about), Locale.getDefault()).parse(viewedEmployee.startDate))
        employeeNamePosition.text = output
        employeeEmail.text = viewedEmployee.email

        setupTransitionAnimations()
        return view
    }

    fun deskRedirect(email: String) {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("$EMPLOYEE_LOCATION$email")
            )
        )
    }

    private fun setupTransitionAnimations() {
        val slide = Slide()
        slide.duration = 350

        val explode = Explode()
        explode.duration = 500

        exitTransition = slide
        enterTransition = explode
    }
}