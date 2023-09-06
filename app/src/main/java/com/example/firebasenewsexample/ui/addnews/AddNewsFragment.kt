package com.example.firebasenewsexample.ui.addnews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import coil.load
import com.example.firebasenewsexample.Constants
import com.example.firebasenewsexample.R
import com.example.firebasenewsexample.data.model.News
import com.example.firebasenewsexample.databinding.FragmentAddNewsBinding
import com.example.firebasenewsexample.ui.dashboard.DashBoardFragment.Companion.USER_ID
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot


class AddNewsFragment : Fragment(R.layout.fragment_add_news) {
    private lateinit var binding: FragmentAddNewsBinding
    private val viewModel: AddNewsViewModel by activityViewModels()

    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(3)){ uris ->
        uris.forEach {
            val imageView = ImageView(requireContext())
            imageView.setImageURI(it)
            imageView.layoutParams = LinearLayout.LayoutParams(200,200)
            binding.llNewsImages.addView(imageView)
            viewModel.setNewsPhotos(uris)
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddNewsBinding.bind(view)



        binding.ivNewsImage.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))

        }

        binding.btnSave.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val content = binding.etContent.text.toString()
            viewModel.addNews(title,content)

        }
    }
}