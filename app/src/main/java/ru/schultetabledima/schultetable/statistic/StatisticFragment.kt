package ru.schultetabledima.schultetable.statistic

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.recyclerview.widget.RecyclerView
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.schultetabledima.schultetable.R
import ru.schultetabledima.schultetable.advice.AdvicePresenter
import ru.schultetabledima.schultetable.advice.DaggerAdviceComponent
import ru.schultetabledima.schultetable.common.BaseScreenFragment
import ru.schultetabledima.schultetable.contracts.StatisticsContract
import ru.schultetabledima.schultetable.database.StatisticAdapter
import ru.schultetabledima.schultetable.main.MainActivity

class StatisticFragment : BaseScreenFragment(R.layout.fragment_statistics), StatisticsContract.View,
    AdapterView.OnItemSelectedListener {

    @InjectPresenter
    lateinit var statisticsPresenter: StatisticsPresenter

    @ProvidePresenter
    fun provideStatisticsPresenter(): StatisticsPresenter {
        val adviceModule = DaggerAdviceComponent.create().getAdviceModel()
        statisticsPresenter = StatisticsPresenter()
        return statisticsPresenter
    }

    private lateinit var recyclerView: RecyclerView
    private var selectQuantityTables: Spinner? = null
    private var selectValueType: Spinner? = null
    private var selectPlayedSizes: Spinner? = null
    private var recyclerViewState: Parcelable? = null

    companion object {
        fun newInstance(): StatisticFragment {
            return StatisticFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = requireView().findViewById(R.id.recyclerview)
        selectQuantityTables = requireView().findViewById(R.id.spinnerQuantityTables)
        selectValueType = requireView().findViewById(R.id.spinnerValueType)
        selectPlayedSizes = requireView().findViewById(R.id.spinnerPlayedSizes)


        selectQuantityTables?.onItemSelectedListener = this
        selectValueType?.onItemSelectedListener = this
        selectPlayedSizes?.onItemSelectedListener = this

        registerForContextMenu(recyclerView)
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

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    override fun setRecyclerViewAdapter(statisticAdapter: StatisticAdapter?) {
        recyclerView.adapter = statisticAdapter
    }

    override fun setQuantityTablesAdapter(adapterQuantityTables: ArrayAdapter<String>?) {
        selectQuantityTables?.adapter = adapterQuantityTables
    }

    override fun setValueTypeAdapter(adapterValueType: ArrayAdapter<String>?) {
        selectValueType?.adapter = adapterValueType
    }

    override fun setPlayedSizesAdapter(adapterPlayedSizes: ArrayAdapter<String>?) {
        selectPlayedSizes?.adapter = adapterPlayedSizes
    }

    override fun setSelectionQuantityTables(position: Int) {
        selectQuantityTables?.setSelection(position)
    }

    override fun setSelectionSpinnerValueType(position: Int) {
        selectValueType?.setSelection(position)
    }

    override fun setSelectionPlayedSizes(position: Int) {
        selectPlayedSizes?.setSelection(position)
    }
}