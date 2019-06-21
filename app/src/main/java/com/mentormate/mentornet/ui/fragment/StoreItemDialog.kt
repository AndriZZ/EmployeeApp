package com.mentormate.mentornet.ui.fragment

import android.app.Dialog
import android.content.Intent
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import com.bumptech.glide.Glide
import com.mentormate.mentornet.R
import com.mentormate.mentornet.utilities.ATTLASIAN_URL
import com.mentormate.mentornet.utilities.STORE_URL


class StoreItemDialog : AppCompatDialogFragment() {

    private lateinit var itemImage: ImageView
    private lateinit var itemName: TextView
    private lateinit var itemPrice: TextView
    private lateinit var redirectAtlassian: Button
    private lateinit var itemCrystal: ImageView


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity!!)
        val inflater = activity!!.layoutInflater
        val view = inflater.inflate(R.layout.store_item_dialog, null)
        builder.setView(view)
        val bundle = this.arguments

        itemImage = view.findViewById(R.id.store_item_image)
        itemName = view.findViewById(R.id.item_name)
        redirectAtlassian = view.findViewById(R.id.button)
        itemPrice = view.findViewById(R.id.item_crystal_price)
        itemCrystal = view.findViewById(R.id.crystal)
        itemName.text = bundle?.getString(getString(R.string.name))
        itemPrice.text = bundle?.getString(getString(R.string.price))

        redirectAtlassian.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(ATTLASIAN_URL)))
        }

        Glide
            .with(this.context!!)
            .asBitmap()
            .load(R.drawable.crystal)
            .thumbnail()
            .into(itemCrystal)

        Glide
            .with(this.context!!)
            .asBitmap()
            .load(STORE_URL + bundle?.getString(getString(R.string.image_name)))
            .thumbnail()
            .into(itemImage)

        if (bundle?.getString(getString(R.string.price))?.toInt()!! > bundle.getInt(getString(R.string.crystals_string))) {
            val colorMatrix = ColorMatrix()
            colorMatrix.setSaturation(0f)
            val filter = ColorMatrixColorFilter(colorMatrix)
            itemCrystal.setColorFilter(filter)


        }
        return builder.create()
    }


}