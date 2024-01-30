package ru.schultetabledima.schultetable.screens.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import ru.schultetabledima.schultetable.R
import ru.schultetabledima.schultetable.common.BaseScreenFragment
import ru.schultetabledima.schultetable.databinding.FragmentSettingsBinding
import ru.schultetabledima.schultetable.screens.main.MainActivity

class SettingsFragment : BaseScreenFragment(R.layout.fragment_settings), View.OnClickListener {

    private val settingsPresenter = SettingsPresenter(this)
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance(): SettingsFragment {
            return SettingsFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        switchesSetOnClickListener()
        customizationViewPager()

        //Запуск презентера должен быть в конце, чтобы ViewPager создался с настройками по умолчанию.
        //После идет настройка через Презетер
        settingsPresenter.start()
    }

    private fun switchesSetOnClickListener() {
        with(binding) {
            switchAnimation.setOnClickListener(this@SettingsFragment)
            switchTouchCells.setOnClickListener(this@SettingsFragment)
            switchTwoTables.setOnClickListener(this@SettingsFragment)
            switchMoveHint.setOnClickListener(this@SettingsFragment)
        }
    }

    private fun customizationViewPager() {
        binding.viewPager.isSaveEnabled = false

        val numbersFragment: NumbersFragment = NumbersFragment.newInstance()
        val lettersFragment: LettersFragment = LettersFragment.newInstance()

        val fragments: List<Fragment> = listOf(numbersFragment, lettersFragment)

        val indexOfNumbers = fragments.indexOf(numbersFragment)
        val indexOfLetters = fragments.indexOf(lettersFragment)

        val pageAdapter = MyAdapter(requireActivity())

        pageAdapter.setListFragments(fragments)
        binding.viewPager.adapter = pageAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            if (position == indexOfNumbers)
                tab.setText(R.string.numbers)
            if (position == indexOfLetters)
                tab.setText(R.string.letters)
        }.attach()

        binding.tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                settingsPresenter.onTabSelectedListener(tab.position)
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    fun setViewPagerCurrentItem(index: Int) {
        binding.viewPager.setCurrentItem(index, false)
    }

    override fun onClick(v: View) {
        settingsPresenter.onClickListenerSwitch(v.id, (v as SwitchMaterial).isChecked)
    }

    fun customizationSwitchMoveHint(isEnabled: Boolean, isChecked: Boolean) {
        binding.switchMoveHint.isEnabled = isEnabled
        if (isEnabled) {
            binding.switchMoveHint.isChecked = isChecked
        } else {
            binding.switchMoveHint.isChecked = false
        }
    }

    fun switchAnimationSetChecked(isChecked: Boolean) {
        binding.switchAnimation.isChecked = isChecked
    }

    fun switchTouchCellsSetChecked(isChecked: Boolean) {
        binding.switchTouchCells.isChecked = isChecked
    }

    fun switchTwoTablesSetChecked(isChecked: Boolean) {
        binding.switchTwoTables.isChecked = isChecked
    }

    fun switchMoveHintSetChecked(isChecked: Boolean) {
        binding.switchMoveHint.isChecked = isChecked
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).visibilityBottomNavigationView(View.VISIBLE)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}