package com.kostya_windows.noteflowcodinginflow.data.dao

import androidx.room.*
import com.kostya_windows.noteflowcodinginflow.data.datastore.SortOrder
import com.kostya_windows.noteflowcodinginflow.data.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Insert
    suspend fun insert(task : Task)

    @Update
    suspend fun update(task : Task)

    @Delete
    suspend fun delete(task : Task)

    @Query("SELECT * FROM task_table")
    fun getAllTasks(): Flow<List<Task>>

    //Если что то у меня name это переменная текст в entity,a у кодинг ин флоу это name,метод сортед по имени сортирует в алфавитном порядке
    //Эти мы будем использовать один из этих методов в getTask() в зависимости от того как мы сортируем таски по имени или дате создания
    //Эти методы также проверяют скрыть выполненные таски или нет и срзау работаю по поиску в алфавитном порядке
    @Query("SELECT * FROM task_table WHERE (completed != :hideCompleted OR completed = 0) AND text LIKE '%' || :searchQuery || '%' ORDER BY important DESC, text")
    fun getTasksSortedByName(searchQuery: String,hideCompleted: Boolean): Flow<List<Task>>

    @Query("SELECT * FROM task_table WHERE (completed != :hideCompleted OR completed = 0) AND text LIKE '%' || :searchQuery || '%' ORDER BY important DESC, created")
    fun getTasksSortedByDateCreated(searchQuery: String,hideCompleted: Boolean): Flow<List<Task>>

    fun getTasks(
        searchQuery: String,
        sortOrder: com.kostya_windows.noteflowcodinginflow.data.datastore.SortOrder,
        hideCompleted: Boolean): Flow<List<Task>> {
        //Проверка по чему сортируем и юзаем один из метоов выше
       return when(sortOrder){
            SortOrder.BY_DATE -> getTasksSortedByDateCreated(searchQuery,hideCompleted)
            SortOrder.BY_NAME -> getTasksSortedByName(searchQuery,hideCompleted)
        }
    }

}