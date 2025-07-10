package com.devyd.settings.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.devyd.settings.databinding.FragmentCategorySettingsBinding
import com.devyd.settings.vm.CategorySettingsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategorySettingsFragment : Fragment() {
    private var _binding: FragmentCategorySettingsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CategorySettingsViewModel by viewModels<CategorySettingsViewModel>()

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

        var listAdapter: CategoryAdapter? = null

        listAdapter = CategoryAdapter(
            categories = viewModel.categories,
            onModify = { sel ->
                viewModel.modifySelection(sel.id, sel.category, sel.weight)
            },
            onDelete = { sel ->
                viewModel.deleteSelection(sel.id)

                listAdapter?.submitList(viewModel.getCategoryWeightList())
            }
        )

        var concatAdapter: ConcatAdapter? = null
        val footerAdapter = FooterAdapter {

            viewModel.addSelection()  // 구현: id 생성 + addSelection
            listAdapter.submitList(viewModel.getCategoryWeightList())
            binding.rvCategories.post {
                binding.rvCategories.scrollToPosition(concatAdapter?.itemCount?.minus(1) ?: 0)
            }
        }


        binding.rvCategories.layoutManager = LinearLayoutManager(requireContext())

        concatAdapter = ConcatAdapter(listAdapter, footerAdapter)
        binding.rvCategories.adapter = concatAdapter


        listAdapter.submitList(viewModel.getCategoryWeightList())

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}