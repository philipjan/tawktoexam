package com.coding.tawktoexam.utility

import androidx.recyclerview.widget.RecyclerView

/**
 * Extension method for Recyclerview ScrollState Listener to listen if use is currently scrolling the "LAST" item
 * [https://stackoverflow.com/a/55596362/6012096]
 */
fun RecyclerView.lastItemListener(method: () -> Unit) {
    this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                method.invoke()
            }
        }
    })
}