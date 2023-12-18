package ru.schultetabledima.schultetable.screens.statistic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
//import dagger.hilt.android.lifecycle.HiltViewModel
import ru.schultetabledima.schultetable.database.Result
import javax.inject.Inject

//@HiltViewModel
class StatisticViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel(){

    private var _list = MutableLiveData<List<Result>>()
    val categories: LiveData<List<Result>> = _list

    private fun getList() {
//        GlobalScope.launch {
//            _list.postValue(repository.getList())
//        }
    }

}