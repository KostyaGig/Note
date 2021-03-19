package com.kostya_windows.noteflowcodinginflow.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kostya_windows.noteflowcodinginflow.data.model.Task
import com.kostya_windows.noteflowcodinginflow.databinding.TaskItemBinding


class TaskAdapter : ListAdapter<Task,TaskAdapter.TaskViewHolder>(DiffCallback()) {


    class TaskViewHolder(private val binding: TaskItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(task: Task) {


            binding.apply {
                completedTaskCheckBox.isChecked = task.completed
                textTextView.text = task.text

                textTextView.paint.isStrikeThruText = task.completed

                //Показываем изображение important если таск important
                if (task.important){
                    priorityImageView.visibility = View.VISIBLE
                } else {
                    priorityImageView.visibility = View.INVISIBLE
                }

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskAdapter.TaskViewHolder {
        return TaskViewHolder(TaskItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    //dif list callback
    class DiffCallback: DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            //Можно сверять id тасков и если id добавления например новой таски не совпадает с id которые были до добавленмия то это значит что мы добавили новую таску и метод вернет нам false => обновит список
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            //Сверит содержимое всех itemов и если мы обновляли таску,то данный метод вернет нем false и обновит таску
            return oldItem == newItem
        }

    }

}