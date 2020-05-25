package com.rahulabrol.materialdialog

import android.app.Activity
import android.content.Context
import android.graphics.Outline
import android.os.Build
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.FrameLayout
import androidx.annotation.NonNull
import androidx.annotation.RawRes
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.rahulabrol.materialdialog.model.DialogButton

/**
 * Created by Rahul Abrol on 25/5/20.
 *
 * Creates BottomSheet Material Dialog with 2 buttons.
 * <p>
 * Use {@link BottomSheetMaterialDialog.Builder} to create a new instance.
 */
class BottomSheetMaterialDialog(
    @NonNull mActivity: Activity,
    @NonNull title: String,
    @NonNull message: String,
    mCancelable: Boolean,
    @NonNull mPositiveButton: DialogButton,
    @NonNull mNegativeButton: DialogButton,
    @RawRes mAnimationResId: Int,
    @NonNull mAnimationFile: String?
) : AbstractDialog(
    mActivity,
    title,
    message,
    mCancelable,
    mPositiveButton,
    mNegativeButton,
    mAnimationResId,
    mAnimationFile
) {
    init {

        // Init Dialog, Create Bottom Sheet Dialog
        mDialog = BottomSheetDialog(mActivity)

        val inflater = mActivity.layoutInflater

        val dialogView = createView(inflater, null)
        mDialog?.setContentView(dialogView!!)

        // Set Cancelable property
        mDialog?.setCancelable(mCancelable)

        // Clip AnimationView to round Corners

        // Clip AnimationView to round Corners
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialogView?.outlineProvider = object : ViewOutlineProvider() {
                override fun getOutline(view: View, outline: Outline) {
                    val radius =
                        mActivity.resources.getDimension(R.dimen.radiusTop)
                    outline.setRoundRect(
                        0,
                        0,
                        view.width,
                        view.height + radius.toInt(),
                        radius
                    )
                }
            }
            dialogView?.clipToOutline = true
        } else {
            dialogView?.findViewById<View>(R.id.relative_layout_dialog)?.setPadding(
                0,
                mActivity.resources.getDimension(R.dimen.paddingTop).toInt(),
                0,
                0
            )
        }

        // Expand Bottom Sheet after showing.
        mDialog?.setOnShowListener { dialog ->
            val d: BottomSheetDialog = dialog as BottomSheetDialog
            val bottomSheet: FrameLayout? = d.findViewById(R.id.design_bottom_sheet)
            if (bottomSheet != null) {
                BottomSheetBehavior.from(bottomSheet).state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
    }


    /**
     * Builder for [BottomSheetMaterialDialog].
     */
    class Builder
    /**
     * @param activity where BottomSheet Material Dialog is to be built.
     */(private val activity: Activity) {
        private var title: String? = null
        private var message: String? = null
        private var isCancelable = false
        private var positiveButton: DialogButton? = null
        private var negativeButton: DialogButton? = null
        private var animationResId = NO_ANIMATION
        private var animationFile: String? = null

        /**
         * @param title Sets the Title of BottomSheet Material Dialog.
         * @return this, for chaining.
         */
        fun setTitle(title: String): Builder {
            this.title = title
            return this
        }

        /**
         * @param message Sets the Message of BottomSheet Material Dialog.
         * @return this, for chaining.
         */
        fun setMessage(message: String): Builder {
            this.message = message
            return this
        }

        /**
         * @param isCancelable Sets cancelable property of BottomSheet Material Dialog.
         * @return this, for chaining.
         */
        fun setCancelable(isCancelable: Boolean): Builder {
            this.isCancelable = isCancelable
            return this
        }

        /**
         * Sets the Positive Button to BottomSheet Material Dialog without icon
         *
         * @param name            sets the name/label of button.
         * @param onClickListener interface for callback event on click of button.
         * @return this, for chaining.
         */
        fun setPositiveButton(name: String, onClickListener: OnClickListener): Builder {
            return setPositiveButton(name, NO_ICON, onClickListener)
        }

        /**
         * Sets the Positive Button to BottomSheet Material Dialog with icon
         *
         * @param name            sets the name/label of button.
         * @param icon            sets the resource icon for button.
         * @param onClickListener interface for callback event on click of button.
         * @return this, for chaining.
         */
        fun setPositiveButton(
            name: String,
            icon: Int,
            onClickListener: OnClickListener
        ): Builder {
            positiveButton = DialogButton(name, icon, onClickListener)
            return this
        }

        /**
         * Sets the Negative Button to BottomSheet Material Dialog without icon.
         *
         * @param name            sets the name/label of button.
         * @param onClickListener interface for callback event on click of button.
         * @see this, for chaining.
         */
        fun setNegativeButton(name: String, onClickListener: OnClickListener): Builder {
            return setNegativeButton(name, NO_ICON, onClickListener)
        }

        /**
         * Sets the Negative Button to BottomSheet Material Dialog with icon
         *
         * @param name            sets the name/label of button.
         * @param icon            sets the resource icon for button.
         * @param onClickListener interface for callback event on click of button.
         * @return this, for chaining.
         */
        fun setNegativeButton(
            name: String,
            icon: Int,
            onClickListener: OnClickListener
        ): Builder {
            negativeButton = DialogButton(name, icon, onClickListener)
            return this
        }

        /**
         * It sets the resource json to the [com.airbnb.lottie.LottieAnimationView].
         *
         * @param animationResId sets the resource to [com.airbnb.lottie.LottieAnimationView].
         * @return this, for chaining.
         */
        fun setAnimation(@RawRes animationResId: Int): Builder {
            this.animationResId = animationResId
            return this
        }

        /**
         * It sets the json file to the [com.airbnb.lottie.LottieAnimationView] from assets.
         *
         * @param fileName sets the file from assets to [com.airbnb.lottie.LottieAnimationView].
         * @return this, for chaining.
         */
        fun setAnimation(fileName: String): Builder {
            animationFile = fileName
            return this
        }

        /**
         * Build the [BottomSheetMaterialDialog].
         */
        fun build(): BottomSheetMaterialDialog {
            return BottomSheetMaterialDialog(
                activity,
                title!!,
                message!!,
                isCancelable,
                positiveButton!!,
                negativeButton!!,
                animationResId,
                animationFile
            )
        }

    }

    internal class BottomSheetDialog(context: Context) :
        com.google.android.material.bottomsheet.BottomSheetDialog(
            context,
            R.style.BottomSheetDialogTheme
        )
}