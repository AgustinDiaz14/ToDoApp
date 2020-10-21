package com.agustin.todoapp.presentation.view.fragments

import android.os.Bundle
import android.util.Log
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
import com.agustin.todoapp.domain.entities.User
import com.agustin.todoapp.presentation.viewmodel.LoginViewModel
import com.agustin.todoapp.tools.DataResources
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {
    private val model: LoginViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.repository = UserRepositoryImpl(FirebaseDataResource)

        btn_login_create_user.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_createUserFragment)
        }

        btn_login.setOnClickListener {
            val email = etxt_login_email.text
            val password = etxt_login_password.text
            Log.d("login", "$email, $password")
            model.logIn(User(email.toString(), password.toString()))
                .observe(viewLifecycleOwner, {
                    when (it) {
                        is DataResources.Loading -> {
                            btn_login.isEnabled = false
                            btn_login_create_user.isEnabled = false
                            pb_login.visibility = View.VISIBLE
                        }

                        is DataResources.Success -> {
                            pb_login.visibility = View.GONE
                            findNavController().navigate(R.id.action_loginFragment_to_notesFragment)
                        }

                        is DataResources.Failure -> {
                            btn_login.isEnabled = true
                            btn_login_create_user.isEnabled = true
                            pb_login.visibility = View.GONE
                            Log.d("errors", it.error.localizedMessage ?: "no error")
                            when (it.error.localizedMessage) {
                                "Given String is empty or null" -> Toast.makeText(
                                    requireContext(),
                                    R.string.complete_fields,
                                    Toast.LENGTH_SHORT
                                ).show()
                                "The email address is badly formatted." -> Toast.makeText(
                                    requireContext(),
                                    R.string.wrong_email,
                                    Toast.LENGTH_SHORT
                                ).show()
                                "There is no user record corresponding to this identifier. The user may have been deleted." -> Toast.makeText(
                                    requireContext(),
                                    R.string.no_user,
                                    Toast.LENGTH_SHORT
                                ).show()
                                "The password is invalid or the user does not have a password." -> Toast.makeText(
                                    requireContext(),
                                    R.string.email_or_password_incorrect,
                                    Toast.LENGTH_SHORT
                                ).show()
                                else -> Toast.makeText(
                                    requireContext(),
                                    R.string.default_error,
                                    Toast.LENGTH_SHORT
                                ).show()

                            }
                        }
                    }
                })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Fragment", "Destroyed")
    }
}