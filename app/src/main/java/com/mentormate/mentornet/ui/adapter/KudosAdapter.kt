package com.mentormate.mentornet.ui.adapter

import android.content.Context
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.mentormate.mentornet.R
import com.mentormate.mentornet.data.kudos.CompleteKudo
import com.mentormate.mentornet.data.store.GeneralItemType
import com.mentormate.mentornet.data.store.TextStoreItem
import com.mentormate.mentornet.data.trainings.CompleteTraining
import com.mentormate.mentornet.ui.fragment.KudosItemDialog
import com.mentormate.mentornet.ui.fragment.TrainingsDialog
import com.mentormate.mentornet.utilities.IMAGE_URL
import de.hdodenhof.circleimageview.CircleImageView
import java.text.SimpleDateFormat
import javax.inject.Inject


class KudosAdapter constructor(
    private val context: Context,
    var kudoLongPress: onKudoLongPress
) : GenericAdapter<GeneralItemType>() {
    private var prefixText = context.getString(R.string.To)
    private var flag = false

    lateinit var kudoAddressName: String
    lateinit var viewHolder: RecyclerView.ViewHolder
    override fun getLayoutId(position: Int, obj: GeneralItemType): Int {
        if (obj is TextStoreItem) {
            return R.layout.textview_holder
        } else {
            return R.layout.layout_kudo
        }
    }

    override fun getViewHolder(view: View, viewType: Int): RecyclerView.ViewHolder {

        if (view is CardView) {
            viewHolder = KudoListHolder(view)
        } else {
            if (flag) prefixText = changePrefix(prefixText)
            else {
                prefixText = changePrefix(prefixText)
            }
            viewHolder = TextItemViewHolder(view)
        }
        return viewHolder
    }

    private fun changePrefix(text: String): String {
        when {
            flag -> flag = false
            flag == false -> flag = true
        }
        if (text.equals(context.getString(R.string.To))) return context.getString(R.string.By)
        else {
            return context.getString(R.string.To)
        }
    }

    inner class KudoListHolder(itemView: View) : RecyclerView.ViewHolder(itemView), Binder<CompleteKudo> {
        private var avatar: CircleImageView = itemView.findViewById(R.id.kudo_circleImage)
        private var kudoText: TextView = itemView.findViewById(R.id.kudo_comment)
        private var kudoDetail: TextView = itemView.findViewById(R.id.kudo_comment_details)


        override fun bind(data: CompleteKudo) {
            itemView.setOnLongClickListener {
                kudoLongPress.KudoLongPress(data, itemView)
                return@setOnLongClickListener true
            }
            Glide
                .with(context)
                .asBitmap()
                .load("$IMAGE_URL${data.imageUrl}")
                .thumbnail()
                .into(avatar)
            data.creationDate
            val output = SimpleDateFormat(context.getString(R.string.date_format_output))
                .format(SimpleDateFormat(context.getString(R.string.date_format_input)).parse(data.creationDate))
            this.kudoText.text = data.message
            if (prefixText.equals(context.getString(R.string.By))) {

                kudoAddressName = "$prefixText ${data.employeeFromName} - $output"
            } else {

                kudoAddressName = "$prefixText ${data.employeeToName} - $output"
            }
            this.kudoDetail.text = kudoAddressName

            YoYo.with(Techniques.FadeIn).duration(500).playOn(itemView)
        }
    }

    inner class TextItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), Binder<TextStoreItem> {
        private var desc: TextView = itemView.findViewById(R.id.store_desc)
        override fun bind(data: TextStoreItem) {
            this.desc.text = data.textValue

            YoYo.with(Techniques.FadeIn).duration(500).playOn(itemView)
        }
    }
    interface onKudoLongPress {

        fun KudoLongPress(completeKudo: CompleteKudo, view: View)

    }
}