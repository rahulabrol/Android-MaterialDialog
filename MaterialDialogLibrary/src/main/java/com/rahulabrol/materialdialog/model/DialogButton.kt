package com.rahulabrol.materialdialog.model

import com.rahulabrol.materialdialog.AbstractDialog

/**
 * Created by Rahul Abrol on 25/5/20.
 */
data class DialogButton(
    var title: String? = null,
    val icon: Int = AbstractDialog.NO_ICON,
    val onClickListener: AbstractDialog.OnClickListener? = null
)