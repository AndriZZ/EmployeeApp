package com.mentormate.mentornet.ui.adapter

import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mentormate.mentornet.R
import com.mentormate.mentornet.data.leaderboard.CompleteLeaderBoard


/**
 * Created by vasil.mitov@mentormate.com on 15/03/19.
 */

class BoardsAdapter constructor(
    private val context: Context,
    private var leader: BoardsLeadersAdapter.onClickLeader,
    private var boardsLeadersAdapter: BoardsLeadersAdapter
) : AdapterView.OnItemClickListener, GenericAdapter<CompleteLeaderBoard>() {

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
    }


    override fun getLayoutId(position: Int, obj: CompleteLeaderBoard): Int {

        return R.layout.employee_leaderboard_layout

    }

    override fun getViewHolder(view: View, viewType: Int): RecyclerView.ViewHolder {
        val recyclerView: RecyclerView = view.findViewById(R.id.employees_leaderboard_recycler)

        recyclerView.layoutManager = LinearLayoutManager(context) as RecyclerView.LayoutManager?
        boardsLeadersAdapter = BoardsLeadersAdapter(context, leader)
        recyclerView.adapter = boardsLeadersAdapter

        return LeaderBoardHolder(view, boardsLeadersAdapter)
    }


    inner class LeaderBoardHolder(
        itemView: View,
        private val adapter: BoardsLeadersAdapter

    ) : AdapterView.OnItemClickListener, RecyclerView.ViewHolder(itemView), Binder<CompleteLeaderBoard> {


        override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {


        }

        private val leaderBoardTitle: TextView = itemView.findViewById(R.id.leaderboard_title)
        private val viewSetting: Button = itemView.findViewById(R.id.view_all_button)
        private val topFive = 5
        private var currentAmountState = topFive

        private val viewMoreString = context.getString(R.string.view_more_string)
        private val viewLessString = context.getString(R.string.view_less_string)


        override fun bind(data: CompleteLeaderBoard) {
            leader = boardsLeadersAdapter.leader
            val initialTopEmployees = try {
                data.leaders.subList(0, topFive)
            } catch (e: java.lang.IndexOutOfBoundsException) {
                data.leaders
            }

            adapter.setItems(initialTopEmployees)


            val leaderBoardTitlePrefix = context.getString(R.string.leaderboards_prefix)
            val title = "$leaderBoardTitlePrefix ${initialTopEmployees.size} ${data.title}"
            leaderBoardTitle.text = title

            viewSetting.setOnClickListener {
                if (currentAmountState > topFive) {
                    try {
                        currentAmountState = topFive
                        val topFive = data.leaders.subList(0, topFive)
                        adapter.setItems(topFive)

                        val newTitle = "$leaderBoardTitlePrefix ${topFive.size} ${data.title}"
                        leaderBoardTitle.text = newTitle

                    } catch (e: IndexOutOfBoundsException) {
                        currentAmountState = data.leaders.size
                        adapter.setItems(data.leaders)

                        val newTitle = "$leaderBoardTitlePrefix ${data.leaders.size} ${data.title}"
                        leaderBoardTitle.text = newTitle
                    }

                    viewSetting.text = viewMoreString

                } else if (currentAmountState <= topFive) {
                    currentAmountState = data.leaders.size
                    adapter.setItems(data.leaders)

                    val newTitle = "$leaderBoardTitlePrefix ${data.leaders.size} ${data.title}"
                    leaderBoardTitle.text = newTitle
                    viewSetting.text = viewLessString
                }
            }
        }

    }

}

