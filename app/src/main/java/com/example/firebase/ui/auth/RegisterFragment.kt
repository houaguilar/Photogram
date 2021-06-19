package com.example.firebase.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.firebase.R
import com.example.firebase.core.Resource
import com.example.firebase.data.remote.auth.AuthDataSource
import com.example.firebase.databinding.FragmentRegisterBinding
import com.example.firebase.domain.auth.AuthRepoImpl
import com.example.firebase.presentation.auth.AuthViewModel
import com.example.firebase.presentation.auth.AuthViewModelFactory
import com.google.firebase.auth.FirebaseAuth

class RegisterFragment : Fragment(R.layout.fragment_register) {

    private lateinit var binding: FragmentRegisterBinding
    private val viewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory(
            AuthRepoImpl(
                AuthDataSource()
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)
        signUp()
    }

    private fun signUp() {
        binding.btnSignup.setOnClickListener {
            val username = binding.editTextUsername.text.toString().trim()
            val email = binding.editTextEmail.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()
            val confirmPassword = binding.editTextconfirmPassword.text.toString().trim()

            validateUserData(username, email, password, confirmPassword)

            createUser(email, password, username)
        }
    }

    private fun createUser(email: String, password: String, username: String) {
        viewModel.signUp(email, password, username).observe(viewLifecycleOwner, Observer { result ->
            when(result) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.btnSignup.isEnabled = false
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                }
                is Resource.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    binding.btnSignup.isEnabled = true
                    Toast.makeText(requireContext(), "Error: ${result.exception}", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun validateUserData(
        username: String,
        email: String,
        password: String,
        confirmPassword: String
    ) {
        if (username.isEmpty()) {
            binding.editTextUsername.error = "Username is empty"
            return
        }

        if (email.isEmpty()) {
            binding.editTextEmail.error = "Email is empty"
            return
        }

        if (password.isEmpty()) {
            binding.editTextPassword.error = "Password is empty"
            return
        }

        if (confirmPassword.isEmpty()) {
            binding.editTextconfirmPassword.error = "Password is empty"
            return
        }

        if (password != confirmPassword) {
            binding.editTextconfirmPassword.error = "Password does not match"
            binding.editTextPassword.error = "Password does not match"
            return
        }
    }
}