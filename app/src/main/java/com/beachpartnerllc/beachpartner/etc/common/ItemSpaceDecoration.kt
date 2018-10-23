package com.beachpartnerllc.beachpartner.etc.common

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 02 Dec 2017 at 11:39 AM
 */
class ItemSpaceDecoration(private val offset: Int, private val spanCount: Int = 1) : RecyclerView.ItemDecoration() {
	override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
		val position = parent.getChildAdapterPosition(view)
		
		val left = if (position % spanCount != spanCount - 1) offset else offset / 2
		val right = if (position % spanCount == spanCount - 1) offset else offset / 2
        outRect.set(left, offset / 2, right, offset / 2)
	}
}