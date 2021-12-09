package ru.schultetabledima.schultetable.table.view

import ru.schultetabledima.schultetable.table.presenter.TablePresenter

//Интерфейс для передачи Презентера в DialogueFragments:
//EndGameDialogueFragment и ErrorDialogueFragment
interface PassMeLinkOnObject {
    fun getTablePresenter(): TablePresenter
}