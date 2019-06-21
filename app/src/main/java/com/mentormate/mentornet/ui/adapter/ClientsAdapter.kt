package com.mentormate.mentornet.ui.adapter

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.mentormate.mentornet.R
import com.mentormate.mentornet.data.employee.Client


class ClientsAdapter : GenericAdapter<Client>() {

    override fun getLayoutId(position: Int, obj: Client): Int {

        return R.layout.clients_list_holder
    }

    override fun getViewHolder(view: View, viewType: Int): RecyclerView.ViewHolder {
        return ClientsListHolder(view)
    }

    inner class ClientsListHolder(itemView: View) : RecyclerView.ViewHolder(itemView), Binder<Client> {
        private var trainingName: TextView = itemView.findViewById(R.id.client_name)

        override fun bind(data: Client) {

            if (data.id % 2 == 1) {
                itemView.setBackgroundColor(Color.WHITE)
            }
            this.trainingName.text = data.name
            YoYo.with(Techniques.FadeIn).duration(500).playOn(itemView)
        }
    }

}