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
    val presenter: StatisticsPresenter,
    val context: Context?,
    val view: View?,
    private val result: Result,
    val position: Int
) {
    private lateinit var popupMenu: PopupMenu

    init {
        main()
    }

    private fun main() {
        val wrapper: Context = ContextThemeWrapper(context, R.style.PopupMenuStyle)
        popupMenu = PopupMenu(wrapper, view!!)
        popupMenu.inflate(R.menu.statistic_menu)

        popupMenu.setOnMenuItemClickListener { item: MenuItem? ->

            when (item!!.itemId) {
                R.id.item_delete -> {
                    presenter.deleteRecordFromDB(result, position)
                }
            }
            true
        }
    }

    fun getPopupMenu(): PopupMenu {
        return popupMenu
    }
}


