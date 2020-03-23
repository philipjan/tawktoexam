package com.coding.tawktoexam.utility

import android.content.ContextWrapper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
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

/**
 * this file contains reusable view extension functions.
 * @see https://kotlinlang.org/docs/reference/extensions.html
 */

fun View.getParentActivity(): AppCompatActivity? {
    var context = this.context
    while (context is ContextWrapper) {
        if (context is AppCompatActivity) {
            return context
        }
        context = context.baseContext
    }
    return null
}