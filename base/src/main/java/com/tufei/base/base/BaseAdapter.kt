package com.tufei.base.base

import android.content.Context
import android.support.annotation.LayoutRes
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.tufei.base.util.rx.into
import com.tufei.base.util.rx.toSingle

/**
 * @author TuFei
 * @date 18-9-26.
 */
abstract class BaseAdapter<T>(@LayoutRes private val layoutId: Int) : RecyclerView.Adapter<ViewHolder>() {

    protected val TAG = javaClass.simpleName
    protected lateinit var context: Context

    open var items = mutableListOf<T>()
        /**
         * 使用[DiffUtil.DiffResult]做了一定的优化处理，但需要你在你的adapter里面，实现
         * 属于你自己的[areItemsTheSame]和[areContentsTheSame]方法，才能实现优化
         * 如果你将"items = 新的数据"，就会该set方法。最后刷新列表，不用你自己调
         * [notifyDataSetChanged]
         */
        set(update) {
            replace(field, update)
        }

    open fun addItem(item: T) {
        items.add(item)
        notifyItemChanged(items.size - 1)
    }

    open fun removeItem(item: T) {
        if (items.contains(item)) {
            val position = items.indexOf(item)
            items.remove(item)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, items.size - position + 1)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        onBind(holder, items[position], position)
    }

    /**
     * 本质就是adapter的[onBindViewHolder]
     */
    abstract fun onBind(holder: ViewHolder, item: T, position: Int)

    override fun getItemCount(): Int = items.size

    private fun replace(old: MutableList<T>, update: MutableList<T>) {
        when {
            old.isEmpty() -> {
                old.addAll(update)
                notifyDataSetChanged()
            }
            update.isEmpty() -> {
                old.clear()
                notifyDataSetChanged()
            }
            else -> {
                update(old, update)
                    .toSingle()
                    .into {
                        old.clear()
                        old.addAll(update)
                        it.dispatchUpdatesTo(this)
                    }
            }
        }
    }

    private fun update(oldItems: List<T>, update: List<T>): DiffUtil.DiffResult =
        DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize() = oldItems.size

            override fun getNewListSize() = update.size

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                areContentsTheSame(oldItems[oldItemPosition], update[newItemPosition])

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                areItemsTheSame(oldItems[oldItemPosition], update[newItemPosition])
        })

    protected abstract fun areItemsTheSame(oldItem: T, newItem: T): Boolean

    protected abstract fun areContentsTheSame(oldItem: T, newItem: T): Boolean
}