package ru.schultetabledima.schultetable.advice

import android.util.Log
import moxy.InjectViewState
import moxy.MvpPresenter
import ru.schultetabledima.schultetable.contracts.AdviceContract
import ru.schultetabledima.schultetable.R
import java.util.*

@InjectViewState
class AdvicePresenter : MvpPresenter<AdviceContract.View?>(), AdviceContract.Presenter {


    private var adviceModel: AdviceContract.Model? = null
    private var adviceResource: Int? = null
    private var listAdvice: List<String>? = null

    init {
        initialization()
        getAdvice()
        pushAdviceToView()
    }

    private fun initialization() {
        adviceModel = AdviceModel()
        adviceResource = if (Locale.getDefault().language == "ru") R.raw.advice1_ru else R.raw.advice1_en
    }

    private fun getAdvice() {
        listAdvice = adviceModel!!.getAdvice(adviceResource!!).lines()
        Log.d("TA1111111G", "listAdvice: ${listAdvice!!.size}")
    }

    private fun pushAdviceToView() {
        for ((count, advice) in listAdvice!!.withIndex()) {
            viewState!!.showAdvice(count, advice)
        }
    }
}