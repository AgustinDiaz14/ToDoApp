package com.agustin.todoapp.presentation.view.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.agustin.todoapp.R
import com.agustin.todoapp.data.dataresources.firebase.FirebaseDataResource
import com.agustin.todoapp.domain.entities.Note
import com.agustin.todoapp.domain.repository.NoteRepositoryImpl
import com.agustin.todoapp.domain.repository.UserRepositoryImpl
import com.agustin.todoapp.presentation.view.recyclerviewadapters.RvNotesAdapter
import com.agustin.todoapp.presentation.viewmodel.NotesViewModel
import com.agustin.todoapp.tools.DataResources
import kotlinx.android.synthetic.main.fragment_notes.*

class NotesFragment : Fragment() {

    private val model: NotesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.noteRepository = NoteRepositoryImpl(FirebaseDataResource)
        model.userRepository = UserRepositoryImpl(FirebaseDataResource)

        val adapter = RvNotesAdapter(mutableListOf(), requireContext()) {
            val bundle = Bundle()
            bundle.putString("note", it.title)
            findNavController().navigate(R.id.action_notesFragment_to_noteDetailsFragment, bundle)
        }
        rv_notes.layoutManager = LinearLayoutManager(requireContext())
        rv_notes.adapter = adapter
        rv_notes.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        model.getNotes().observe(viewLifecycleOwner,
            {
                when (it) {
                    is DataResources.Loading -> pb_notes.visibility = View.VISIBLE

                    is DataResources.Success -> {
                        val list = it.data as List<Note>
                        pb_notes.visibility = View.GONE
                        adapter.setDataList(list.toMutableList())
                        adapter.notifyDataSetChanged()
                    }

                    is DataResources.Failure -> {
                        pb_notes.visibility = View.GONE
                        Toast.makeText(requireContext(), R.string.default_error, Toast.LENGTH_SHORT).show()
                    }

                }
            })
        fbtn_notes_add.setOnClickListener {
            findNavController().navigate(R.id.action_notesFragment_to_createNoteFragment)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.user_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_item_log_out){
            model.signOut()
            findNavController().navigate(R.id.action_notesFragment_to_loginFragment)
        }
        return super.onOptionsItemSelected(item)

    }
}