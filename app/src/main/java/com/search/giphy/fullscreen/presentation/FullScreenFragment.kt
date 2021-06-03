package com.search.giphy.fullscreen.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import com.search.giphy.R
import com.search.giphy.databinding.FragmentFullScreenBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FullScreenFragment : Fragment(R.layout.fragment_full_screen) {

    private val args: FullScreenFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentFullScreenBinding.bind(view)

        binding.imageViewFullscreen.load(args.gif.highRes)
    }
}