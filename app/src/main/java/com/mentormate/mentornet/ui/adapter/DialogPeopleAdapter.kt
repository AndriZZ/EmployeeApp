package com.mentormate.mentornet.ui.adapter

import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.mentormate.mentornet.R
import com.mentormate.mentornet.adapter.retrofit.employee.CompleteEmployee
import com.mentormate.mentornet.utilities.IMAGE_URL
import de.hdodenhof.circleimageview.CircleImageView


class DialogPeopleAdapter constructor(
    private val context: Context,
    private val employee: onClickEmployee
) : GenericAdapter<CompleteEmployee>() {
    var clickedEmployees: MutableList<CompleteEmployee> = mutableListOf()

    override fun getLayoutId(position: Int, obj: CompleteEmployee): Int {
        return R.layout.employee_list_layout_dialog
    }

    override fun getViewHolder(view: View, viewType: Int): RecyclerView.ViewHolder {
        return EmployeeListHolder(view)
    }

    inner class EmployeeListHolder(itemView: View) : AdapterView.OnItemClickListener, RecyclerView.ViewHolder(itemView),
        Binder<CompleteEmployee> {
        override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        }

        private var avatar: CircleImageView = itemView.findViewById(R.id.employee_avatar)
        private var name: TextView = itemView.findViewById(R.id.employee_name)
        private var positionAtCity: TextView = itemView.findViewById(R.id.employee_position_in_city)
        private var checkBox: CheckBox = itemView.findViewById(R.id.checkBox)


        override fun bind(data: CompleteEmployee) {
            checkBox.isChecked = false

            when {
                clickedEmployees.find { alreadyMarked -> alreadyMarked.id == data.id } != null -> checkBox.isChecked = true
            }
            itemView.setOnClickListener {
                checkBox.performClick()

            }
            checkBox.setOnClickListener {
                clickedEmployees = clickedEmployees.distinct().toMutableList()
                if (checkBox.isChecked) {
                    clickedEmployees.add(data)
                    employee.employeeOnClick(clickedEmployees, itemView)
                }
                if (!checkBox.isChecked) {
                    clickedEmployees.remove(data)
                }
            }
            Glide
                .with(context)
                .asBitmap()
                .load("$IMAGE_URL${data.imageUrl}")
                .thumbnail()
                .into(avatar)
            if (checkBox.isChecked) {
                clickedEmployees.add(data)
            }

            val displayName = "${data.firstName} ${data.lastName}"
            val positionAtCity = "${data.position}, ${data.city}"

            this.name.text = displayName
            this.positionAtCity.text = positionAtCity


            YoYo.with(Techniques.FadeIn).duration(500).playOn(itemView)
        }

    }

    interface onClickEmployee {

        fun employeeOnClick(employee: MutableList<CompleteEmployee>, view: View)

    }
}