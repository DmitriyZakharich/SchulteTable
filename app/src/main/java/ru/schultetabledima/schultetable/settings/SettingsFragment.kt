package ru.schultetabledima.schultetable.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import ru.schultetabledima.schultetable.R
import ru.schultetabledima.schultetable.common.BaseScreenFragment
import ru.schultetabledima.schultetable.main.MainActivity

class SettingsFragment : BaseScreenFragment(R.layout.fragment_settings), View.OnClickListener {

    private var settingsPresenter: SettingsPresenter? = null

    private var switchMoveHint: SwitchMaterial? = null
    private var switchAnimation: SwitchMaterial? = null
    private var switchTouchCells: SwitchMaterial? = null
    private var switchTwoTables: SwitchMaterial? = null
    private var viewPager: ViewPager2? = null
    private var numbersFragment: NumbersFragment? = null
    private var lettersFragment: LettersFragment? = null

    companion object {
        fun newInstance(): SettingsFragment {
            return SettingsFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        switchAnimation = view.findViewById(R.id.switchAnimation)
        switchTouchCells = view.findViewById(R.id.switchPressButtons)
        switchTwoTables = view.findViewById(R.id.switchTwoTables)
        switchMoveHint = view.findViewById(R.id.switchMoveHint)

        val tabLayout: TabLayout = view.findViewById(R.id.tab_layout)
        viewPager = view.findViewById(R.id.viewPager)

        viewPager?.isSaveEnabled = false

        switchAnimation?.setOnClickListener(this)
        switchTouchCells?.setOnClickListener(this)
        switchTwoTables?.setOnClickListener(this)
        switchMoveHint?.setOnClickListener(this)

        numbersFragment = NumbersFragment.newInstance()
        lettersFragment = LettersFragment.newInstance()

        val fragments: MutableList<Fragment?> = mutableListOf()

        fragments.add(numbersFragment)
        fragments.add(lettersFragment)


        val indexOfNumbers = fragments.indexOf(numbersFragment)
        val indexOfLetters = fragments.indexOf(lettersFragment)

        val pageAdapter =
            MyAdapter(requireActivity())

        pageAdapter.setListFragments(fragments)
        viewPager?.adapter = pageAdapter



        TabLayoutMediator(tabLayout, viewPager!!) { tab, position ->
            if (position == indexOfNumbers)
                tab.setText(R.string.numbers);
            if (position == indexOfLetters)
                tab.setText(R.string.letters);
        }.attach()


        settingsPresenter = SettingsPresenter(this)

        tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                settingsPresenter!!.onTabSelectedListener(tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

    }

    fun setViewPagerCurrentItem(index: Int) {
        viewPager!!.setCurrentItem(index, false)
    }

    override fun onClick(v: View) {
        settingsPresenter!!.onClickListenerSwitch(v.id, (v as SwitchMaterial).isChecked)
    }


    fun customizationSwitchMoveHint(isEnabled: Boolean, isChecked: Boolean) {
        switchMoveHint!!.isEnabled = isEnabled
        if (isEnabled) {
            switchMoveHint!!.isChecked = isChecked
        } else {
            switchMoveHint!!.isChecked = false
        }
    }

    fun switchAnimationSetChecked(isChecked: Boolean) {
        switchAnimation!!.isChecked = isChecked
    }

    fun switchTouchCellsSetChecked(isChecked: Boolean) {
        switchTouchCells!!.isChecked = isChecked
    }

    fun switchTwoTablesSetChecked(isChecked: Boolean) {
        switchTwoTables!!.isChecked = isChecked
    }

    fun switchMoveHintSetChecked(isChecked: Boolean) {
        switchMoveHint!!.isChecked = isChecked
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).visibilityBottomNavigationView(View.VISIBLE)
    }
}