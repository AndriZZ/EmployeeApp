package com.mentormate.mentornet.ui.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.transition.Explode
import android.transition.Slide
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mentormate.mentornet.R
import com.mentormate.mentornet.adapter.retrofit.employee.CompleteEmployee
import com.mentormate.mentornet.adapter.retrofit.employee.EmployeeService
import com.mentormate.mentornet.adapter.retrofit.leaderboard.FullRankListNetworkBoundRepository
import com.mentormate.mentornet.data.authentication.AuthCredentialsDao
import com.mentormate.mentornet.data.employee.CompleteEmployeeDao
import com.mentormate.mentornet.data.leaderboard.CompleteLeaderBoard
import com.mentormate.mentornet.ui.activity.LoginActivity
import com.mentormate.mentornet.ui.adapter.BoardsAdapter
import com.mentormate.mentornet.ui.adapter.BoardsLeadersAdapter
import com.mentormate.mentornet.ui.utils.MarginItemDecoration
import com.mentormate.mentornet.ui.viewmodel.BoardsViewModel
import com.mentormate.mentornet.utilities.AppExecutors
import com.mentormate.mentornet.utilities.EMPLOYEE_BUNDLE_KEY
import dagger.android.support.DaggerFragment
import de.hdodenhof.circleimageview.CircleImageView
import javax.inject.Inject


/**
 * Created by vasil.mitov@mentormate.com on 12/02/19.
 */

class BoardsFragment : DaggerFragment() {
    @Inject
    lateinit var employeeService: EmployeeService

    @Inject
    lateinit var appExecutors: AppExecutors

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var contex: Context

    lateinit var boardsAdapter: BoardsAdapter

    lateinit var boardsLeadersAdapter: BoardsLeadersAdapter

    @Inject
    lateinit var fullRankListNetworkBoundRepository: FullRankListNetworkBoundRepository

    @Inject
    lateinit var completeEmployeeDao: CompleteEmployeeDao

    @Inject
    lateinit var authCredentialsDao: AuthCredentialsDao

    lateinit var viewModel: BoardsViewModel

    private var allLeaderBoards: List<CompleteLeaderBoard> = listOf()

    lateinit var recyclerView: RecyclerView

    lateinit var navController: NavController


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_boards, container, false)
        getActivity()!!.setTitle(getString(R.string.boards_string))

        navController = findNavController()
        viewModel = ViewModelProviders.of(
            activity!!,
            viewModelFactory
        )[BoardsViewModel::class.java]

        boardsLeadersAdapter = BoardsLeadersAdapter(contex)

        boardsAdapter = BoardsAdapter(contex, object : BoardsLeadersAdapter.onClickLeader {
            override fun leaderOnClick(employee: CompleteEmployee, view: View) {
                navController.navigate(
                    R.id.action_global_profileFragment,
                    bundleOf(EMPLOYEE_BUNDLE_KEY to employee),
                    null,
                    FragmentNavigatorExtras(
                        view.findViewById<CircleImageView>(R.id.employee_avatar) to getString(R.string.leader_avatar_shared_transition_key),
                        view.findViewById<TextView>(R.id.employee_name) to getString(R.string.leader_name_shared_transition_key),
                        view.findViewById<TextView>(R.id.employee_position_in_city) to getString(R.string.leader_position_shared_transition_key)
                    )
                )
            }
        }, boardsLeadersAdapter)


        viewModel.completedLeaderBoards.observe(this, Observer { finalLeaderBoards ->
            allLeaderBoards = finalLeaderBoards
            if (finalLeaderBoards[0].leaders.size < 6 && boardsAdapter.listItems.isEmpty()) {
                fullRankListNetworkBoundRepository.setIdsToLoad(allLeaderBoards.map { it.id })

                fullRankListNetworkBoundRepository.fetchData()

            } else {
                for (leaderboard in finalLeaderBoards) {
                    if (leaderboard.leaders.size < 6) {
                        viewModel.completedLeaderBoards.removeObservers(this)
                        boardsAdapter.setItems(finalLeaderBoards)
                    } else {
                        boardsAdapter.setItems(finalLeaderBoards)
                    }
                }
            }


        })


        //Setup RecyclerView
        recyclerView = view.findViewById(R.id.leaderboards_recycler_view)
        recyclerView.setHasFixedSize(false)

        recyclerView.addItemDecoration(
            MarginItemDecoration(
                resources.getDimension(R.dimen.default_padding).toInt(),
                resources.getDimension(R.dimen.large_padding).toInt(),
                0,
                0
            )
        )


        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = boardsAdapter

        setupTransitionAnimations()



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
}