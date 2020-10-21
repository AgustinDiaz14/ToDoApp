package com.agustin.todoapp.presentation.view.recyclerviewadapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.agustin.todoapp.R
import com.agustin.todoapp.domain.entities.NoteTask
import kotlinx.android.synthetic.main.task_item_row.view.*

class RvTasksAdapter(private val data: MutableList<NoteTask>, private val context: Context) :
    RecyclerView.Adapter<RvTasksAdapter.TasksViewHolder>() {

    fun addTask(noteTask: NoteTask) {
        data.add(noteTask)
    }

    fun getTasks(): MutableList<NoteTask> = data

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.task_item_row, parent, false)
        return TasksViewHolder(view)
    }

    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
        val task = data[position]
        holder.bind(task)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class TasksViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(task: NoteTask) {
            itemView.apply {
                checkbox_task.text = task.content
                checkbox_task.isChecked = task.isCompleted
            }
        }
    }
}