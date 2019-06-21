package com.mentormate.mentornet.ui.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by vasil.mitov@mentormate.com on 22/02/19.
 */

class MarginItemDecoration(
    private val topMargin : Int,
    private val botMargin : Int,
    private val leftMargin : Int,
    private val rightMargin : Int

    ) : RecyclerView.ItemDecoration(){
    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        with(outRect) {
            if (parent.getChildAdapterPosition(view) == 0) {
                top = topMargin
            }
            left = leftMargin
            right = rightMargin
            bottom = botMargin
        }
    }
}