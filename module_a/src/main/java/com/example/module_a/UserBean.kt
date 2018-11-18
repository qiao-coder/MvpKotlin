package com.example.module_a

/**
 * @author TuFei
 * @date 18-11-17.
 */
//我们公司习惯写默认值。如果后台没传该字段，就会使用默认值。
//如果后台传了个null回来。Emmmm..必炸。
data class UserBean(var id: String = "0", var name: String = "")