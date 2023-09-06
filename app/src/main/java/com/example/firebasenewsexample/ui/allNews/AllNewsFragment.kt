package com.example.firebasenewsexample.ui.allNews

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.firebasenewsexample.R
import com.example.firebasenewsexample.data.adapter.AllNewsAdapter
import com.example.firebasenewsexample.data.state.AllNewsState
import com.example.firebasenewsexample.databinding.FragmentAllNewsBinding
import com.example.firebasenewsexample.ui.dashboard.DashBoardFragment.Companion.USER_ID
import kotlinx.coroutines.launch


class AllNewsFragment : Fragment(R.layout.fragment_all_news) {

    private lateinit var binding:FragmentAllNewsBinding
    private val viewModel : AllNewsViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAllNewsBinding.bind(view)

        if(arguments==null){
            viewModel.getAllNews(null)
        }
        else{
            viewModel.getAllNews(arguments?.getString(USER_ID).toString())
        }

        observeAllNewsState()

    }




    private fun observeAllNewsState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                viewModel.allNewsState.collect{
                    when(it){
                        is AllNewsState.Idle->{}
                        is AllNewsState.Loading->{}
                        is AllNewsState.Empty->{}
                        is AllNewsState.Result->{
                            binding.rvNews.adapter=AllNewsAdapter(requireContext(),it.news){

                            }
                        }
                        is AllNewsState.Error->{}
                    }
                }
            }
        }
    }



}