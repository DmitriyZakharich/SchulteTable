package ru.schultetabledima.schultetable.advice

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import ru.schultetabledima.schultetable.R
import ru.schultetabledima.schultetable.common.BaseScreenFragment
import ru.schultetabledima.schultetable.contracts.AdviceContract
import ru.schultetabledima.schultetable.main.MainActivity
import ru.schultetabledima.schultetable.main.popBackStackAllInstances

class AdviceFragment : BaseScreenFragment(R.layout.fragment_advice), AdviceContract.View {
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
        textViewList.add(view.findViewById(R.id.tvAdvice3))
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).visibilityBottomNavigationView(View.VISIBLE)
    }

    override fun showAdvice(index: Int, advice: String?) {
        textViewList[index]?.text = advice
    }
}