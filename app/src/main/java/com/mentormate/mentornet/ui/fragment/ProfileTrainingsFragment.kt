package com.mentormate.mentornet.ui.fragment

import android.os.Bundle
import android.transition.Explode
import android.transition.Slide
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mentormate.mentornet.R
import com.mentormate.mentornet.adapter.retrofit.employee.CompleteEmployee
import com.mentormate.mentornet.data.trainings.CompleteTraining
import com.mentormate.mentornet.ui.adapter.TrainingsAdapter
import com.mentormate.mentornet.ui.utils.MarginItemDecoration
import com.mentormate.mentornet.ui.viewmodel.TrainingsFragmentViewModel
import com.mentormate.mentornet.utilities.EMPLOYEE_BUNDLE_KEY
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class ProfileTrainingsFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory


    lateinit var traingsAdapter: TrainingsAdapter

    lateinit var viewModel: TrainingsFragmentViewModel

    lateinit var navController: NavController

    private var currentTrainings: MutableList<CompleteTraining> = mutableListOf()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profile_trainings, container, false)
        navController = findNavController()
        setupTransitionAnimations()
        val clickedEmployee = arguments?.getSerializable(EMPLOYEE_BUNDLE_KEY) as CompleteEmployee

        traingsAdapter = TrainingsAdapter(this.context!!, object : TrainingsAdapter.onTrainingClick {
            override fun trainingOnClick(training: CompleteTraining, view: View) {
                val bundle = bundleOf(EMPLOYEE_BUNDLE_KEY to clickedEmployee)
                bundle.putSerializable(context?.getString(R.string.course_category_id), training.id)
                if (training.orderNumber == 4) bundle.putSerializable(
                    context?.getString(R.string.course_percentage),
                    currentTrainings[0].percentage
                )
                else bundle.putSerializable(
                        context?.getString(R.string.course_percentage),
                        currentTrainings[training.orderNumber].percentage)

                openDialog(bundle)
            }

        })
        viewModel = ViewModelProviders.of(
            activity!!,
            viewModelFactory
        )[TrainingsFragmentViewModel::class.java]

        //Setup RecyclerView
        val recyclerView: RecyclerView = view.findViewById(R.id.training_profile_recycler_view)
        recyclerView.setHasFixedSize(false)

        //Set ItemDecorator for RecycleView's margin
        recyclerView.addItemDecoration(
            MarginItemDecoration(
                resources.getDimension(R.dimen.default_padding).toInt(),
                resources.getDimension(R.dimen.default_padding).toInt(),
                resources.getDimension(R.dimen.large_padding).toInt(),
                resources.getDimension(R.dimen.large_padding).toInt()
            )
        )

        viewModel.getTrainings().observe(this, Observer { trainings ->

            trainings.mapTo(currentTrainings) {
                CompleteTraining(
                    it.courseIds,
                    it.id,
                    it.kingdom,
                    it.name,
                    it.orderNumber

                )
            }

            for (currentTraining in currentTrainings) {
                val listCourseIds: List<Int> = clickedEmployee.courseIds
                if (listCourseIds.isNotEmpty()) {
                    for (courseId in listCourseIds)
                        when {
                            currentTraining.courseIds.contains(courseId) ->
                                currentTraining.percentage += (100 / currentTraining.courseIds.size.toFloat())
                        }
                }
            }
            traingsAdapter.setItems(currentTrainings.toList())

        })

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = traingsAdapter
        getActivity()!!.setTitle(getString(R.string.trainings_string))
        return view
    }

    fun openDialog(bundle: Bundle) {
        val trainingsDialog = TrainingsDialog()
        trainingsDialog.arguments = bundle
        val manager = childFragmentManager
        trainingsDialog.show(manager, context?.getString(R.string.store_item_dialog))
    }


    override fun onPause() {
        currentTrainings = mutableListOf()
        super.onPause()
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
