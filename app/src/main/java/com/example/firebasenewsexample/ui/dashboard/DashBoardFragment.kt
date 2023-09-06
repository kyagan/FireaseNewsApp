package com.example.firebasenewsexample.ui.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.firebasenewsexample.R
import com.example.firebasenewsexample.data.adapter.UserListAdapter
import com.example.firebasenewsexample.data.model.getFullNameOrEmail
import com.example.firebasenewsexample.data.state.UserListState
import com.example.firebasenewsexample.databinding.FragmentDashBoardBinding
import kotlinx.coroutines.launch



class DashBoardFragment : Fragment(R.layout.fragment_dash_board) {

    companion object{
        const val USER_ID = "userId"
    }

    private lateinit var binding: FragmentDashBoardBinding
    private val viewModel: DashBoardViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDashBoardBinding.bind(view)


        viewModel.getUsers()
        initListeners()
        observeUserListState()

    }

    private fun initListeners() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getUsers()
        }
        binding.btnAddNews.setOnClickListener {
            findNavController().navigate(R.id.action_dashBoardFragment_to_addNewsFragment)
        }
        binding.btnShowAllNews.setOnClickListener {
            findNavController().navigate(R.id.action_dashBoardFragment_to_allNewsFragment)
        }
        binding.btnProfile.setOnClickListener {
            findNavController().navigate(R.id.action_dashBoardFragment_to_profileFragment2)

        }
    }

    private fun observeUserListState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                viewModel.userListState.collect{
                    binding.swipeRefresh.isRefreshing = false
                    println("gelen user list state: $it")
                    when(it){
                        is UserListState.Idle->{}
                        is UserListState.Loading->{
                            binding.rvUsers.isVisible = false
                            binding.progressBar.isVisible = true
                            binding.ivEmpty.isVisible=false
                        }
                        is UserListState.Empty->{
                            binding.ivEmpty.isVisible = true
                            binding.rvUsers.isVisible = false
                            binding.progressBar.isVisible = false
                        }
                        is UserListState.Result->{
                            binding.ivEmpty.isVisible = false
                            binding.rvUsers.isVisible = true
                            binding.progressBar.isVisible = false

                            binding.rvUsers.adapter = UserListAdapter(requireContext(), it.users){user->
                                findNavController().navigate(R.id.action_dashBoardFragment_to_allNewsFragment,
                                    bundleOf(USER_ID to user.id)
                                )
                            }
                        }
                        is UserListState.Error->{
                            binding.ivEmpty.setImageResource(R.drawable.ic_error)
                            binding.ivEmpty.isVisible = true
                            binding.rvUsers.isVisible = false
                            binding.progressBar.isVisible = false
                        }
                    }
                }
            }
        }
    }


}