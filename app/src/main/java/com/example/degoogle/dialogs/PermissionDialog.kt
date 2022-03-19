package com.example.degoogle.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.DialogFragment
import com.example.degoogle.R

class PermissionDialog : DialogFragment() {

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            return activity?.let {
                // Use the Builder class for convenient dialog construction
                val builder = AlertDialog.Builder(it)
                builder.setMessage(R.string.unknown_sources_dialog)
                    .setPositiveButton(R.string.settings,
                        DialogInterface.OnClickListener { dialog, id ->
                            startActivity(
                                Intent(
                                    Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES,
                                    Uri.parse("package:" + requireContext().packageName)
                                )
                            )
                        })
                    .setNegativeButton(R.string.decline,
                        DialogInterface.OnClickListener { dialog, id ->

                        })
                // Create the AlertDialog object and return it
                builder.create()
            } ?: throw IllegalStateException("Activity cannot be null")
        }
}