package com.rahulabrol.materialdialog

import android.app.Activity
import androidx.annotation.NonNull
import androidx.annotation.RawRes
import androidx.appcompat.app.AlertDialog
import com.rahulabrol.materialdialog.model.DialogButton

/**
 * Created by Rahul Abrol on 25/5/20.
 *
 * Creates a Material Dialog with 2 buttons.
 * <p>
 * Use {@link Builder} to create a new instance.
 */
class MaterialDialog(
    @NonNull mActivity: Activity,
    @NonNull title: String,
    @NonNull message: String,
    mCancelable: Boolean,
    @NonNull mPositiveButton: DialogButton,
    @NonNull mNegativeButton: DialogButton,
    @RawRes mAnimationResId: Int,
    @NonNull mAnimationFile: String
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

        // Init Dialog
        val builder = AlertDialog.Builder(mActivity)

        val inflater = mActivity.layoutInflater

        val dialogView = createView(inflater, null)

        builder.setView(dialogView)

        // Set Cancelable property
        builder.setCancelable(mCancelable)

        // Create and show dialog
        mDialog = builder.create()
    }

    /**
     * Builder for [MaterialDialog].
     *
     * [activity] where Material Dialog is to be built
     */
    class Builder(private val activity: Activity) {

        private var title: String? = null
        private var message: String? = null
        private var isCancelable = false
        private var positiveButton: DialogButton? = null
        private var negativeButton: DialogButton? = null
        private var animationResId = NO_ANIMATION
        private var animationFile: String? = null

        /**
         * @param title Sets the Title of Material Dialog.
         * @return this, for chaining.
         */
        fun setTitle(title: String): Builder {
            this.title = title
            return this
        }

        /**
         * @param message Sets the Message of Material Dialog.
         * @return this, for chaining.
         */
        fun setMessage(message: String): Builder {
            this.message = message
            return this
        }

        /**
         * @param isCancelable Sets cancelable property of Material Dialog.
         * @return this, for chaining.
         */
        fun setCancelable(isCancelable: Boolean): Builder {
            this.isCancelable = isCancelable
            return this
        }

        /** Sets the Positive Button to Material Dialog without icon
         * @param name sets the name/label of button.
         * @param onClickListener interface for callback event on click of button.
         * @see this, for chaining.
         */
        fun setPositiveButton(name: String, onClickListener: OnClickListener): Builder {
            return setPositiveButton(name, NO_ICON, onClickListener)
        }

        /** Sets the Positive Button to Material Dialog with icon
         * @param name sets the name/label of button.
         * @param icon sets the resource icon for button.
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

        /** Sets the Negative Button to Material Dialog without icon.
         * @param name sets the name/label of button.
         * @param onClickListener interface for callback event on click of button.
         * @return this, for chaining.
         */
        fun setNegativeButton(name: String, onClickListener: OnClickListener): Builder {
            return setNegativeButton(name, NO_ICON, onClickListener)
        }

        /** Sets the Negative Button to Material Dialog with icon
         * @param name sets the name/label of button.
         * @param icon sets the resource icon for button.
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

        /** It sets the resource json to the [com.airbnb.lottie.LottieAnimationView].
         * @param animationResId sets the resource to [com.airbnb.lottie.LottieAnimationView].
         * @return this, for chaining.
         */
        fun setAnimation(@RawRes animationResId: Int): Builder {
            this.animationResId = animationResId
            return this
        }

        /** It sets the json file to the [com.airbnb.lottie.LottieAnimationView] from assets.
         * @param fileName sets the file from assets to [com.airbnb.lottie.LottieAnimationView].
         * @return this, for chaining.
         */
        fun setAnimation(fileName: String): Builder {
            animationFile = fileName
            return this
        }

        /**
         * Build the [MaterialDialog].
         */
        fun build(): MaterialDialog {
            return MaterialDialog(
                activity,
                title!!,
                message!!,
                isCancelable,
                positiveButton!!,
                negativeButton!!,
                animationResId,
                animationFile!!
            )
        }
    }
}