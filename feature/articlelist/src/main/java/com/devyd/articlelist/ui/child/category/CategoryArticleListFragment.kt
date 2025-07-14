package com.devyd.articlelist.ui.child.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.devyd.articlelist.databinding.FragmentCategoryArticlelistBinding
import com.devyd.articlelist.models.ArticleResult
import com.devyd.articlelist.ui.child.category.adapter.CategoryArticleAdapter
import com.devyd.articlelist.ui.child.category.vm.CategoryArticleListViewModel
import com.devyd.common.Constants
import com.devyd.common.util.LogUtil
import com.devyd.common.util.logTag
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoryArticleListFragment : Fragment() {

    private var _binding: FragmentCategoryArticlelistBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CategoryArticleListViewModel by viewModels<CategoryArticleListViewModel>()

    private val category: String by lazy {
        arguments?.getString(CATEGORY) ?: "default"
    }

    companion object {
        fun newInstance(category: String): CategoryArticleListFragment {
            return CategoryArticleListFragment().apply {
                arguments = bundleOf(CATEGORY to category)
            }
        }

        private const val CATEGORY = "category"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.initParams(category)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryArticlelistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.swipeRefreshLayout.setOnRefreshListener  {
            viewModel.refreshArticle(true, category)
        }

        val categoryArticleAdapter = CategoryArticleAdapter(emptyList()) { articleUiState ->
            val dataBundle = bundleOf(Constants.ARTICLE to articleUiState)
            parentFragmentManager.setFragmentResult(Constants.ARTICLE_CLICK, dataBundle)
        }

        binding.rvArticles.apply {
            layoutManager = GridLayoutManager(requireContext(), 1)
            adapter = categoryArticleAdapter
        }

        binding.btnRetry.setOnClickListener {
            viewModel.refreshArticle(false, category)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.article.collect { articleResult ->
                when (articleResult) {
                    ArticleResult.Idle -> {
                        binding.swipeRefreshLayout.isRefreshing = false
                        renderState(isLoading = false, isFail = false, isSuccess = false)
                    }

                    is ArticleResult.Loading -> {
                        renderState(isLoading = !articleResult.isSwipeLoading, isFail = false, isSuccess = false)
                    }

                    is ArticleResult.Failure -> {
                        LogUtil.e(logTag(), articleResult.error)
                        binding.swipeRefreshLayout.isRefreshing = false
                        renderState(isLoading = false, isFail = true, isSuccess = false)
                    }

                    is ArticleResult.Success -> {
                        categoryArticleAdapter.updateArticlesUiState(articleResult.articlesUiState.articleUiState)
                        binding.swipeRefreshLayout.isRefreshing = false
                        renderState(isLoading = false, isFail = false, isSuccess = true)
                    }
                }
            }
        }
    }

    private fun renderState(isLoading: Boolean, isFail: Boolean, isSuccess: Boolean) {
        binding.progressLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.btnRetry.visibility = if (isFail) View.VISIBLE else View.GONE
        binding.swipeRefreshLayout.visibility = if (isSuccess) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}