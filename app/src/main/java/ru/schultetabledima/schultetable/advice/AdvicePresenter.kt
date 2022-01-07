package ru.schultetabledima.schultetable.advice

import moxy.InjectViewState
import moxy.MvpPresenter
import ru.schultetabledima.schultetable.contracts.AdviceContract

@InjectViewState
class AdvicePresenter(private val adviceModel: AdviceContract.Model) :
    MvpPresenter<AdviceContract.View?>(), AdviceContract.Presenter {

    private var listAdvice: List<String>? = null

    init {
        getAdvice()
        pushAdviceToView()
    }

    private fun getAdvice() {
        listAdvice = adviceModel.advice
    }

    private fun pushAdviceToView() {
        for ((count, advice) in listAdvice!!.withIndex()) {
            viewState!!.showAdvice(count, advice)
        }
    }
}