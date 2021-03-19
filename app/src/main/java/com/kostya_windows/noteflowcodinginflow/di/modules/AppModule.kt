package com.kostya_windows.noteflowcodinginflow.di.modules

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.kostya_windows.noteflowcodinginflow.data.dao.TaskDao
import com.kostya_windows.noteflowcodinginflow.data.db.TaskDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {


    @Provides
    fun provideTaskDatabase(@ApplicationContext context: Context,callBack:TaskDatabase.MyCallBack): TaskDatabase{
        return Room.databaseBuilder(context as Application,TaskDatabase::class.java,"task database")
            .fallbackToDestructiveMigration()
            .addCallback(callBack)
            .build()

    }

    @Provides
    fun provideDao(database: TaskDatabase): TaskDao {
        return database.getTaskDao()
    }

    //provide Coroutine
    //Custom annotation
    @ApplicationScope
    @Provides
    fun provideApplicationScope(): CoroutineScope {
        return CoroutineScope(SupervisorJob())
    }

}

//Custom annotation
@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope{

}