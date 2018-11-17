package com.tufei.base.base

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.extensions.LayoutContainer

/**
 * @author TuFei
 * @date 18-9-27.
 */
open class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer