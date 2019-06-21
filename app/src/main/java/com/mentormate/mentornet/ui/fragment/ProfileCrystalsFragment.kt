package com.mentormate.mentornet.ui.fragment

import android.os.Bundle
import android.transition.Explode
import android.transition.Slide
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mentormate.mentornet.R
import com.mentormate.mentornet.adapter.retrofit.employee.CompleteEmployee
import com.mentormate.mentornet.data.store.GeneralItemType
import com.mentormate.mentornet.data.store.StoreItem
import com.mentormate.mentornet.data.store.TextStoreItem
import com.mentormate.mentornet.ui.adapter.StoreItemsAdapter
import com.mentormate.mentornet.ui.utils.MarginItemDecoration
import com.mentormate.mentornet.ui.viewmodel.StoreFragmentViewModel
import com.mentormate.mentornet.utilities.EMPLOYEE_BUNDLE_KEY
import dagger.android.support.DaggerFragment
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class ProfileCrystalsFragment : DaggerFragment() {


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var storeItemListAdapter: StoreItemsAdapter

    lateinit var viewModel: StoreFragmentViewModel

    lateinit var clickedListDisposable: Disposable

    private var currentStoreItems: List<StoreItem> = listOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profile_crystals, container, false)
        setupTransitionAnimations()

        viewModel = ViewModelProviders.of(
            activity!!,
            viewModelFactory
        )[StoreFragmentViewModel::class.java]

        val clickedEmployee = arguments?.getSerializable(EMPLOYEE_BUNDLE_KEY) as CompleteEmployee
        val recyclerView: RecyclerView = view.findViewById(R.id.store_list_recycler_view)
        recyclerView.setHasFixedSize(false)
        recyclerView.addItemDecoration(
            MarginItemDecoration(
                resources.getDimension(R.dimen.default_padding).toInt(),
                resources.getDimension(R.dimen.default_padding).toInt(),
                resources.getDimension(R.dimen.large_padding).toInt(),
                resources.getDimension(R.dimen.large_padding).toInt()
            )
        )
        viewModel.getStoreItemsLiveData().observe(this, Observer { storeItems ->
            val textItem = TextStoreItem(getString(R.string.desc_reach_for_orders))
            val crystalsAmount =
                TextStoreItem(clickedEmployee.crystalAmount.toString())
            currentStoreItems = storeItems
            val items = mutableListOf<GeneralItemType>()
            items.add(crystalsAmount)
            items.addAll(storeItems)
            storeItemListAdapter.setItems(items)


        })
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = storeItemListAdapter
        clickedListDisposable = storeItemListAdapter.clickEvent.subscribe { position ->
            if (position >= 0) {//you should not be able to click on the TextView
                val bundle: Bundle = Bundle()
                bundle.putString(context?.getString(R.string.name), currentStoreItems[position].name)
                bundle.putString(context?.getString(R.string.description), currentStoreItems[position].description)
                bundle.putString(context?.getString(R.string.image_name), currentStoreItems[position].imageName)
                bundle.putString(context?.getString(R.string.price), currentStoreItems[position].price.toString())
                bundle.putInt(getString(R.string.crystals_string), clickedEmployee.crystalAmount)
                openDialog(bundle)
            }
        }
        getActivity()!!.setTitle(getString(R.string.crystals_string))
        return view
    }

    fun openDialog(bundle: Bundle) {
        val storeItemDialog = StoreItemDialog()

        storeItemDialog.arguments = bundle
        val manager = childFragmentManager
        storeItemDialog.show(manager, context?.getString(R.string.store_item_dialog))
    }

    override fun onPause() {
        super.onPause()
        clickedListDisposable.dispose()
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
