package com.agustin.todoapp.presentation.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.agustin.todoapp.R
import com.agustin.todoapp.data.dataresources.firebase.FirebaseDataResource
import com.agustin.todoapp.domain.entities.Note
import com.agustin.todoapp.domain.repository.NoteRepositoryImpl
import com.agustin.todoapp.presentation.view.recyclerviewadapters.RvDetailsAdapter
import com.agustin.todoapp.presentation.viewmodel.NoteDetailsViewModel
import com.agustin.todoapp.tools.DataResources
import kotlinx.android.synthetic.main.fragment_note_details.*


class NoteDetailsFragment : Fragment() {

    private val model: NoteDetailsViewModel by viewModels()
    private lateinit var noteId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        arguments?.let {
            noteId = it.getString("note")!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("bundle", noteId)

        model.repository = NoteRepositoryImpl(FirebaseDataResource)
        model.getNote(noteId).observe(viewLifecycleOwner){
            when(it){
                is DataResources.Loading -> pb_note_details.visibility = View.VISIBLE
                is DataResources.Success -> {
                    pb_note_details.visibility = View.GONE
                    val note: Note = it.data as Note
                    txt_note_details_title.text = note.title
                    val tasks: List<HashMap<String, Any>> = note.tasks as List<HashMap<String, Any>>
                    Log.d("bundle", tasks[0]["completed"].toString())
                    val adapter = RvDetailsAdapter(tasks, requireContext())
                    rv_note_details_tasks.layoutManager = LinearLayoutManager(requireContext())
                    rv_note_details_tasks.adapter = adapter

                    btn_note_details_save_changes.setOnClickListener {
                        val noteUpdated = Note(noteId, adapter.checkedTasks)
                        model.updateData(noteUpdated)
                        findNavController().popBackStack()
                    }
                }

                is DataResources.Failure -> {
                    pb_note_details.visibility = View.GONE
                    Toast.makeText(requireContext(), R.string.default_error, Toast.LENGTH_SHORT).show()
                }
            }
        }


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.note_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_item_delete_note) {
            model.deleteNote(noteId)
            findNavController().popBackStack()
        }
        return super.onOptionsItemSelected(item)
    }
}