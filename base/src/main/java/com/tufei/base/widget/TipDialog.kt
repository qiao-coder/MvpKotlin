package com.tufei.base.widget

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.tufei.base.R
import kotlinx.android.synthetic.main.dialog_tip.*

/**
 * @author TuFei
 * @date 18-10-17.
 */
class TipDialog : DialogFragment() {

    private lateinit var title: String
    private var content: String? = null
    private lateinit var confirmText: String
    private lateinit var cancelText: String
    private var isOnlyConfirm: Boolean = false
    private var onConfirmListener: OnConfirmListener? = null
    private var onCancelListener: OnCancelListener? = null
    private var _isCancelable: Boolean = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        setupArgs()
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCanceledOnTouchOutside(_isCancelable)
        return inflater.inflate(R.layout.dialog_tip, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_title.text = title
        tv_content.text = content
        if (isOnlyConfirm) {
            tv_cancel.visibility = View.GONE
            horizontal_divider.visibility = View.GONE
        }
        tv_confirm.text = confirmText
        tv_cancel.text = cancelText
        tv_cancel.setOnClickListener { onCancelListener?.onCancel(this) }
        tv_confirm.setOnClickListener { onConfirmListener?.onConfirm(this) }
    }

    private fun setupArgs() {
        arguments ?: dismiss()
        val args = arguments!!
        title = args.getString(TITLE, "")
        content = args.getString(CONTENT)
        confirmText = args.getString(CONFIRM_TEXT, "")
        cancelText = args.getString(CANCEL_TEXT, "")
        isOnlyConfirm = args.getBoolean(IS_ONLY_CONFIRM)
        _isCancelable = args.getBoolean(IS_CANCELABLE)
    }

    companion object {
        const val TITLE = "title"
        const val CONTENT = "content"
        const val CONFIRM_TEXT = "confirm_text"
        const val CANCEL_TEXT = "cancel_text"
        const val IS_ONLY_CONFIRM = "is_only_confirm"
        const val IS_CANCELABLE = "is_cancelable"
        fun newInstance(builder: Builder): TipDialog {
            val args = Bundle()
            val fragment = TipDialog()
            args.putString(TITLE, builder.title)
            val content = builder.content
            if (content == null || content.isEmpty()) throw IllegalArgumentException("The content must be not null or empty!")
            args.putString(CONTENT, builder.content)
            args.putString(CONFIRM_TEXT, builder.confirmText)
            args.putString(CANCEL_TEXT, builder.cancelText)
            args.putBoolean(IS_ONLY_CONFIRM, builder.isOnlyConfirm)
            args.putBoolean(IS_CANCELABLE, builder.isCancelable)
            fragment.arguments = args
            fragment.onConfirmListener = builder.onConfirmListener
            fragment.onCancelListener = builder.onCancelListener
            return fragment
        }
    }

    class Builder {
        internal var title: String
        internal var content: String? = null
        internal var confirmText: String
        internal var cancelText: String
        internal var onConfirmListener: OnConfirmListener? = null
        internal var onCancelListener: OnCancelListener? = null
        internal var isOnlyConfirm: Boolean
        internal var isCancelable: Boolean
        internal var isDismissOnceClick: Boolean

        init {
            this.title = "温馨提示"
            this.confirmText = "确定"
            this.cancelText = "取消"
            this.isOnlyConfirm = false
            this.isCancelable = true
            this.isDismissOnceClick = true
        }

        fun setTitle(title: String): Builder {
            this.title = title
            return this
        }

        fun setContent(content: String): Builder {
            this.content = content
            return this
        }

        fun setConfirmText(confirmText: String): Builder {
            this.confirmText = confirmText
            return this
        }

        fun setCancelText(cancelText: String): Builder {
            this.cancelText = cancelText
            return this
        }

        fun onConfirm(action: (DialogFragment) -> Unit): Builder {
            this.onConfirmListener = object : OnConfirmListener {
                override fun onConfirm(dialog: DialogFragment) {
                    action(dialog)
                    if (isDismissOnceClick) dialog.dismiss()
                }
            }
            return this
        }

        fun onCancel(action: (DialogFragment) -> Unit): Builder {
            this.onCancelListener = object : OnCancelListener {
                override fun onCancel(dialog: DialogFragment) {
                    action(dialog)
                    if (isDismissOnceClick) dialog.dismiss()
                }
            }
            return this
        }

        /**
         * 是否只有确认按钮。默认为false。
         */
        fun isOnlyConfirm(isOnlyConfirm: Boolean): Builder {
            this.isOnlyConfirm = isOnlyConfirm
            return this
        }

        /**
         * 点击弹窗外，是否可取消弹窗。默认为true。
         */
        fun isCancelable(isCancelable: Boolean): Builder {
            this.isCancelable = isCancelable
            return this
        }

        /**
         * 一点击"取消"或者"确认"按钮，是否就立刻关闭弹窗。默认为true。
         * 如果设为false，需要手动调用DialogFragment的dismiss方法。
         */
        fun isDismissOnceClick(isDismissOnceClick: Boolean): Builder {
            this.isDismissOnceClick = isDismissOnceClick
            return this
        }

        fun build(): TipDialog {
            return TipDialog.newInstance(this)
        }
    }
}

interface OnConfirmListener {
    fun onConfirm(dialog: DialogFragment)
}

interface OnCancelListener {
    fun onCancel(dialog: DialogFragment)
}
