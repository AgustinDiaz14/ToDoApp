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
import com.agustin.todoapp.presentation.viewmodel.CreateUserViewModel
import com.agustin.todoapp.tools.DataResources
import kotlinx.android.synthetic.main.fragment_create_user.*

class CreateUserFragment : Fragment() {

    private val model: CreateUserViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model.repository = UserRepositoryImpl(FirebaseDataResource)


        btn_create_user.setOnClickListener {
            val email = etxt_create_user_email.text
            val firstPassword = etxt_create_user_first_password.text
            val secondPassword = etxt_create_user_second_password.text

            if(firstPassword.toString() == secondPassword.toString()){
                model.createUser(email.toString(), firstPassword.toString()).observe(viewLifecycleOwner){
                    when(it){
                        is DataResources.Loading -> {
                            pb_create_user.visibility = View.VISIBLE
                            btn_create_user.isEnabled = false
                        }

                        is DataResources.Success ->{
                            pb_create_user.visibility = View.GONE
                            findNavController().popBackStack()
                        }

                        is DataResources.Failure -> {
                            pb_create_user.visibility = View.GONE
                            btn_create_user.isEnabled = true
                            when (it.error.localizedMessage) {
                                "The email address is badly formatted." -> Toast.makeText(
                                    requireContext(),
                                    R.string.wrong_email,
                                    Toast.LENGTH_SHORT
                                ).show()
                                "The given password is invalid. [ Password should be at least 6 characters ]" -> Toast.makeText(
                                    requireContext(),
                                    R.string.wrong_password_size,
                                    Toast.LENGTH_SHORT
                                ).show()

                                "Given String is empty or null" -> Toast.makeText(
                                    requireContext(),
                                    R.string.complete_fields,
                                    Toast.LENGTH_SHORT
                                ).show()
                                "The email address is already in use by another account." -> Toast.makeText(
                                    requireContext(),
                                    R.string.existent_user,
                                    Toast.LENGTH_SHORT
                                ).show()
                                else -> Toast.makeText(
                                    requireContext(),
                                    R.string.default_error,
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }

                        }
                    }
                }
            }
            
            else{
                pb_create_user.visibility = View.GONE
                Toast.makeText(requireContext(), R.string.equal_passwords, Toast.LENGTH_SHORT).show()
            }
        }

    }

}