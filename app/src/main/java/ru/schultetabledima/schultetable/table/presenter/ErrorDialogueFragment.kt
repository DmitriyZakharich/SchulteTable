package ru.schultetabledima.schultetable.table.presenter

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import ru.schultetabledima.schultetable.table.view.PassMeLinkOnObject

class ErrorDialogueFragment : DialogFragment() {

    private var tablePresenter: TablePresenter? = null
    private var dialogue: Dialog? = null
    private var errorDialoguePresenter: ErrorDialoguePresenter? = null
    private var view: PassMeLinkOnObject? = null


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        view = parentFragment as PassMeLinkOnObject
        tablePresenter = view?.getTablePresenter()
        errorDialoguePresenter()
        return dialogue!!
    }

    private fun errorDialoguePresenter() {
        errorDialoguePresenter = ErrorDialoguePresenter(this, tablePresenter)
    }

    fun setDialog(dialogue: Dialog) {
        this.dialogue = dialogue
    }

    companion object {
        fun newInstance() = ErrorDialogueFragment()
    }
}