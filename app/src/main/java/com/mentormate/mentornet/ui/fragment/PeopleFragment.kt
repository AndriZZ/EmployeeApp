package com.mentormate.mentornet.ui.fragment

import android.os.Bundle
import android.transition.Explode
import android.transition.Slide
import android.view.*
import android.widget.EditText
import android.widget.SearchView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.mentormate.mentornet.R
import com.mentormate.mentornet.adapter.retrofit.employee.CompleteEmployee
import com.mentormate.mentornet.data.employee.ClickedEmployeeView
import com.mentormate.mentornet.ui.adapter.GenericAdapter
import com.mentormate.mentornet.ui.adapter.PeopleAdapter
import com.mentormate.mentornet.ui.utils.MarginItemDecoration
import com.mentormate.mentornet.ui.viewmodel.EmployeeListViewModel
import com.mentormate.mentornet.utilities.EMPLOYEE_BUNDLE_KEY
import com.mentormate.mentornet.utilities.SEARCHVIEW_ID
import dagger.android.support.DaggerFragment
import de.hdodenhof.circleimageview.CircleImageView
import io.reactivex.disposables.Disposable
import javax.inject.Inject


class PeopleFragment : DaggerFragment(), SearchView.OnQueryTextListener {


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var peopleAdapter: PeopleAdapter

    lateinit var viewModel: EmployeeListViewModel

    lateinit var employeeListDisposable: Disposable

    lateinit var navController: NavController

    lateinit var currentEmployees: List<CompleteEmployee>

    lateinit var searchView: SearchView

    lateinit var recyclerView: RecyclerView

    lateinit var filteredEmployeeList: List<CompleteEmployee>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_employee_list, container, false)
        navController = findNavController()
        setupTransitionAnimations()
        setHasOptionsMenu(true)

        viewModel = ViewModelProviders.of(
            activity!!,
            viewModelFactory
        )[EmployeeListViewModel::class.java]
        //Setup RecyclerView
        recyclerView = view.findViewById(R.id.employee_list_recycler_view)
        recyclerView.setHasFixedSize(false)

        val a = getString(R.string.app_name)
        getActivity()!!.setTitle(getString(R.string.people_string))


        //Set ItemDecorator for RecycleView's margin
        recyclerView.addItemDecoration(
            MarginItemDecoration(
                resources.getDimension(R.dimen.default_padding).toInt(),
                resources.getDimension(R.dimen.default_padding).toInt(),
                resources.getDimension(R.dimen.default_padding).toInt(),
                resources.getDimension(R.dimen.default_padding).toInt()
            )
        )

        //Observe for employee changes and apply them in the adapter
        viewModel.getCompleteEmployee()
            .observe(this, UpdateEmployeesObserver(peopleAdapter))

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = peopleAdapter

        //Setup the click listener
        employeeListDisposable = peopleAdapter.clickEvent.subscribe {
            setupNavigation(
                it,
                FragmentNavigatorExtras(
                    it.thisView.findViewById<CircleImageView>(R.id.employee_avatar) to getString(R.string.employee_avatar_shared_transition_key),
                    it.thisView.findViewById<TextView>(R.id.employee_name) to getString(R.string.employee_name_shared_transition_key),
                    it.thisView.findViewById<TextView>(R.id.employee_position_in_city) to getString(R.string.employee_position_shared_transition_key)
                )
            )
        }

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.menu, menu)


        val searchItem: MenuItem = menu.findItem(R.id.search)
        searchView = searchItem.actionView as SearchView
        searchView.setIconifiedByDefault(true)
        searchView.setOnQueryTextListener(this)
        searchView.queryHint = getString(R.string.search_hint)

        val searchviewIdentifier = searchView.context.resources.getIdentifier(SEARCHVIEW_ID, null, null)
        val searchviewEditText: EditText = searchView.findViewById(searchviewIdentifier)
        searchviewEditText.setHintTextColor(ContextCompat.getColor(this.context!!, R.color.fadedWhite))

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onQueryTextChange(query: String): Boolean {
        filteredEmployeeList = filter(currentEmployees, query.toLowerCase())
        peopleAdapter.setItems(filteredEmployeeList)
        YoYo.with(Techniques.Pulse).duration(300).playOn(recyclerView)
        peopleAdapter.notifyDataSetChanged()

        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    fun filter(employees: List<CompleteEmployee>, query: String): List<CompleteEmployee> {
        val filteredModelList: MutableList<CompleteEmployee> = ArrayList()
        for (employee in employees) {
            when {
                employee.firstName.toLowerCase().contains(query) -> filteredModelList.add(employee)
                employee.lastName.toLowerCase().contains(query) -> filteredModelList.add(employee)
                "${employee.firstName} ${employee.lastName}".toLowerCase().contains(query) -> filteredModelList.add(
                    employee
                )
                employee.city.toLowerCase().contains(query) -> filteredModelList.add(employee)
                employee.position.toLowerCase().contains(query) -> filteredModelList.add(employee)
            }
        }
        return filteredModelList.toList()
    }

    override fun onPause() {
        super.onPause()
        employeeListDisposable.dispose()
    }

    private fun setupNavigation(clickedEmployeeView: ClickedEmployeeView, transitionExtras: FragmentNavigator.Extras) {
        navController.navigate(

            R.id.action_global_profileFragment,
            bundleOf(EMPLOYEE_BUNDLE_KEY to filteredEmployeeList[clickedEmployeeView.employeePosition]),
            null,
            transitionExtras
        )
        searchView.setQuery("", false)
    }

    private fun setupTransitionAnimations() {
        val slide = Slide()
        slide.duration = 350

        val explode = Explode()
        explode.duration = 500

        exitTransition = slide
        enterTransition = explode
    }

    inner class UpdateEmployeesObserver(private val employeeAdapter: GenericAdapter<CompleteEmployee>) :
        Observer<List<CompleteEmployee>> {
        override fun onChanged(employees: List<CompleteEmployee>?) {
            if (employees != null && employees.isNotEmpty()) {
                currentEmployees = employees
                filteredEmployeeList = employees
                employeeAdapter.setItems(employees)
            }
        }
    }
}

