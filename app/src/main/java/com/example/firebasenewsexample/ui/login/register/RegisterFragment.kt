package com.example.firebasenewsexample.ui.login.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.firebasenewsexample.R
import com.example.firebasenewsexample.data.state.RegisterState
import com.example.firebasenewsexample.databinding.FragmentRegisterBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch


class RegisterFragment : Fragment(R.layout.fragment_register) {
    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: RegisterViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)
        initListeners()
        observeRegisterState()
        observeMessageState()
    }

    private fun observeMessageState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.message.collect{
                    Snackbar.make(binding.registerSingUp,it,Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun observeRegisterState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.registerState.collect {
                    when (it) {
                        RegisterState.Idle -> {}
                        RegisterState.Loading -> {}
                        RegisterState.Success -> {
                            findNavController().navigate(R.id.action_registerFragment_to_dashBoardFragment)
                        }

                        is RegisterState.Error -> {
                            Snackbar.make(
                                binding.registerSingUp,
                                "Something went wrong: ${it.throwable?.message}",
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }

    private fun initListeners() {
        binding.registerSingUp.setOnClickListener {
            viewModel.register(
                binding.registerEmail.text.toString(),
                binding.registerPass.text.toString(),
                binding.registerPassAgain.text.toString()
            )
        }
    }
}