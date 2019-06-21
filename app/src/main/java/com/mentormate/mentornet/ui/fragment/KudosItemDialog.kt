package com.mentormate.mentornet.ui.fragment

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.transition.Explode
import android.transition.Slide
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.mentormate.mentornet.R
import com.mentormate.mentornet.adapter.retrofit.employee.CompleteEmployee
import com.mentormate.mentornet.adapter.retrofit.kudos.KudoService
import com.mentormate.mentornet.data.authentication.AuthCredentialsDao
import com.mentormate.mentornet.data.kudos.KudoPost
import com.mentormate.mentornet.ui.adapter.DialogPeopleAdapter
import com.mentormate.mentornet.ui.viewmodel.EmployeeListViewModel
import com.mentormate.mentornet.ui.viewmodel.MainActivityViewModel
import com.mentormate.mentornet.utilities.AppExecutors
import dagger.android.support.DaggerAppCompatDialogFragment
import javax.inject.Inject


class KudosItemDialog : DaggerAppCompatDialogFragment(), SearchView.OnQueryTextListener {


    @Inject
    lateinit var kudoService: KudoService
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var googleSignInClient: GoogleSignInClient
    @Inject
    lateinit var authCredentialsDao: AuthCredentialsDao
    @Inject
    lateinit var appExecutors: AppExecutors

    lateinit var dialogPeopleAdapter: DialogPeopleAdapter

    lateinit var viewModel: EmployeeListViewModel

    lateinit var mainViewModel: MainActivityViewModel

    lateinit var searchView: SearchView

    lateinit var filteredEmployeeList: List<CompleteEmployee>

    lateinit var currentEmployees: List<CompleteEmployee>

    lateinit var recyclerView: RecyclerView

    private lateinit var kudoMessage: EditText

    private lateinit var postKudoButton: Button

    private var employeesToReceiveKudo: MutableList<CompleteEmployee> = mutableListOf()


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity!!)
        setupTransitionAnimations()
        val view = View.inflate(context, R.layout.kudos_add_dialog, null)
        recyclerView = view.findViewById<RecyclerView>(R.id.recycler_kudo_add)
        kudoMessage = view.findViewById(R.id.input_text)
        postKudoButton = view.findViewById(R.id.submit)

        postKudoButton.setOnClickListener { }

        viewModel = ViewModelProviders.of(
            activity!!,
            viewModelFactory
        )[EmployeeListViewModel::class.java]
        mainViewModel = ViewModelProviders.of(
            activity!!,
            viewModelFactory
        )[MainActivityViewModel::class.java]

        searchView = view.findViewById(R.id.searchView)
        searchView.setIconifiedByDefault(true)
        searchView.queryHint = getString(R.string.search_hint)
        searchView.setOnQueryTextListener(this)

        val searchViewText = searchView.findViewById<TextView>(androidx.appcompat.R.id.search_src_text)
        searchViewText.setTextColor(Color.BLACK)

        val searchViewImage: ImageView = searchView.findViewById(R.id.search_button)
        searchViewImage.setColorFilter(Color.BLACK)


        postKudoButton.setOnClickListener { KudoPostListener() }

        dialogPeopleAdapter = DialogPeopleAdapter(this.context!!, object : DialogPeopleAdapter.onClickEmployee {
            override fun employeeOnClick(employee: MutableList<CompleteEmployee>, view: View) {
                employeesToReceiveKudo = employee
            }
        })


        viewModel.getCompleteEmployee().observe(this, Observer { allEmployees ->
            currentEmployees = allEmployees
            filteredEmployeeList = allEmployees
            dialogPeopleAdapter.setItems(allEmployees)
        })

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = dialogPeopleAdapter
        builder.setView(view)
        return builder.create()
    }

    private fun KudoPostListener() {
        for (employee in employeesToReceiveKudo) {
            if (kudoMessage.text.isNotBlank()) {
                val kudoPosting = KudoPost(employee.id, kudoMessage.text.toString())
                appExecutors.networkIO.execute {
                    if ( mainViewModel.getConnectionStatus()) {
                        val response = kudoService.postKudo(kudoPosting).execute()
                        if (response.isSuccessful) {
                            appExecutors.mainThread.execute {
                                Toast.makeText(context, context?.getString(R.string.success_kudo), Toast.LENGTH_SHORT)
                                    .show()
                                mainViewModel.preloadData()
                            }
                        }
                    } else {
                        appExecutors.mainThread.execute {
                            Toast.makeText(context, context?.getString(R.string.failure_kudo), Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
        }
    }


    override fun onQueryTextChange(query: String?): Boolean {
        filteredEmployeeList = PeopleFragment().filter(currentEmployees, query!!.toLowerCase())
        dialogPeopleAdapter.setItems(filteredEmployeeList)
        YoYo.with(Techniques.Pulse).duration(300).playOn(recyclerView)
        dialogPeopleAdapter.notifyDataSetChanged()

        return true
    }


    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
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


}