package ru.schultetabledima.schultetable.table.model

import android.app.AlertDialog
import android.app.Dialog
import ru.schultetabledima.schultetable.R
import ru.schultetabledima.schultetable.table.presenter.ErrorDialogueFragment
import ru.schultetabledima.schultetable.table.presenter.TablePresenter

class ErrorDialogueCreator(
    private val errorDialogueFragment: ErrorDialogueFragment,
    val tablePresenter: TablePresenter?
) {

    private val builder: AlertDialog.Builder =
        AlertDialog.Builder(errorDialogueFragment.requireActivity(), R.style.AlertDialogCustom)

    init {
        createDialogue()
    }

    private fun createDialogue() {
        builder.setTitle(R.string.internalError)
            .setMessage(R.string.restartTable)
            .setPositiveButton(R.string.restart) { _, _ ->
                tablePresenter?.viewState
                    ?.moveFragment(R.id.action_tableFragment_to_tableFragment, null)
            }
            .setCancelable(false)

        errorDialogueFragment.isCancelable = false
    }

    fun getDialog(): Dialog = builder.create()
}