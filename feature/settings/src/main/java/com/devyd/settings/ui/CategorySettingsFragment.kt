package com.devyd.settings.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.devyd.settings.databinding.FragmentCategorySettingsBinding
import com.devyd.settings.ui.adapter.CategoryAdapter
import com.devyd.settings.ui.adapter.FooterAdapter
import com.devyd.settings.ui.vm.CategorySettingsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategorySettingsFragment : Fragment() {
    private var _binding: FragmentCategorySettingsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CategorySettingsViewModel by viewModels()

    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var footerAdapter: FooterAdapter
    private lateinit var concatAdapter: ConcatAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategorySettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        categoryAdapter = CategoryAdapter(
            categories = viewModel.categories,
            onModify = { id: Int, category: String, weight: Int ->
                viewModel.updateSelection(id, category, weight)
            },
            onDelete = { categoryWeight ->
                viewModel.deleteSelection(categoryWeight)
            }
        )

        footerAdapter = FooterAdapter {
            if(viewModel.categoryWeights.value.size >= viewModel.categories.size){
                Toast.makeText(context, "No more categories can be created.", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.addSelection()
            }
        }

        binding.rvCategories.layoutManager = LinearLayoutManager(requireContext())
        concatAdapter = ConcatAdapter(categoryAdapter, footerAdapter)
        binding.rvCategories.adapter = concatAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.categoryWeights.collect { list ->
                    categoryAdapter.submitList(list)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.scroll.collect { scroll ->
                    if (scroll) {
                        binding.rvCategories.post {
                            binding.rvCategories.scrollToPosition(concatAdapter.itemCount - 1)
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}