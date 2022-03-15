package com.playsdev.testsecond.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.playsdev.testsecond.databinding.FragmentDetailsBinding
import com.squareup.picasso.Picasso

class DetailsFragment(private val image: String) : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = checkNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Picasso.get()
            .load(image)
            .fit()
            .centerCrop()
            .into(binding.imageView2)

    }



}