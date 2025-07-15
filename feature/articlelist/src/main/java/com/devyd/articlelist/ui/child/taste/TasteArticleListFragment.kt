package com.devyd.articlelist.ui.child.taste

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.devyd.articlelist.databinding.FragmentTasteArticlelistBinding
import com.devyd.articlelist.models.ArticleResult
import com.devyd.articlelist.ui.child.taste.adapter.TasteArticleAdapter
import com.devyd.articlelist.ui.child.taste.vm.TasteArticleListViewModel
import com.devyd.common.Constants
import com.devyd.common.util.LogUtil
import com.devyd.common.util.logTag
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TasteArticleListFragment : Fragment() {

    private var _binding: FragmentTasteArticlelistBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TasteArticleListViewModel by viewModels<TasteArticleListViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTasteArticlelistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshArticle(true)
        }

        val tasteArticleAdapter = TasteArticleAdapter(emptyList()) { articleUiState ->
            val dataBundle = bundleOf(Constants.ARTICLE to articleUiState)
            parentFragmentManager.setFragmentResult(Constants.ARTICLE_CLICK, dataBundle)
        }

        binding.rvArticles.apply {
            layoutManager = GridLayoutManager(requireContext(), 1)
            adapter = tasteArticleAdapter
        }

        binding.btnRetry.setOnClickListener {
            viewModel.refreshArticle(false)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.article.collect { articleResult ->
                when (articleResult) {
                    ArticleResult.Idle -> {
                        LogUtil.i(logTag(), "Start time")
                        binding.swipeRefreshLayout.isRefreshing = false
                        renderState(isLoading = false, isFail = false, isSuccess = false)
                    }

                    is ArticleResult.Loading -> {
                        renderState(
                            isLoading = !articleResult.isSwipeLoading,
                            isFail = false,
                            isSuccess = false
                        )
                    }

                    is ArticleResult.Failure -> {
                        LogUtil.e(logTag(), articleResult.error)
                        binding.swipeRefreshLayout.isRefreshing = false
                        renderState(isLoading = false, isFail = true, isSuccess = false)
                    }

                    is ArticleResult.Success -> {
                        tasteArticleAdapter.updateArticlesUiState(articleResult.articlesUiState.articleUiState)
                        binding.swipeRefreshLayout.isRefreshing = false
                        renderState(isLoading = false, isFail = false, isSuccess = true)
                        LogUtil.i(logTag(), "end time")
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