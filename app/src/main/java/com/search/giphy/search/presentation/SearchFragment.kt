package com.search.giphy.search.presentation

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.search.giphy.R
import com.search.giphy.base.util.onQueryTextChanged
import com.search.giphy.databinding.MainFragmentBinding
import com.search.giphy.search.domain.Gif
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.main_fragment), GifsAdapter.ThumbnailClickListener {

    private val searchViewModel: SearchViewModel by viewModels()

    private lateinit var searchView: SearchView

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private var viewStateJob: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = MainFragmentBinding.bind(view)

        val gifsAdapter = GifsAdapter(this)

        binding.recyclerViewGifs.apply {
            adapter = gifsAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
            addItemDecoration(
                ItemOffsetDecoration(
                    requireContext(),
                    R.dimen.recycler_view_item_offset
                )
            )
        }

        // View States
        searchViewModel.viewStateLiveData.observe(viewLifecycleOwner) { viewState ->
            viewStateJob?.cancel()
            Log.d("SearchFragment", "viewState:  $viewState")
            when (viewState) {
                ViewState.Empty -> showEmptyState()
                is ViewState.Results -> {
                    showResultsState()
                    viewStateJob = viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                        viewState.searchResults.collectLatest {
                            gifsAdapter.submitData(it)
                        }
                    }
                }
            }
        }

        // Navigation Events
        lifecycleScope.launchWhenStarted {
            searchViewModel.navFlow.collectLatest { navigation ->
                when (navigation) {
                    is SearchNavigationEvent.ToFullScreen -> {
                        SearchFragmentDirections
                            .actionSearchFragmentToFullScreenFragment(navigation.gif)
                            .also { findNavController().navigate(it) }
                    }
                }
            }
        }

        setHasOptionsMenu(true)
    }

    private fun showResultsState() {
        binding.apply {
            textViewEmpty.visibility = View.GONE
            recyclerViewGifs.visibility = View.VISIBLE
        }
    }

    private fun showEmptyState() {
        binding.apply {
            recyclerViewGifs.visibility = View.GONE
            textViewEmpty.visibility = View.VISIBLE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search, menu)

        val searchItem = menu.findItem(R.id.action_search)
        searchView = searchItem.actionView as SearchView

        val pendingQuery = searchViewModel.searchQuery
        if (pendingQuery != null && pendingQuery.isNotEmpty()) {
            searchItem.expandActionView()
            searchView.setQuery(pendingQuery, false)
        }

        searchView.onQueryTextChanged {
            Log.d("SearchFragment", it)
            searchViewModel.searchQuery = it
        }
    }

    override fun onClick(gif: Gif) {
        searchViewModel.onThumbnailClick(gif)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewStateJob?.cancel()
        searchView.setOnQueryTextListener(null)
    }
}