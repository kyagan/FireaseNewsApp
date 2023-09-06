package com.example.firebasenewsexample.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import com.example.firebasenewsexample.R
import com.example.firebasenewsexample.data.state.ProfilePhotoUpdateState
import com.example.firebasenewsexample.data.state.ProfileState
import com.example.firebasenewsexample.databinding.FragmentProfileBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel:ProfileViewModel by activityViewModels()

    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()){uri ->
        if(uri !=null){
            binding.ivUserProfileImage.load(uri)
            viewModel.uploadProfileImage(uri)
        }
        else{
            // media not selected
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)

        viewModel.showProfile()
        observeProfileState()
        observeProfileImageUpdateState()
        initListeners()

    }

    private fun observeProfileImageUpdateState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                viewModel.profilePhotoUpdateState.collect{
                    binding.pbImageUpload.isVisible = it is ProfilePhotoUpdateState.Progress
                    when(it){
                        is ProfilePhotoUpdateState.Idle->{}
                        is ProfilePhotoUpdateState.Loading->{}
                        is ProfilePhotoUpdateState.Success->{
                            Snackbar.make(binding.etUserName,"Image uploaded",Snackbar.LENGTH_SHORT).show()
                        }
                        is ProfilePhotoUpdateState.Progress->{
                            binding.pbImageUpload.progress = it.progress
                        }
                        is ProfilePhotoUpdateState.Error->{
                            Snackbar.make(binding.etUserName,"Something went wrong",Snackbar.LENGTH_SHORT).show()

                        }
                    }
                }
            }
        }
    }

    private fun initListeners() {
        binding.ivUserProfileImage.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))

        }
        binding.btnUpdate.setOnClickListener {
            viewModel.updateProfile(binding.etUserName.text.toString(),binding.etUserSurname.text.toString())
        }
    }

    private fun observeProfileState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                viewModel.profileState.collect{
                    when(it){
                        is ProfileState.Idle->{}
                        is ProfileState.Loading->{
                            binding.ivUserProfileImage.background = ContextCompat.getDrawable(requireContext(),R.drawable.user)
                        }
                        is ProfileState.Result->{
                            binding.ivUserProfileImage.load(it.user!!.profileImageUrl)
                            binding.etUserName.setText(it.user.name)
                            binding.etUserSurname.setText(it.user.surName)
                        }
                        is ProfileState.Error->{}
                    }
                }
            }
        }
    }


}
