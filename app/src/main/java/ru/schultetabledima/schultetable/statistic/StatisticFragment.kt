package ru.schultetabledima.schultetable.statistic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.recyclerview.widget.RecyclerView
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import ru.schultetabledima.schultetable.R
import ru.schultetabledima.schultetable.contracts.StatisticsContract
import ru.schultetabledima.schultetable.database.StatisticAdapter
import ru.schultetabledima.schultetable.main.MainActivity

class StatisticFragment : MvpAppCompatFragment(), StatisticsContract.View,
    AdapterView.OnItemSelectedListener {

    @InjectPresenter
    lateinit var statisticsPresenter: StatisticsPresenter

    private var recyclerView: RecyclerView? = null
    private var selectQuantityTables: Spinner? = null
    private var selectValueType: Spinner? = null
    private var selectPlayedSizes: Spinner? = null


    companion object {
        fun newInstance(): StatisticFragment {
            return StatisticFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View? = inflater.inflate(R.layout.activity_statistics, container, false)

        recyclerView = view?.findViewById(R.id.recyclerview)
        selectQuantityTables = view?.findViewById(R.id.spinnerQuantityTables)
        selectValueType = view?.findViewById(R.id.spinnerValueType)
        selectPlayedSizes = view?.findViewById(R.id.spinnerPlayedSizes)


        selectQuantityTables?.onItemSelectedListener = this
        selectValueType?.onItemSelectedListener = this
        selectPlayedSizes?.onItemSelectedListener = this

        return view
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
        );
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    override fun setRecyclerViewAdapter(statisticAdapter: StatisticAdapter?) {
        recyclerView?.adapter = statisticAdapter
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