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
import com.mentormate.mentornet.data.employee.Client
import com.mentormate.mentornet.ui.adapter.ClientsAdapter
import com.mentormate.mentornet.ui.viewmodel.ClientsListViewModel
import com.mentormate.mentornet.utilities.EMPLOYEE_BUNDLE_KEY
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class ProfileClientsFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var clientsAdapter: ClientsAdapter

    lateinit var viewModel: ClientsListViewModel

    lateinit var clients: List<Client>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profile_clients, container, false)
        viewModel = ViewModelProviders.of(
            activity!!,
            viewModelFactory
        )[ClientsListViewModel::class.java]

        val recyclerView: RecyclerView = view!!.findViewById(R.id.clients_list_recycler_view)
        val clickedEmployee = arguments?.getSerializable(EMPLOYEE_BUNDLE_KEY) as CompleteEmployee
        viewModel.getEmployeeClients(clickedEmployee.id)
            .observe(this, Observer {
                clients = addClientPositions(it)
                if (clients.isEmpty()) {
                    clientsAdapter.setItems(listOf(Client(0, (getString(R.string.no_clients_yet)))))
                } else {
                    clientsAdapter.setItems(clients)
                }
            })

        recyclerView.layoutManager = LinearLayoutManager(context) as RecyclerView.LayoutManager?
        recyclerView.adapter = clientsAdapter
        setupTransitionAnimations()
        return view
    }

    private fun addClientPositions(clients: List<Client>): List<Client> {
        var position = 1
        for (client in clients) {
            client.id = position
            position++
        }
        return clients
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