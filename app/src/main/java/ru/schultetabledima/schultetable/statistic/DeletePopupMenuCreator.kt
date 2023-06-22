package ru.schultetabledima.schultetable.statistic

import android.content.Context
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.appcompat.view.ContextThemeWrapper
import ru.schultetabledima.schultetable.R
import ru.schultetabledima.schultetable.database.Result
/**
 * Class for deleting records from DB
 * */
class DeletePopupMenuCreator(
    private val presenter: StatisticsPresenter,
    context: Context?,
    view: View?,
    private val result: Result,
    private val position: Int
) {
    private val popupMenu: PopupMenu

    init {
        val wrapper: Context = ContextThemeWrapper(context, R.style.PopupMenuStyle)
        popupMenu = PopupMenu(wrapper, view!!)
        popupMenu.inflate(R.menu.statistic_menu)

        popupMenu.setOnMenuItemClickListener { item: MenuItem? ->

            if  (item!!.itemId == R.id.item_delete)
                presenter.deleteRecordFromDB(result, position)

            true
        }
    }

    fun getPopupMenu(): PopupMenu = popupMenu
}


