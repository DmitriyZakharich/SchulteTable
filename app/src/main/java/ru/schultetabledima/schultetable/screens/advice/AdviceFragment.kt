package ru.schultetabledima.schultetable.screens.advice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import moxy.presenter.InjectPresenter
import ru.schultetabledima.schultetable.R
import ru.schultetabledima.schultetable.common.BaseScreenFragment
import ru.schultetabledima.schultetable.contracts.AdviceContract
import ru.schultetabledima.schultetable.databinding.FragmentAdviceBinding
import ru.schultetabledima.schultetable.screens.main.MainActivity

class AdviceFragment : BaseScreenFragment(R.layout.fragment_advice), AdviceContract.View {
    @InjectPresenter
    lateinit var advicePresenter: AdvicePresenter

    private val textViewList: MutableList<AppCompatTextView?> = mutableListOf()
    private var _binding: FragmentAdviceBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance(): AdviceFragment {
            return AdviceFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        _binding = FragmentAdviceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textViewList.add(binding.tvDefinition)
        textViewList.add(binding.tvAdvice1)
        textViewList.add(binding.tvAdvice2)
        textViewList.add(binding.tvAdvice3)
        textViewList.add(binding.tvAdvice4)
        textViewList.add(binding.tvAdvice5)
        textViewList.add(binding.tvAdvice6)
        textViewList.add(binding.tvAdvice7)
        textViewList.add(binding.tvAdvice8)
        textViewList.add(binding.tvAdvice9)
        textViewList.add(binding.tvImportantPoint)
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
        _binding = null
        textViewList.clear()
    }
}