package com.mentormate.mentornet.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.transition.Explode
import android.transition.Slide
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout
import com.mentormate.mentornet.R
import com.mentormate.mentornet.adapter.retrofit.employee.CompleteEmployee
import com.mentormate.mentornet.ui.utils.AppBarStateChangeListener
import com.mentormate.mentornet.ui.viewmodel.ProfileViewModel
import com.mentormate.mentornet.utilities.AppExecutors
import com.mentormate.mentornet.utilities.EMPLOYEE_BUNDLE_KEY
import com.mentormate.mentornet.utilities.IMAGE_URL
import dagger.android.support.DaggerFragment
import de.hdodenhof.circleimageview.CircleImageView
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * Created by vasil.mitov@mentormate.com on 12/02/19.
 */

class ProfileFragment : DaggerFragment() {


    lateinit var employeeListDisposable: Disposable

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var profileViewModel: ProfileViewModel

    @Inject
    lateinit var profileClientsFragment: ProfileClientsFragment

    @Inject
    lateinit var profileKudosFragment: ProfileKudosFragment

    @Inject
    lateinit var profileTrainingsFragment: ProfileTrainingsFragment

    @Inject
    lateinit var profileCrystalsFragment: ProfileCrystalsFragment

    @Inject
    lateinit var profileAboutFragment: ProfileAboutFragment
    @Inject
    lateinit var appExecutors: AppExecutors

    var urlToFeedback: String = ""

    private var viewedEmployee: CompleteEmployee = CompleteEmployee()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        //Setup toolbar visibility listener
        val appBarLayout = view.findViewById<AppBarLayout>(R.id.profile_bar_layout)
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        val tabLayout = view.findViewById<TabLayout>(R.id.profile_nav_tab)
        val viewPager = view.findViewById<ViewPager>(R.id.profile_view_pager)
        val feedbackImage = view.findViewById<ImageView>(R.id.feedback_image)
        val feedbackImageSpread = view.findViewById<ImageView>(R.id.feedback_image_spread)

        //If we came here form list employees fragment
        val clickedEmployee = arguments?.getSerializable(EMPLOYEE_BUNDLE_KEY)

        if (clickedEmployee != null) {
            clickedEmployee as CompleteEmployee
            viewedEmployee = clickedEmployee

            bindView(clickedEmployee, view)
        } else {
            appExecutors.diskIO.execute {
                viewedEmployee = profileViewModel.getCurrentEmployee()
                appExecutors.mainThread.execute {
                    bindView(viewedEmployee, view)
                }
            }
        }

        appBarLayout.addOnOffsetChangedListener(object : AppBarStateChangeListener() {
            override fun onStateChanged(appBarLayout: AppBarLayout, state: State) {
                when (state) {
                    State.EXPANDED -> toolbar.visibility = View.INVISIBLE
                    State.COLLAPSED -> toolbar.visibility = View.VISIBLE
                    else -> toolbar.visibility = View.INVISIBLE
                }
            }
        })

        profileViewModel = ViewModelProviders.of(
            activity!!,
            viewModelFactory
        )[ProfileViewModel::class.java]


        tabLayout.tabGravity = TabLayout.GRAVITY_FILL
        tabLayout.setupWithViewPager(viewPager)
        viewPager.adapter = PagerAdapter(childFragmentManager, 5)

        feedbackImage.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(urlToFeedback)))
        }
        feedbackImageSpread.setOnClickListener {

            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(urlToFeedback)))
        }
        setupTransitionAnimations()


        return view

    }

    private fun setupTransitionAnimations() {
        val slide = Slide()
        slide.duration = 500

        val explode = Explode()
        explode.duration = 500

        exitTransition = slide
        enterTransition = explode

        sharedElementEnterTransition = slide
        sharedElementReturnTransition = slide
    }

    private fun bindView(employee: CompleteEmployee, view: View) {
        val employeeName = view.findViewById<TextView>(R.id.employee_name)
        val titleEmployeeName = view.findViewById<TextView>(R.id.title_employee_name)

        val avatar = view.findViewById<CircleImageView>(R.id.avatar)
        val smallAvatar = view.findViewById<CircleImageView>(R.id.small_avatar)
        val position = view.findViewById<TextView>(R.id.position)
        val crystalsAmount = view.findViewById<TextView>(R.id.crystals_amount)
        val employeeFullName = "${employee.firstName} ${employee.lastName}"

        employeeName.text = employeeFullName
        titleEmployeeName.text = employeeFullName
        position.text = employee.position
        crystalsAmount.text = "${employee.crystalAmount}"

        //Glide keeps cash with image url as key, so this will automatically load the cached image.
        //If we expect images to change frequently we should use skipMemoryCache(true)
        //or share the image between fragments.
        Glide
            .with(context!!)
            .asBitmap()
            .load("$IMAGE_URL${employee.imageUrl}")
            .thumbnail()
            .into(avatar)

        Glide
            .with(context!!)
            .asBitmap()
            .load("$IMAGE_URL${employee.imageUrl}")
            .thumbnail()
            .into(smallAvatar)
    }

    inner class PagerAdapter(
        fragmentManager: FragmentManager,
        private val numberOfTabs: Int
    ) :
        FragmentStatePagerAdapter(fragmentManager) {
        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> setArguments(profileAboutFragment)
                1 -> setArguments(profileTrainingsFragment)
                2 -> setArguments(profileKudosFragment)
                3 -> setArguments(profileClientsFragment)
                4 -> setArguments(profileCrystalsFragment)
                else -> setArguments(profileAboutFragment)
            }
        }

        override fun getCount(): Int {
            return numberOfTabs
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return when (position) {
                0 -> getString(R.string.about_string)
                1 -> getString(R.string.trainings_string)
                2 -> getString(R.string.kudos_string)
                3 -> getString(R.string.clients_string)
                4 -> getString(R.string.crystals_string)
                else -> getString(R.string.about_string)
            }
        }

        private fun setArguments(fragment: Fragment): Fragment {
            urlToFeedback = viewedEmployee.feedbackForm
            fragment.arguments = bundleOf(EMPLOYEE_BUNDLE_KEY to viewedEmployee)
            return fragment
        }
    }
}
