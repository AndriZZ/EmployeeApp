package com.mentormate.mentornet.ui.adapter

import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.mentormate.mentornet.R
import com.mentormate.mentornet.adapter.retrofit.employee.CompleteEmployee
import com.mentormate.mentornet.utilities.IMAGE_URL
import de.hdodenhof.circleimageview.CircleImageView
import javax.inject.Inject


class BoardsLeadersAdapter @Inject constructor(
    private val context: Context
) : GenericAdapter<CompleteEmployee>() {
    constructor(context1: Context, leader: onClickLeader) : this(context1) {
        this.context1 = context
        this.leader = leader
    }

    lateinit var leader: onClickLeader
    private lateinit var context1: Context

    override fun getLayoutId(position: Int, obj: CompleteEmployee): Int {

        return R.layout.employee_leaderboard_list_layout
    }

    override fun getViewHolder(view: View, viewType: Int): RecyclerView.ViewHolder {
        return BoardsEmployeeHolder(view)
    }

    inner class BoardsEmployeeHolder(itemView: View) : AdapterView.OnItemClickListener,
        RecyclerView.ViewHolder(itemView), Binder<CompleteEmployee> {
        override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        }

        private var avatar: CircleImageView = itemView.findViewById(R.id.employee_avatar)
        private var name: TextView = itemView.findViewById(R.id.employee_name)
        private val position: TextView = itemView.findViewById(R.id.employee_leader_board_position)
        private var positionAtCity: TextView = itemView.findViewById(R.id.employee_position_in_city)
        private var crystals: TextView = itemView.findViewById(R.id.crystals)

        override fun bind(data: CompleteEmployee) {

            itemView.setOnClickListener {
                leader.leaderOnClick(data, itemView)

            }
            position.text = (layoutPosition + 1).toString()
            Glide
                .with(context)
                .asBitmap()
                .load("$IMAGE_URL${data.imageUrl}")
                .thumbnail()
                .into(avatar)

            val displayName = "${data.firstName} ${data.lastName}"
            val positionAtCity = "${data.position}, ${data.city}"

            this.name.text = displayName
            this.positionAtCity.text = positionAtCity
            this.crystals.text = "${data.crystalAmount}"

            YoYo.with(Techniques.FadeIn).duration(500).playOn(itemView)
        }
    }

    interface onClickLeader {

        fun leaderOnClick(employee: CompleteEmployee, view: View)

    }
}