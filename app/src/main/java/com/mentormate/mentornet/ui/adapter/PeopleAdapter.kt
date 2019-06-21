package com.mentormate.mentornet.ui.adapter

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.mentormate.mentornet.R
import com.mentormate.mentornet.adapter.retrofit.employee.CompleteEmployee
import com.mentormate.mentornet.data.employee.ClickedEmployeeView
import com.mentormate.mentornet.utilities.IMAGE_URL
import de.hdodenhof.circleimageview.CircleImageView
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

/**
 * Created by vasil.mitov@mentormate.com on 28/02/19.
 */

class PeopleAdapter @Inject constructor(
    private val employees: List<CompleteEmployee>,
    private val context: Context
) : GenericAdapter<CompleteEmployee>(employees) {

    private val clickEmployeeView = PublishSubject.create<ClickedEmployeeView>()
    //Exposing kudoAddressName clickЕvent observable that will be used by the subscribers
    val clickEvent: Observable<ClickedEmployeeView> = clickEmployeeView

    override fun getLayoutId(position: Int, obj: CompleteEmployee): Int {
        return R.layout.employee_list_layout
    }

    override fun getViewHolder(view: View, viewType: Int): RecyclerView.ViewHolder {
        return EmployeeListHolder(view)
    }

    inner class EmployeeListHolder(itemView: View) : RecyclerView.ViewHolder(itemView), Binder<CompleteEmployee> {
        private var avatar: CircleImageView = itemView.findViewById(R.id.employee_avatar)
        private var name: TextView = itemView.findViewById(R.id.employee_name)
        private var positionAtCity: TextView = itemView.findViewById(R.id.employee_position_in_city)
        private var crystals: TextView = itemView.findViewById(R.id.crystals)

        init {
            itemView.setOnClickListener {
                clickEmployeeView.onNext(
                    ClickedEmployeeView(
                        layoutPosition,
                        itemView
                    )
                )
            }
        }

        override fun bind(data: CompleteEmployee) {
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
}