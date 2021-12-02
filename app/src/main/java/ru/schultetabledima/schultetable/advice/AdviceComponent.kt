package ru.schultetabledima.schultetable.advice

import dagger.Component

@Component
interface AdviceComponent {
    fun getAdviceModel(): AdviceModel
}
