package com.example.firebasenewsexample.ui.forgot

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
import com.example.firebasenewsexample.data.state.ResetPasswordState
import com.example.firebasenewsexample.databinding.FragmentForgotBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class ForgotFragment : Fragment(R.layout.fragment_forgot) {
    private val viewModel:ForgotPasswordViewModel by activityViewModels()
    private lateinit var binding: FragmentForgotBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding=FragmentForgotBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        initListeners()
        observeResetPasswordState()
    }

    private fun observeResetPasswordState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                viewModel.resetPasswordState.collect{
                    when(it){
                        is ResetPasswordState.Idle->{}
                        is ResetPasswordState.Loading->{
                            binding.progressBar.isVisible = true
                        }
                        is ResetPasswordState.Success->{
                            binding.progressBar.isVisible = false
                            binding.ivSuccess.isVisible = true
                            delay(1000)
                            binding.ivSuccess.isVisible = false
                        }
                        is ResetPasswordState.Error->{
                            binding.progressBar.isVisible = false
                        }
                    }
                }
            }
        }
    }

    private fun initListeners() {
        binding.rememberPass.setOnClickListener{
            findNavController().navigate(R.id.action_forgotFragment_to_loginFragment)
        }
        binding.resetMyPass.setOnClickListener {
            viewModel.sendResetPasswordLink(binding.etEmail.text.toString())
        }
    }
}