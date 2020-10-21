package com.agustin.todoapp.presentation.view.recyclerviewadapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.agustin.todoapp.R
import com.agustin.todoapp.domain.entities.NoteTask
import kotlinx.android.synthetic.main.task_item_row.view.*

class RvDetailsAdapter(
    private val tasks: List<HashMap<String, Any>>,
    private val context: Context
) : RecyclerView.Adapter<RvDetailsAdapter.DetailsViewHolder>() {

    val checkedTasks: MutableList<NoteTask> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.task_item_row, parent, false)
        return DetailsViewHolder(view)
    }

    override fun onBindViewHolder(holder: DetailsViewHolder, position: Int) {
        val task = NoteTask(tasks[position]["content"].toString(), tasks[position]["completed"] as Boolean)
        checkedTasks.add(task)
        holder.bind(task)
        val checkBox = holder.itemView.checkbox_task
        checkBox.setOnClickListener {
            checkedTasks[position].isCompleted = checkBox.isChecked
        }


    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    inner class DetailsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(task: NoteTask){
            itemView.apply{
                checkbox_task.text = task.content
                checkbox_task.isChecked = task.isCompleted
            }
        }
    }
}