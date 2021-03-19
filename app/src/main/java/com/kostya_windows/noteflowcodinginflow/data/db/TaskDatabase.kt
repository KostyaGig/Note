package com.kostya_windows.noteflowcodinginflow.data.db

import android.util.Log
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.kostya_windows.noteflowcodinginflow.data.model.Task
import com.kostya_windows.noteflowcodinginflow.data.dao.TaskDao
import com.kostya_windows.noteflowcodinginflow.di.modules.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(
    entities = [Task::class],
    version = 1,
    exportSchema = false
)
abstract class TaskDatabase: RoomDatabase() {

    abstract fun getTaskDao(): TaskDao

    class MyCallBack @Inject constructor(
        private val database: Provider<TaskDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            Log.d("database","ONCREATE")
            applicationScope.launch {
                database.get().getTaskDao().insert(
                    Task(
                        "This is text",
                        important = true
                    )
                )
                //default important = false,completed = false
                database.get().getTaskDao().insert(
                    Task(
                        "This is text"
                    )
                )
                database.get().getTaskDao().insert(
                    Task(
                        "This is text",
                        important = true
                    )
                )
                //default important = false,completed = false
                database.get().getTaskDao().insert(
                    Task(
                        "This is text"
                    )
                )
                database.get().getTaskDao().insert(
                    Task(
                        "This idgds text"
                    )
                )
                database.get().getTaskDao()
                    .insert(
                        Task(
                            "This is text",
                            important = true,
                            completed = true
                        )
                    )

            }
        }
    }
}
