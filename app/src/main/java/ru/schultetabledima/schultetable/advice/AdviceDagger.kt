package ru.schultetabledima.schultetable.advice

import dagger.Component
import dagger.Module
import dagger.Provides
import ru.schultetabledima.schultetable.contracts.AdviceContract


@Component(modules = [AdviceModule::class])
interface AdviceComponent {
    fun getAdviceModel(): AdviceContract.Model
}

@Module
object AdviceModule{
    @Provides
    fun adviceModel(): AdviceContract.Model = AdviceModel()
}
