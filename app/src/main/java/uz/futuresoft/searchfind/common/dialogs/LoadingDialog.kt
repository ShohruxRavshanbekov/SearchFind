package uz.futuresoft.searchfind.common.dialogs

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import uz.futuresoft.searchfind.databinding.DialogLoadingBinding

class LoadingDialog(private val context: Context) {

    private val dialog: AlertDialog by lazy { AlertDialog.Builder(context).create() }
    private val dialogView by lazy { DialogLoadingBinding.inflate(LayoutInflater.from(context)) }

    init {
        dialog.setView(dialogView.root)
        dialog.setCancelable(false)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
    }

    fun show() {
        dialog.show()
    }

    fun dismiss() {
        dialog.dismiss()
    }
}