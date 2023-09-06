package com.example.firebasenewsexample.ui.login.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.firebasenewsexample.R
import com.example.firebasenewsexample.data.state.LoginState
import com.example.firebasenewsexample.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch


class LoginFragment : Fragment(R.layout.fragment_login) {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)
        initListener()
        observeLoginState()
        observeMessage()
    }

    private fun observeMessage() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.message.collect{
                    Snackbar.make(
                        binding.login,
                        it,
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun observeLoginState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loginState.collect {
                    stateForViews(it is LoginState.Loading)
                    when (it) {
                        LoginState.Idle -> {}
                        LoginState.UserNotFound -> {
                            Snackbar.make(binding.login, "User Not Found", Snackbar.LENGTH_SHORT)
                                .setAction("SignUp") {
                                    findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
                                }.show()
                        }

                        LoginState.Success -> {
                            findNavController().navigate(R.id.action_loginFragment_to_dashBoardFragment)
                        }

                        is LoginState.Error -> {
                            Snackbar.make(
                                binding.login,
                                "Something went wrond",
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }

                        LoginState.Loading -> {}
                    }
                }
            }
        }
    }

    private fun stateForViews(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
        binding.login.isVisible = !isLoading
        binding.email.isEnabled = !isLoading
        binding.pass.isEnabled = !isLoading
    }

    private fun initListener() {
        binding.singUp.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        binding.forgotPass.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgotFragment)
        }
        binding.login.setOnClickListener {
            viewModel.login(binding.email.text.toString(), binding.pass.text.toString())
        }
    }

}