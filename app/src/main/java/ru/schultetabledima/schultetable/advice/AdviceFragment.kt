package ru.schultetabledima.schultetable.advice

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import ru.schultetabledima.schultetable.R
import ru.schultetabledima.schultetable.contracts.AdviceContract
import ru.schultetabledima.schultetable.main.MainActivity

class AdviceFragment : MvpAppCompatFragment(R.layout.activity_advice), AdviceContract.View {
    @InjectPresenter
    lateinit var advicePresenter: AdvicePresenter

    private val textViewList: MutableList<AppCompatTextView?> = mutableListOf()

    companion object {
        fun newInstance(): AdviceFragment {
            return AdviceFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textViewList.add(view.findViewById(R.id.tvAdvice1))
        textViewList.add(view.findViewById(R.id.tvAdvice2))
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).visibilityBottomNavigationView(View.VISIBLE)
    }

    override fun showAdvice(index: Int, advice: String?) {
        textViewList[index]?.text = advice
    }

    override fun onDestroyView() {
        super.onDestroyView()
        textViewList.clear()
    }
}