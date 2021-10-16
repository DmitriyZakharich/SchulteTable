package ru.schultetabledima.schultetable.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import ru.schultetabledima.schultetable.R
import ru.schultetabledima.schultetable.advice.AdviceActivity
import ru.schultetabledima.schultetable.databinding.ActivityMainBinding
import ru.schultetabledima.schultetable.settings.SettingsActivity
import ru.schultetabledima.schultetable.statistic.StatisticsActivity
import ru.schultetabledima.schultetable.table.mvp.view.TableActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tableButton.setOnClickListener(this)
        binding.statisticsButton.setOnClickListener(this)
        binding.customizationButton.setOnClickListener(this)
        binding.adviceButton.setOnClickListener(this)
        binding.aboutProgramButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tableButton -> startActivity(Intent(this, TableActivity::class.java))
            R.id.statisticsButton -> startActivity(Intent(this, StatisticsActivity::class.java))
            R.id.customizationButton -> startActivity(Intent(this, SettingsActivity::class.java))
            R.id.adviceButton -> startActivity(Intent(this, AdviceActivity::class.java))
        }
    }
}