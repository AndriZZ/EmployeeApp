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
import com.mentormate.mentornet.data.kudos.CompleteKudo
import com.mentormate.mentornet.data.store.GeneralItemType
import com.mentormate.mentornet.data.store.TextStoreItem
import com.mentormate.mentornet.ui.adapter.KudosAdapter
import com.mentormate.mentornet.ui.utils.MarginItemDecoration
import com.mentormate.mentornet.ui.viewmodel.KudoListViewModel
import com.mentormate.mentornet.ui.viewmodel.ProfileViewModel
import com.mentormate.mentornet.utilities.EMPLOYEE_BUNDLE_KEY
import com.mentormate.mentornet.utilities.KUDO_BUNDLE_KEY
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class ProfileKudosFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory


    lateinit var kudosAdapter: KudosAdapter

    lateinit var viewModel: KudoListViewModel

    lateinit var navController: NavController
    @Inject
    lateinit var profileViewModel: ProfileViewModel

    var currentKudos: MutableList<GeneralItemType> = mutableListOf()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profile_kudos, container, false)
        navController = findNavController()
        setupTransitionAnimations()


        viewModel = ViewModelProviders.of(
            activity!!,
            viewModelFactory
        )[KudoListViewModel::class.java]

        //Setup RecyclerView
        val recyclerView: RecyclerView = view.findViewById(R.id.kudos_profile_recycler_view)
        recyclerView.setHasFixedSize(false)

        //Set ItemDecorator for RecycleView's margin
        recyclerView.addItemDecoration(
            MarginItemDecoration(
                resources.getDimension(R.dimen.default_padding).toInt(),
                resources.getDimension(R.dimen.default_padding).toInt(),
                resources.getDimension(R.dimen.default_padding).toInt(),
                resources.getDimension(R.dimen.default_padding).toInt()
            )
        )
        profileViewModel = ViewModelProviders.of(
            activity!!,
            viewModelFactory
        )[ProfileViewModel::class.java]
        val clickedEmployee = arguments?.getSerializable(EMPLOYEE_BUNDLE_KEY) as CompleteEmployee

        kudosAdapter = KudosAdapter(this.context!!, object : KudosAdapter.onKudoLongPress {
            override fun KudoLongPress(completeKudo: CompleteKudo, view: View) {
                val bundle = bundleOf(KUDO_BUNDLE_KEY to completeKudo)
                val expandedKudo =ExpandedKudoDialog()
                expandedKudo.arguments = bundle
                val manager = childFragmentManager
                expandedKudo.show(manager, context?.getString(R.string.store_item_dialog))
            }

        })
        viewModel.getCompleteKudoForId(clickedEmployee.id)
            .observe(this, Observer { kudos ->
                var textItem = TextStoreItem(context!!.getString(R.string.kudos_for_you))
                if (kudos.isEmpty()) textItem = TextStoreItem(context!!.getString(R.string.no_kudos_yet))
                currentKudos.add(textItem)
                currentKudos.addAll(kudos)
                viewModel.getCompleteKudoFromId(clickedEmployee.id)
                    .observe(this, Observer { kudosFromId ->
                        var textItem = TextStoreItem(context!!.getString(R.string.kudos_by_you))
                        if (kudosFromId.isEmpty()) textItem = TextStoreItem(getString(R.string.no_written_kudos_yet))
                        currentKudos.add(textItem)
                        currentKudos.addAll(kudosFromId.sortedByDescending { it.creationDate})
                        kudosAdapter.setItems(currentKudos.toList())
                    })
            })

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = kudosAdapter
        getActivity()!!.setTitle(getString(R.string.kudos_string))
        return view
    }


    private fun setupTransitionAnimations() {
        val slide = Slide()
        slide.duration = 350

        val explode = Explode()
        explode.duration = 500

        exitTransition = slide
        enterTransition = explode
    }

    override fun onPause() {
        currentKudos = mutableListOf()
        super.onPause()
    }

}