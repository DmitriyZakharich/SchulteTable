package ru.schultetabledima.schultetable.screens.statistic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import moxy.presenter.InjectPresenter
import ru.schultetabledima.schultetable.R
import ru.schultetabledima.schultetable.common.BaseScreenFragment
import ru.schultetabledima.schultetable.contracts.StatisticsContract
import ru.schultetabledima.schultetable.database.StatisticAdapter
import ru.schultetabledima.schultetable.databinding.FragmentStatisticsBinding
import ru.schultetabledima.schultetable.screens.main.MainActivity

class StatisticFragment : BaseScreenFragment(R.layout.fragment_statistics), StatisticsContract.View,
    AdapterView.OnItemSelectedListener {

    @InjectPresenter
    lateinit var statisticsPresenter: StatisticsPresenter

    private var _binding: FragmentStatisticsBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance(): StatisticFragment {
            return StatisticFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View {
        _binding = FragmentStatisticsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSpinnersItemSelectedListener()
        registerForContextMenu(binding.recyclerview)
    }

    private fun setSpinnersItemSelectedListener() {
        with(binding){
            spinnerQuantityTables.onItemSelectedListener = this@StatisticFragment
            spinnerValueType.onItemSelectedListener = this@StatisticFragment
            spinnerPlayedSizes.onItemSelectedListener = this@StatisticFragment
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).visibilityBottomNavigationView(View.VISIBLE)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        statisticsPresenter.spinnerItemSelected(
            parent!!.id,
            position,
            parent.selectedItem.toString()
        )
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

    override fun setRecyclerViewAdapter(statisticAdapter: StatisticAdapter?) {
        binding.recyclerview.adapter = statisticAdapter
    }

    override fun setQuantityTablesAdapter(adapterQuantityTables: ArrayAdapter<String>?) {
        binding.spinnerQuantityTables.adapter = adapterQuantityTables
    }

    override fun setValueTypeAdapter(adapterValueType: ArrayAdapter<String>?) {
        binding.spinnerValueType.adapter = adapterValueType
    }

    override fun setPlayedSizesAdapter(adapterPlayedSizes: ArrayAdapter<String>?) {
        binding.spinnerPlayedSizes.adapter = adapterPlayedSizes
    }

    override fun setSelectionQuantityTables(position: Int) {
        binding.spinnerQuantityTables.setSelection(position)
    }

    override fun setSelectionSpinnerValueType(position: Int) {
        binding.spinnerValueType.setSelection(position)
    }

    override fun setSelectionPlayedSizes(position: Int) {
        binding.spinnerPlayedSizes.setSelection(position)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}