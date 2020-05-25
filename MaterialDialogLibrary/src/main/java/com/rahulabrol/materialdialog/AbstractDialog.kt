package com.rahulabrol.materialdialog

import android.app.Activity
import android.app.Dialog
import android.content.res.Configuration
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.annotation.RawRes
import androidx.core.content.ContextCompat
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.button.MaterialButton
import com.rahulabrol.materialdialog.interfaces.DialogInterface
import com.rahulabrol.materialdialog.interfaces.OnCancelListener
import com.rahulabrol.materialdialog.interfaces.OnDismissListener
import com.rahulabrol.materialdialog.interfaces.OnShowListener
import com.rahulabrol.materialdialog.model.DialogButton
import com.rahulabrol.materialdialog.utils.getColor
import com.rahulabrol.materialdialog.utils.hide
import com.rahulabrol.materialdialog.utils.remove
import com.rahulabrol.materialdialog.utils.show


/**
 * Created by Rahul Abrol on 25/5/20.
 */
open class AbstractDialog(
    @NonNull var mActivity: Activity,
    @NonNull var title: String,
    @NonNull var message: String,
    var mCancelable: Boolean,
    @NonNull var mPositiveButton: DialogButton,
    @NonNull var mNegativeButton: DialogButton,
    @RawRes var mAnimationResId: Int,
    @NonNull var mAnimationFile: String?
) : DialogInterface {

    protected var mDialog: Dialog? = null
    protected var mAnimationView: LottieAnimationView? = null

    protected var mOnDismissListener: OnDismissListener? = null
    protected var mOnCancelListener: OnCancelListener? = null
    protected var mOnShowListener: OnShowListener? = null

    protected open fun createView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): View? {
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        val dialogView: View =
            inflater.inflate(R.layout.layout_alert_dialog, container, false)

        // Initialize Views
        val mTitleView = dialogView.findViewById<TextView>(R.id.textView_title)
        val mMessageView = dialogView.findViewById<TextView>(R.id.textView_message)
        val mPositiveButtonView: MaterialButton = dialogView.findViewById(R.id.button_positive)
        val mNegativeButtonView: MaterialButton = dialogView.findViewById(R.id.button_negative)
        mAnimationView = dialogView.findViewById(R.id.animation_view)

        // Set Title
        title.let {
            mTitleView.show()
            mTitleView.text = title
        }

        // Set Message
        message.let {
            mMessageView.show()
            mMessageView.text = message
        }

        // Set Positive Button
        mPositiveButton.let {
            mPositiveButtonView.show()
            mPositiveButtonView.text = it.title
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP &&
                it.icon != NO_ICON
            ) {
                mActivity.let { a ->
                    mPositiveButtonView.icon = ContextCompat.getDrawable(
                        a,
                        it.icon
                    )
                }
            }
            mPositiveButtonView.setOnClickListener { _ ->
                it.onClickListener
                    ?.onClick(this@AbstractDialog, BUTTON_POSITIVE)
            }
        }


        // Set Negative Button
        mNegativeButton.let {
            mNegativeButtonView.show()
            mNegativeButtonView.text = it.title
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP &&
                it.icon != NO_ICON
            ) {
                mActivity.let { a ->
                    mNegativeButtonView.icon = ContextCompat.getDrawable(
                        a,
                        it.icon
                    )
                }
            }
            mNegativeButtonView.setOnClickListener { _ ->
                it.onClickListener
                    ?.onClick(this@AbstractDialog, BUTTON_NEGATIVE)
            }
        }

        // If Orientation is Horizontal, Hide AnimationView
        val orientation = mActivity.resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mAnimationView?.remove()
        } else {
            // Set Animation from Resource
            if (mAnimationResId != NO_ANIMATION) {
                mAnimationView?.show()
                mAnimationView?.setAnimation(mAnimationResId)
                mAnimationView?.playAnimation()

                // Set Animation from Assets File
            } else if (mAnimationFile != null) {
                mAnimationView?.show()
                mAnimationView?.setAnimation(mAnimationFile)
                mAnimationView?.playAnimation()
            } else {
                mAnimationView?.remove()
            }
        }

        // Apply Styles
        val a = mActivity.theme.obtainStyledAttributes(R.styleable.MaterialDialog)
        try {
            // Set Dialog Background
            dialogView.setBackgroundColor(
                a.getColor(
                    R.styleable.MaterialDialog_material_dialog_background,
                    getColor(mActivity, R.color.material_dialog_background)
                )
            )

            // Set Title Text Color
            mTitleView.setTextColor(
                a.getColor(
                    R.styleable.MaterialDialog_material_dialog_title_text_color,
                    getColor(mActivity, R.color.material_dialog_title_text_color)
                )
            )

            // Set Message Text Color
            mMessageView.setTextColor(
                a.getColor(
                    R.styleable.MaterialDialog_material_dialog_message_text_color,
                    getColor(mActivity, R.color.material_dialog_message_text_color)
                )
            )

            // Set Positive Button Icon Tint
            var mPositiveButtonTint = a.getColorStateList(
                R.styleable.MaterialDialog_material_dialog_positive_button_text_color
            )
            if (mPositiveButtonTint == null) {
                mPositiveButtonTint = ContextCompat.getColorStateList(
                    mActivity.applicationContext,
                    R.color.material_dialog_positive_button_text_color
                )
            }
            mPositiveButtonView.setTextColor(mPositiveButtonTint)
            mPositiveButtonView.iconTint = mPositiveButtonTint

            // Set Negative Button Icon & Text Tint
            var mNegativeButtonTint = a.getColorStateList(
                R.styleable.MaterialDialog_material_dialog_negative_button_text_color
            )
            if (mNegativeButtonTint == null) {
                mNegativeButtonTint = ContextCompat.getColorStateList(
                    mActivity.applicationContext,
                    R.color.material_dialog_negative_button_text_color
                )
            }
            mNegativeButtonView.iconTint = mNegativeButtonTint
            mNegativeButtonView.setTextColor(mNegativeButtonTint)

            // Set Positive Button Background Tint
            var mBackgroundTint = a.getColorStateList(
                R.styleable.MaterialDialog_material_dialog_positive_button_color
            )
            if (mBackgroundTint == null) {
                mBackgroundTint = ContextCompat.getColorStateList(
                    mActivity.applicationContext,
                    R.color.material_dialog_positive_button_color
                )
            }
            mPositiveButtonView.backgroundTintList = mBackgroundTint
            if (mBackgroundTint != null) {
                mNegativeButtonView.rippleColor = mBackgroundTint.withAlpha(75)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            a.recycle()
        }
        return dialogView
    }

    /**
     * Displays the Dialog
     */
    fun show() {
        mDialog?.show() ?: throwNullDialog()
    }

    /**
     * Cancels the Dialog
     */
    override fun cancel() {
        mDialog?.cancel() ?: throwNullDialog()
    }

    /**
     * Dismisses the Dialog
     */
    override fun dismiss() {
        mDialog?.dismiss() ?: throwNullDialog()
    }

    /**
     * @param onShowListener interface for callback events when dialog is showed.
     */
    fun setOnShowListener(onShowListener: OnShowListener) {
        mOnShowListener = onShowListener
        mDialog!!.setOnShowListener { showCallback() }
    }

    /**
     * @param onCancelListener interface for callback events when dialog is cancelled.
     */
    fun setOnCancelListener(onCancelListener: OnCancelListener) {
        mOnCancelListener = onCancelListener
        mDialog?.setOnCancelListener { cancelCallback() }
    }

    /**
     * @param onDismissListener interface for callback events when dialog is dismissed;
     */
    fun setOnDismissListener(onDismissListener: OnDismissListener) {
        mOnDismissListener = onDismissListener
        mDialog?.setOnDismissListener { dismissCallback() }
    }

    /**
     * @return [LottieAnimationView] from the Dialog.
     */
    fun getAnimationView(): LottieAnimationView? {
        return mAnimationView
    }

    private fun showCallback() {
        mOnShowListener?.onShow(this)
    }

    private fun dismissCallback() {
        mOnDismissListener?.onDismiss(this)
    }

    private fun cancelCallback() {
        mOnCancelListener?.onCancel(this)
    }

    private fun throwNullDialog() {
        throw NullPointerException("Called method on null Dialog. Create dialog using `Builder` before calling on Dialog")
    }

    companion object {
        //Constants
        const val BUTTON_POSITIVE = 1
        const val BUTTON_NEGATIVE = -1
        const val NO_ICON = -111
        const val NO_ANIMATION = -111
    }

    interface OnClickListener {
        fun onClick(dialogInterface: DialogInterface?, which: Int)
    }
}