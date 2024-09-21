package uz.futuresoft.searchfind.common.dialogs

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import uz.futuresoft.searchfind.databinding.DialogLogOutBinding

class LogOutDialog(
    private val context: Context,
    private val listener: Listener,
) {

    private val dialog: AlertDialog by lazy { AlertDialog.Builder(context).create() }
    private val dialogView by lazy { DialogLogOutBinding.inflate(LayoutInflater.from(context)) }

    init {
        dialog.setView(dialogView.root)
        dialog.setCancelable(false)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        dialogView.no.setOnClickListener {
            dismiss()
        }

        dialogView.yes.setOnClickListener {
            listener.onYesClicked()
        }
    }

    fun show() {
        dialog.show()
    }

    fun dismiss() {
        dialog.dismiss()
    }

    interface Listener {
        fun onYesClicked()
    }
}