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
import com.devyd.common.CategoryStrings
import com.devyd.settings.databinding.FragmentCategorySettingsBinding
import com.devyd.settings.models.CategoryWeightResult
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
            categories = viewModel.categories.map { getString(it) },
            onModify = { id: Int, category: String, weight: Int ->
                viewModel.updateSelection(id, category, weight)
            },
            onDelete = { categoryWeight ->
                viewModel.deleteSelection(categoryWeight)
            }
        )

        footerAdapter = FooterAdapter {
            if (viewModel.addCategoryPossible.value) {
                viewModel.addSelection(CategoryStrings.validValues.first())
            } else {
                Toast.makeText(context, "No more categories can be created.", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        binding.rvCategories.layoutManager = LinearLayoutManager(requireContext())
        concatAdapter = ConcatAdapter(categoryAdapter, footerAdapter)
        binding.rvCategories.adapter = concatAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.categoryWeights.collect { cwResult ->
                    when (cwResult) {
                        CategoryWeightResult.Idle -> {
                            renderState(isLoading = false, isFail = false, isSuccess = false)
                        }

                        CategoryWeightResult.Loading -> {
                            renderState(isLoading = true, isFail = false, isSuccess = false)
                        }

                        is CategoryWeightResult.Failure -> {
                            renderState(isLoading = false, isFail = true, isSuccess = false)
                        }

                        is CategoryWeightResult.Success -> {
                            categoryAdapter.submitList(cwResult.categoryWeightList)
                            renderState(isLoading = false, isFail = false, isSuccess = true)
                        }
                    }
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

    private fun renderState(isLoading: Boolean, isFail: Boolean, isSuccess: Boolean) {
        binding.progressLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.btnRetry.visibility = if (isFail) View.VISIBLE else View.GONE
        binding.rvCategories.visibility = if (isSuccess) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}