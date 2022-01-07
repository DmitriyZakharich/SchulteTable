package ru.schultetabledima.schultetable.statistic

import android.content.Context
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.appcompat.view.ContextThemeWrapper
import ru.schultetabledima.schultetable.App
import ru.schultetabledima.schultetable.R
import ru.schultetabledima.schultetable.database.Result
import javax.inject.Inject

/**
 * Class for deleting records from DB
 * */
class DeletePopupMenuCreator @Inject constructor() {
    private lateinit var popupMenu: PopupMenu

    private var presenter: StatisticsPresenter? = null
    private var view: View? = null
    private var result: Result? = null
    private var position: Int? = null

    fun setData(presenter: StatisticsPresenter,view: View?,result: Result,position: Int) {
        this.presenter = presenter
        this.view = view
        this.result = result
        this.position = position
        main()
    }

    private fun main() {
        val wrapper: Context = ContextThemeWrapper(App.getContext(), R.style.PopupMenuStyle)
        popupMenu = PopupMenu(wrapper, view!!)
        popupMenu.inflate(R.menu.statistic_menu)

        popupMenu.setOnMenuItemClickListener { item: MenuItem? ->

            when (item!!.itemId) {
                R.id.item_delete -> {
                    presenter?.deleteRecordFromDB(result, position!!)
                }
            }
            true
        }
    }

    fun getPopupMenu(): PopupMenu {
        return popupMenu
    }
}


