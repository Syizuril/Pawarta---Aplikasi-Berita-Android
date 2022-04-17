package com.syizuril.pawarta.utils

import android.content.Context
import android.util.TypedValue
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler


/**
 * Created by Syekh Syihabuddin Azmil Umri on 28/02/2022.
 */
@Suppress("NAME_SHADOWING")
class GridAutofitLayoutManager : GridLayoutManager {
    private var mColumnWidth = 0
    private var mColumnWidthChanged = true

    constructor(context: Context, columnWidth: Int) : super(context, 1) {
        /* Initially set spanCount to 1, will be changed automatically later. */
        setColumnWidth(checkedColumnWidth(context, columnWidth))
    }

    constructor(context: Context, unit: Int, columnWidth: Int) : super(context, 1) {
        /* Initially set spanCount to 1, will be changed automatically later. */
        val pixColumnWidth =
            TypedValue.applyDimension(
                unit,
                columnWidth.toFloat(),
                context.resources.displayMetrics
            )
                .toInt()
        setColumnWidth(checkedColumnWidth(context, pixColumnWidth))
    }

    constructor(
        context: Context,
        columnWidth: Int,
        orientation: Int,
        reverseLayout: Boolean
    ) : super(context, 1, orientation, reverseLayout) {
        /* Initially set spanCount to 1, will be changed automatically later. */
        setColumnWidth(checkedColumnWidth(context, columnWidth))
    }

    private fun checkedColumnWidth(context: Context, columnWidth: Int): Int {
        var columnWidth = columnWidth
        if (columnWidth <= 0) {
            /* Set default columnWidth value (48dp here). It is better to move this constant
            to static constant on top, but we need context to convert it to dp, so can't really
            do so. */
            columnWidth = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 48f,
                context.resources.displayMetrics
            ).toInt()
        }
        return columnWidth
    }

    fun setColumnWidth(newColumnWidth: Int) {
        if (newColumnWidth > 0 && newColumnWidth != mColumnWidth) {
            mColumnWidth = newColumnWidth
            mColumnWidthChanged = true
        }
    }

    override fun onLayoutChildren(recycler: Recycler?, state: RecyclerView.State?) {
        val width = width
        val height = height
        if (mColumnWidthChanged && mColumnWidth > 0 && width > 0 && height > 0) {
            val totalSpace: Int = if (orientation == VERTICAL) {
                width - paddingRight - paddingLeft
            } else {
                height - paddingTop - paddingBottom
            }
            val spanCount = Math.max(1, totalSpace / mColumnWidth)
            setSpanCount(spanCount)
            mColumnWidthChanged = false
        }
        super.onLayoutChildren(recycler, state)
    }
}