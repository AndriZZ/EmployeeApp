package com.mentormate.mentornet.ui.fragment

import android.os.Bundle
import android.transition.Explode
import android.transition.Slide
import android.view.*
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mentormate.mentornet.R
import com.mentormate.mentornet.data.kudos.CompleteKudo
import com.mentormate.mentornet.data.store.GeneralItemType
import com.mentormate.mentornet.ui.adapter.GenericAdapter
import com.mentormate.mentornet.ui.adapter.KudosAdapter
import com.mentormate.mentornet.ui.utils.MarginItemDecoration
import com.mentormate.mentornet.ui.viewmodel.KudoListViewModel
import com.mentormate.mentornet.utilities.KUDO_BUNDLE_KEY
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class KudosFragment : DaggerFragment() {


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory


    lateinit var kudosAdapter: KudosAdapter

    lateinit var viewModel: KudoListViewModel

    lateinit var navController: NavController

    private var currentKudos: List<CompleteKudo> = listOf()

    /* lateinit var kudoButton: ImageButton*/

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_kudos, container, false)
        navController = findNavController()
        setHasOptionsMenu(true)


        viewModel = ViewModelProviders.of(
            activity!!,
            viewModelFactory
        )[KudoListViewModel::class.java]

        //Setup RecyclerView
        val recyclerView: RecyclerView = view.findViewById(R.id.kudo_list_recycler_view)
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



        kudosAdapter = KudosAdapter(this.context!!, object : KudosAdapter.onKudoLongPress {
            override fun KudoLongPress(completeKudo: CompleteKudo, view: View) {
                val bundle = bundleOf(KUDO_BUNDLE_KEY to completeKudo)
                val expandedKudo = ExpandedKudoDialog()
                expandedKudo.arguments = bundle
                val manager = childFragmentManager
                expandedKudo.show(manager, context?.getString(R.string.store_item_dialog))
            }

        })

        viewModel.getCompleteKudos()
            .observe(this, UpdateKudosObserver(kudosAdapter))

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = kudosAdapter

        setupTransitionAnimations()
        getActivity()!!.setTitle(getString(R.string.kudos_string))
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_kudos, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        KudosItemDialog().show(fragmentManager!!, getString(R.string.store_item_dialog))
        return super.onOptionsItemSelected(item)
    }

    private fun setupTransitionAnimations() {
        val slide = Slide()
        slide.duration = 350

        val explode = Explode()
        explode.duration = 500

        exitTransition = slide
        enterTransition = explode
    }

    inner class UpdateKudosObserver(private val kudoAdapter: GenericAdapter<GeneralItemType>) :
        Observer<List<CompleteKudo>> {
        override fun onChanged(kudos: List<CompleteKudo>?) {
            if (kudos != null && kudos.isNotEmpty()) {
                currentKudos = kudos
                kudoAdapter.setItems(currentKudos.toList())


            }
        }
    }
}