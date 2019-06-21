package com.mentormate.mentornet.ui.adapter

import android.content.Context
import android.graphics.Color
import android.text.TextUtils.substring
import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.mentormate.mentornet.R
import com.mentormate.mentornet.data.trainings.CompleteTraining
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import javax.inject.Inject

class TrainingsAdapter(val context: Context, var training: onTrainingClick) : AdapterView.OnItemClickListener,
    GenericAdapter<CompleteTraining>() {

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {}

    override fun getLayoutId(position: Int, obj: CompleteTraining): Int {
        return R.layout.layout_training
    }

    override fun getViewHolder(view: View, viewType: Int): RecyclerView.ViewHolder {
        return TrainingsListHolder(view)
    }

    inner class TrainingsListHolder(itemView: View) : AdapterView.OnItemClickListener,
        RecyclerView.ViewHolder(itemView), Binder<CompleteTraining> {
        override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        }

        private var trainingImage: ImageView = itemView.findViewById(R.id.training_image)
        private var trainingName: TextView = itemView.findViewById(R.id.training_name)
        private var trainingKingdom: TextView = itemView.findViewById(R.id.training_kingdom)
        private var circleProgressBar: CircularProgressBar = itemView.findViewById(R.id.circularProgressBar)
        private var textPercentage: TextView = itemView.findViewById(R.id.textPercentage)
        private var kingdomIcon: Int = 0

        private var color2: Int = 0

        override fun bind(data: CompleteTraining) {

            itemView.setOnClickListener {
                training.trainingOnClick(data, itemView)

            }
            when {
                data.kingdom.name.toLowerCase().contains("water") -> kingdomCirclePopulate(
                    R.color.fadedBlue,
                    R.drawable.ic_water
                )
                data.kingdom.name.toLowerCase().contains("air") -> kingdomCirclePopulate(
                    R.color.fadedPurple,
                    R.drawable.ic_air
                )
                data.kingdom.name.toLowerCase().contains("earth") -> kingdomCirclePopulate(
                    R.color.fadedYellow,
                    R.drawable.ic_earth
                )
                data.kingdom.name.toLowerCase().contains("fire") -> kingdomCirclePopulate(
                    R.color.fadedRed,
                    R.drawable.ic_fire
                )
            }
            Glide
                .with(context)
                .asBitmap()
                .load(kingdomIcon)
                .thumbnail()
                .into(trainingImage)
            trainingImage.setBackgroundColor(context.getColor(color2))
            this.textPercentage.text =
                "${substring(data.percentage.toString(), 0, data.percentage.toString().length - 2)}%"
            this.circleProgressBar.progress = data.percentage

            this.trainingKingdom.setTextColor(ContextCompat.getColor(context, color2))
            this.circleProgressBar.color = ContextCompat.getColor(context, color2)
            this.trainingName.text = data.name
            this.trainingKingdom.text = data.kingdom.name


            YoYo.with(Techniques.FadeIn).duration(500).playOn(itemView)
        }

        private fun kingdomCirclePopulate(color: Int, kingdomIcon: Int) {
            this.color2 = color
            this.kingdomIcon = kingdomIcon
        }
    }
    interface onTrainingClick {

        fun trainingOnClick(training: CompleteTraining, view: View)

    }


}