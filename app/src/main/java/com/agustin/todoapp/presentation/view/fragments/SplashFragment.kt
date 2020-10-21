package com.agustin.todoapp.presentation.view.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.agustin.todoapp.R
import com.agustin.todoapp.data.dataresources.firebase.FirebaseDataResource
import com.agustin.todoapp.domain.repository.UserRepositoryImpl
import com.agustin.todoapp.presentation.viewmodel.SplashViewModel
import com.agustin.todoapp.tools.DataResources
import kotlinx.android.synthetic.main.fragment_splash.*

class SplashFragment : Fragment() {
    private val model: SplashViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.repository = UserRepositoryImpl(FirebaseDataResource)

        pb_splash.visibility = View.VISIBLE
        Handler().postDelayed({
            model.checkUser().observe(viewLifecycleOwner) {
                when (it) {
                    is DataResources.Loading -> {}

                    is DataResources.Success -> {
                        pb_splash.visibility = View.GONE
                        if (it.data as Boolean) findNavController().navigate(R.id.action_splashFragment_to_notesFragment)
                        else findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
                    }

                    is DataResources.Failure -> {
                        pb_splash.visibility = View.GONE
                        Toast.makeText(requireContext(), R.string.default_error, Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
                    }
                }
            }
        }, 3000L)


    }
}