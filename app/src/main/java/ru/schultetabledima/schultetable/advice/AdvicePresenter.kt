package ru.schultetabledima.schultetable.advice

import moxy.InjectViewState
import moxy.MvpPresenter
<<<<<<< HEAD
import ru.schultetabledima.schultetable.contracts.AdviceContract

@InjectViewState
class AdvicePresenter(private val adviceModel: AdviceContract.Model) :
    MvpPresenter<AdviceContract.View?>(), AdviceContract.Presenter {

    private var listAdvice: List<String>? = null

    init {
=======
import ru.schultetabledima.schultetable.R
import ru.schultetabledima.schultetable.contracts.AdviceContract
import java.util.*

@InjectViewState
class AdvicePresenter : MvpPresenter<AdviceContract.View?>(), AdviceContract.Presenter {


    private var adviceModel: AdviceContract.Model? = null
    private var adviceResource: Int? = null
    private var listAdvice: List<String>? = null

    init {
        initialization()
>>>>>>> devdesign
        getAdvice()
        pushAdviceToView()
    }

<<<<<<< HEAD
    private fun getAdvice() {
        listAdvice = adviceModel.advice
=======
    private fun initialization() {
        adviceModel = AdviceModel()
        adviceResource =
            if (Locale.getDefault().language == "ru") R.raw.advice1_ru else R.raw.advice1_en
    }

    private fun getAdvice() {
        listAdvice = adviceModel!!.getAdvice(adviceResource!!).lines()
>>>>>>> devdesign
    }

    private fun pushAdviceToView() {
        for ((count, advice) in listAdvice!!.withIndex()) {
            viewState!!.showAdvice(count, advice)
        }
    }
}