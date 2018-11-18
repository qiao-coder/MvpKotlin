package com.example.module_a.main

import com.example.module_a.R
import com.example.module_a.UserBean
import com.tufei.base.base.BaseAdapter
import com.tufei.base.base.ViewHolder
import com.tufei.base.util.setCircleSrc
import kotlinx.android.synthetic.main.item_user.*

/**
 * @author TuFei
 * @date 18-11-17.
 */
class UserAdapter : BaseAdapter<UserBean>(R.layout.item_user) {
    override fun onBind(holder: ViewHolder, item: UserBean, position: Int) {
        with(holder) {
            iv_head.setCircleSrc(R.drawable.ic_launcher)
            tv_name.text = item.name
        }
    }

    /**
     * 取的这个id肯定是唯一标识。
     * 当然，下面两个方法，都可以直接返回fasle。应该是相当于不使用DiffUtil的更新数据算法。
     * 只有在重新将[items]赋值时，DiffUtil才会起作用
     */
    override fun areItemsTheSame(oldItem: UserBean, newItem: UserBean) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: UserBean, newItem: UserBean) =
        oldItem == newItem
}