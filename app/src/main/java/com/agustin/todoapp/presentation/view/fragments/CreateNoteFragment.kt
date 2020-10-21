package com.agustin.todoapp.presentation.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.agustin.todoapp.R
import com.agustin.todoapp.data.dataresources.firebase.FirebaseDataResource
import com.agustin.todoapp.domain.entities.Note
import com.agustin.todoapp.domain.entities.NoteTask
import com.agustin.todoapp.domain.repository.NoteRepositoryImpl
import com.agustin.todoapp.presentation.view.recyclerviewadapters.RvTasksAdapter
import com.agustin.todoapp.presentation.viewmodel.CreateNoteViewModel
import com.agustin.todoapp.tools.DataResources
import kotlinx.android.synthetic.main.fragment_create_note.*


class CreateNoteFragment : Fragment() {

    private val model: CreateNoteViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = RvTasksAdapter(mutableListOf(), requireContext())
        rv_create_note_tasks.layoutManager = LinearLayoutManager(requireContext())
        rv_create_note_tasks.adapter = adapter
        model.repository = NoteRepositoryImpl(FirebaseDataResource)

        btn_create_note_add_task.setOnClickListener {
            val content = etxt_create_note_add_task.text
            if (content.isNotEmpty()) {
                adapter.apply {
                    addTask(NoteTask(content.toString(), false))
                    notifyDataSetChanged()
                }
                etxt_create_note_add_task.setText("")
            } else {
                return@setOnClickListener
            }
        }

        btn_create_note_add_note.setOnClickListener {
            model.createNote(Note(etxt_create_note_title.text.toString(), adapter.getTasks()))
                .observe(
                    viewLifecycleOwner,
                    {
                        when (it) {
                            is DataResources.Loading -> {
                                pb_create_note.visibility = View.VISIBLE
                                btn_create_note_add_note.isEnabled = false
                            }

                            is DataResources.Success -> findNavController().popBackStack()

                            is DataResources.Failure -> {
                                pb_create_note.visibility = View.GONE
                                Toast.makeText(
                                    requireContext(),
                                    R.string.default_error,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    })
        }
    }
}