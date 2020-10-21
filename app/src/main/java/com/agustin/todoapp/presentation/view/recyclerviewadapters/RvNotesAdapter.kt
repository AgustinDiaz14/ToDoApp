package com.agustin.todoapp.presentation.view.recyclerviewadapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.agustin.todoapp.R
import com.agustin.todoapp.domain.entities.Note
import com.agustin.todoapp.domain.entities.NoteTask
import kotlinx.android.synthetic.main.notes_item_row.view.*

class RvNotesAdapter(private var dataList: List<Note>, private val context: Context, private val onClick: OnItemClick) :
    RecyclerView.Adapter<RvNotesAdapter.ViewHolderNotes>() {

    fun interface OnItemClick{
        fun onNoteClick(note: Note)
    }
    fun setDataList(note: List<Note>){
        dataList = note
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderNotes {
        val view = LayoutInflater.from(context).inflate(R.layout.notes_item_row, parent, false)
        return ViewHolderNotes(view)
    }

    override fun onBindViewHolder(holder: ViewHolderNotes, position: Int) {
        val data = dataList[position]
        holder.bindView(data)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolderNotes(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(data: Note){
            itemView.apply {
                txt_notes_item_row_note_title.text = data.title
                setOnClickListener {
                    onClick.onNoteClick(data)
                }

            }
        }
    }
}