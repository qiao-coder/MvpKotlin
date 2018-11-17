package com.tufei.base.net

/**
 * {"code":"00000","codeMsg":"点赞成功","data":""}
 * @author TuFei
 * @date 18-11-17.
 */
data class HttpResult<Data>(
    val code: String = "00000",
    val data: Data? = null,
    val codeMsg: String = ""
)

