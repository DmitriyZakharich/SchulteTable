package ru.schultetabledima.schultetable.advice

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.schultetabledima.schultetable.R
import ru.schultetabledima.schultetable.common.BaseScreenFragment
import ru.schultetabledima.schultetable.contracts.AdviceContract
import ru.schultetabledima.schultetable.main.MainActivity
import javax.inject.Inject

class AdviceFragment : BaseScreenFragment(R.layout.fragment_advice), AdviceContract.View {
    @InjectPresenter
    lateinit var advicePresenter: AdvicePresenter

    @ProvidePresenter
    fun provideRepositoryPresenter(): AdvicePresenter {
        val adviceModule = DaggerAdviceComponent.create().getAdviceModel()
        advicePresenter = AdvicePresenter(adviceModule)
        return advicePresenter
    }

    private val textViewList: MutableList<AppCompatTextView?> = mutableListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textViewList.add(view.findViewById(R.id.tv_definition))
        textViewList.add(view.findViewById(R.id.tvAdvice1))
        textViewList.add(view.findViewById(R.id.tvAdvice2))
        textViewList.add(view.findViewById(R.id.tvAdvice3))
        textViewList.add(view.findViewById(R.id.tvAdvice4))
        textViewList.add(view.findViewById(R.id.tvAdvice5))
        textViewList.add(view.findViewById(R.id.tvAdvice6))
        textViewList.add(view.findViewById(R.id.tvAdvice7))
        textViewList.add(view.findViewById(R.id.tvAdvice8))
        textViewList.add(view.findViewById(R.id.tvAdvice9))
        textViewList.add(view.findViewById(R.id.tv_important_point))
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).visibilityBottomNavigationView(View.VISIBLE)
    }

    @Override
    override fun showAdvice(index: Int, advice: String?) {
        textViewList[index]?.text = textViewList[index]?.text.toString().plus(advice)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        textViewList.clear()
    }

    companion object {
        fun newInstance(): AdviceFragment {
            return AdviceFragment()
        }
    }
}