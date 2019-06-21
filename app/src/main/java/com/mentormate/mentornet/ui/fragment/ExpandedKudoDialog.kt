package com.mentormate.mentornet.ui.fragment

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import com.bumptech.glide.Glide
import com.mentormate.mentornet.R
import com.mentormate.mentornet.adapter.retrofit.employee.CompleteEmployee
import com.mentormate.mentornet.data.kudos.CompleteKudo
import com.mentormate.mentornet.utilities.*
import dagger.android.support.DaggerAppCompatDialogFragment
import de.hdodenhof.circleimageview.CircleImageView
import java.text.SimpleDateFormat
import javax.inject.Inject


class ExpandedKudoDialog : AppCompatDialogFragment() {

    private lateinit var avatar: CircleImageView
    private lateinit var kudoText: EditText
    private lateinit var kudoDetail: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog?.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity!!)
        val inflater = activity!!.layoutInflater
        val view = inflater.inflate(R.layout.layout_expanded_kudo, null)
        avatar = view.findViewById(R.id.kudo_circleImage)
        kudoText = view.findViewById(R.id.kudo_comment)
        kudoDetail = view.findViewById(R.id.kudo_comment_details)
        builder.setView(view)
        val clickedKudo = arguments?.getSerializable(KUDO_BUNDLE_KEY) as CompleteKudo

        Glide
            .with(context!!)
            .asBitmap()
            .load("$IMAGE_URL${clickedKudo.imageUrl}")
            .thumbnail()
            .into(avatar)
        val output = SimpleDateFormat(context!!.getString(R.string.date_format_output))
            .format(SimpleDateFormat(context!!.getString(R.string.date_format_input)).parse(clickedKudo.creationDate))
        this.kudoText.setText(clickedKudo.message)
        this.kudoDetail.text = "${context!!.getString(R.string.By)} ${clickedKudo.employeeFromName} - $output"

        kudoText.setKeyListener(null)
        kudoText.setTextIsSelectable(true)

        return builder.create()
    }


}