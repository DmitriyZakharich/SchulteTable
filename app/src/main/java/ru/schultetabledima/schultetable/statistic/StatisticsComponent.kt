package ru.schultetabledima.schultetable.statistic

import dagger.Component

@Component
interface StatisticsComponent {
    fun getDeletePopupMenuCreator(): DeletePopupMenuCreator
}