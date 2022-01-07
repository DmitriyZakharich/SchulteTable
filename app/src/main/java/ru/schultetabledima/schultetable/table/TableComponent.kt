package ru.schultetabledima.schultetable.table

import dagger.Component
import dagger.Module
import dagger.Provides
import ru.schultetabledima.schultetable.table.model.CellValuesCreator
import ru.schultetabledima.schultetable.table.presenter.MenuButtonsHandler
import ru.schultetabledima.schultetable.table.presenter.MenuCustomizer
import ru.schultetabledima.schultetable.table.presenter.MoveInspector
import ru.schultetabledima.schultetable.table.presenter.TablePresenter
import ru.schultetabledima.schultetable.utils.PreferencesReader

@Component(modules = [TableModule::class])
interface TableComponent {

    fun getMoveInspector(): MoveInspector
    fun getPreferenceReader(): PreferencesReader
    fun getMenuCustomizer(): MenuCustomizer
    fun getMenuButtonsHandler(): MenuButtonsHandler
    fun getCellValuesCreator(): CellValuesCreator
}

@Module
class TableModule {

    @Provides
    fun moveInspector(): MoveInspector {
        return MoveInspector(PreferencesReader())
    }

    @Provides
    fun menuCustomizer(): MenuCustomizer {
        return MenuCustomizer(PreferencesReader())
    }

    @Provides
    fun preferencesReader(): PreferencesReader {
        return PreferencesReader()
    }

    @Provides
    fun menuButtonsHandler(): MenuButtonsHandler {
        return MenuButtonsHandler(PreferencesReader())
    }

    @Provides
    fun cellValuesCreator(): CellValuesCreator {
        return CellValuesCreator(PreferencesReader())
    }
}