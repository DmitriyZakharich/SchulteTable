package ru.schultetabledima.schultetable.table.presenter

import ru.schultetabledima.schultetable.table.model.ErrorDialogueCreator

class ErrorDialoguePresenter(
    private val errorDialogueFragment: ErrorDialogueFragment,
    private val tablePresenter: TablePresenter?
) {

    init {
        errorDialogueCreate()
    }

    private fun errorDialogueCreate() {
        val dialogueCreator = ErrorDialogueCreator(errorDialogueFragment, tablePresenter)
        errorDialogueFragment.setDialog(dialogueCreator.getDialog())
    }
}