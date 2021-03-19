package com.kostya_windows.noteflowcodinginflow.ui.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.kostya_windows.noteflowcodinginflow.data.model.Task
import com.kostya_windows.noteflowcodinginflow.data.dao.TaskDao
import com.kostya_windows.noteflowcodinginflow.data.datastore.SortOrder
import com.kostya_windows.noteflowcodinginflow.data.datastore.StoreManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
   private val dao: TaskDao,
   private val storeManager: StoreManager
) : ViewModel(){

    private val searchFlow = MutableStateFlow("")

    val preferencesFlow = storeManager.preferencesFlow //return FLow<FilterPreferences>

    //Меняем один из 3-х вышеперечисл. стэйтфлоу,попадаем сюда,делаем запрос в бд и подписываемся :)
    private val taskFlow = combine(
        searchFlow,
        preferencesFlow
    )
    {
        searchQuery,filterPreferences ->
        Pair(searchQuery,filterPreferences)
    }.flatMapLatest { (searchQuery,filterPreferences) ->
         dao.getTasks(searchQuery,filterPreferences.sortOrder,filterPreferences.hideCompleted)
    }

    //Подписка
    val tasks = taskFlow.asLiveData()

    fun searchTasks(query: String){
        searchFlow.value = query
    }

    fun setOrder(sortOrder: SortOrder)= viewModelScope.launch(Dispatchers.IO) {
            storeManager.updateSortOrder(sortOrder)
        }

    fun setHideCompleted(hide:Boolean) = viewModelScope.launch(Dispatchers.IO) {
            storeManager.updateHideCompleted(hide)
        }

}
