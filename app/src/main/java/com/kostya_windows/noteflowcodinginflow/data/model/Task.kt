package com.kostya_windows.noteflowcodinginflow.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.text.DateFormat

@Entity(tableName = "task_table")
data class Task(
    val text:String,
    val important:Boolean = false,
    val completed:Boolean = false,
    val created:Long = System.currentTimeMillis(),
    @PrimaryKey(autoGenerate = true) val id:Int = 0
    ):Serializable {

    val createdDateFormat:String
    get() = DateFormat.getDateInstance().format(created)

}