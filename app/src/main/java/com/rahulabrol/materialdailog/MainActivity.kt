package com.rahulabrol.materialdailog

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.rahulabrol.materialdialog.AbstractDialog
import com.rahulabrol.materialdialog.MaterialDialog
import com.rahulabrol.materialdialog.interfaces.DialogInterface
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var simpleDialog: MaterialDialog? = null
    private var animatedDialog: MaterialDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Simple Material Dialog
        simpleDialog = MaterialDialog.Builder(this)
            .setTitle("Delete?")
            .setMessage("Are you sure want to delete this file?")
            .setCancelable(false)
            .setPositiveButton(
                "Delete",
                R.drawable.ic_delete,
                object : AbstractDialog.OnClickListener {
                    override fun onClick(dialogInterface: DialogInterface?, which: Int) {
                        Toast.makeText(applicationContext, "Deleted!", Toast.LENGTH_SHORT)
                            .show()
                        dialogInterface?.dismiss()

                    }
                })
            .setNegativeButton(
                "Cancel",
                R.drawable.ic_close,
                object : AbstractDialog.OnClickListener {
                    override fun onClick(dialogInterface: DialogInterface?, which: Int) {
                        Toast.makeText(applicationContext, "Cancelled!", Toast.LENGTH_SHORT)
                            .show()
                        dialogInterface?.dismiss()
                    }
                })
            .build()

        // Animated Simple Material Dialog
        animatedDialog = MaterialDialog.Builder(this)
            .setTitle("Delete?")
            .setMessage("Are you sure want to delete this file?")
            .setCancelable(false)
            .setPositiveButton(
                "Delete",
                R.drawable.ic_delete,
                object : AbstractDialog.OnClickListener {
                    override fun onClick(dialogInterface: DialogInterface?, which: Int) {
                        Toast.makeText(applicationContext, "Deleted!", Toast.LENGTH_SHORT)
                            .show()
                        dialogInterface?.dismiss()
                    }
                })
            .setNegativeButton(
                "Cancel",
                R.drawable.ic_close,
                object : AbstractDialog.OnClickListener {
                    override fun onClick(dialogInterface: DialogInterface?, which: Int) {
                        Toast.makeText(applicationContext, "Cancelled!", Toast.LENGTH_SHORT)
                            .show()
                        dialogInterface?.dismiss()
                    }
                })
            .setAnimation("delete_anim.json")
            .build()

        button_simple_dialog.setOnClickListener {
            // Show simple Material Dialog
            simpleDialog?.show()
        }

        button_animated_dialog.setOnClickListener {
            // Animated Simple Material Dialog
            animatedDialog?.show()
        }
    }

    companion object {
        const val IS_SHOWING_DIALOG = "IS_SHOWING_DIALOG"
    }
}
