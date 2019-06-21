package com.mentormate.mentornet.ui.adapter

import android.content.Context
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.mentormate.mentornet.R
import com.mentormate.mentornet.data.store.GeneralItemType
import com.mentormate.mentornet.data.store.StoreItem
import com.mentormate.mentornet.data.store.TextStoreItem
import com.mentormate.mentornet.utilities.STORE_URL
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject


class StoreItemsAdapter
@Inject constructor(
    storeStoreItemTypes: List<GeneralItemType>,
    private val context: Context

) : GenericAdapter<GeneralItemType>(storeStoreItemTypes) {
    override fun getLayoutId(position: Int, obj: GeneralItemType): Int {
        if (obj is TextStoreItem) {
            return R.layout.textview_holder
        } else {
            return R.layout.cadview_store_items
        }
    }

    var crystals: Int = 0
    private val clickedPosition = PublishSubject.create<Int>()
    //Exposing kudoAddressName clickЕvent observable that will be used by the subscribers
    val clickEvent: Observable<Int> = clickedPosition

    override fun getViewHolder(
        view: View,
        viewType: Int
    ): RecyclerView.ViewHolder {
        if (view is CardView) {
            return StoreItemListHolder(view)
        } else {
            return TextItemViewHolder(view)
        }
    }

    inner class StoreItemListHolder(itemView: View) : RecyclerView.ViewHolder(itemView), Binder<StoreItem> {

        private var avatar: ImageView = itemView.findViewById(R.id.store_item_image)
        private var crystalImage: ImageView = itemView.findViewById(R.id.crystal)
        private var desc: TextView = itemView.findViewById(R.id.item_name)
        private var price: TextView = itemView.findViewById(R.id.item_crystal_price)

        init {
            itemView.setOnClickListener {
                clickedPosition.onNext(
                    layoutPosition - 1 //Description of Store will be at first position, therefore omitted
                )
            }
        }

        override fun bind(data: StoreItem) {
            Glide
                .with(context)
                .asBitmap()
                .load("$STORE_URL/${data.imageName}")
                .thumbnail()
                .into(avatar)
            Glide
                .with(context)
                .asBitmap()
                .load(R.drawable.crystal)
                .thumbnail()
                .into(crystalImage)

            if (data.price > crystals) {
                val colorMatrix = ColorMatrix()
                colorMatrix.setSaturation(0f)
                val filter = ColorMatrixColorFilter(colorMatrix)
                avatar.setColorFilter(filter)
                crystalImage.setColorFilter(filter)

            } else {
                val colorMatrix = ColorMatrix()
                colorMatrix.setSaturation(1f)
                val filter = ColorMatrixColorFilter(colorMatrix)
                avatar.setColorFilter(filter)
                crystalImage.setColorFilter(filter)
            }

            this.desc.text = data.name
            this.price.text = data.price.toString()

            YoYo.with(Techniques.FadeIn).duration(500).playOn(itemView)
        }
    }

    inner class TextItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), Binder<TextStoreItem> {
        private var desc: TextView = itemView.findViewById(R.id.store_desc)
        override fun bind(data: TextStoreItem) {

            if (data.textValue.length > 5) {
                this.desc.text = data.textValue
            } else {
                crystals = data.textValue.toInt()
            }
            YoYo.with(Techniques.FadeIn).duration(500).playOn(itemView)
        }
    }

}
