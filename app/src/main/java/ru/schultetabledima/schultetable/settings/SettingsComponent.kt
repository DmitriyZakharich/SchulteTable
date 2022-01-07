package ru.schultetabledima.schultetable.settings

import androidx.fragment.app.Fragment
import dagger.Component
import dagger.Module
import dagger.Provides
import ru.schultetabledima.schultetable.utils.PreferencesReader


@Component(modules = [SettingsModule::class])
interface SettingsComponent {

    fun getPreferencesReader(): PreferencesReader
    fun getPreferencesWriter(): PreferencesWriter
    fun getSettingsPresenter(): SettingsPresenter
}

@Module
class SettingsModule(val view: Fragment) {

    @Provides
    fun settingsPresenter(): SettingsPresenter {
        return SettingsPresenter(view, PreferencesWriter(), PreferencesReader())
    }
}
