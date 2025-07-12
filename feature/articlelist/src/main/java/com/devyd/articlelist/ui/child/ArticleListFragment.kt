package com.devyd.articlelist.ui.child

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.devyd.articlelist.databinding.FragmentArticlelistBinding
import com.devyd.articlelist.models.ArticleResult
import com.devyd.articlelist.ui.child.adapter.ArticleAdapter
import com.devyd.articlelist.ui.child.vm.ArticleListViewModel
import com.devyd.common.Constants
import com.devyd.common.util.LogUtil
import com.devyd.common.util.logTag
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ArticleListFragment : Fragment() {

    private var _binding: FragmentArticlelistBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ArticleListViewModel by viewModels<ArticleListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticlelistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.refreshArticle.setOnClickListener {
            LogUtil.i(logTag(), "article refresh")

        }
        val articleAdapter = ArticleAdapter(emptyList()) { article ->
            val dataBundle = bundleOf(Constants.ARTICLE_ID to article.title)
            parentFragmentManager.setFragmentResult(Constants.ARTICLE_CLICK, dataBundle)
        }


        binding.rvArticles.apply {
            layoutManager = GridLayoutManager(requireContext(), 1)
            adapter = articleAdapter
        }

        binding.btnRetry.setOnClickListener {
            viewModel.refreshArticle()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.article.collect { articleResult ->
                when (articleResult) {
                    ArticleResult.Idle -> {
                        renderState(isLoading = false, isFail = false, isSuccess = false)
                    }

                    ArticleResult.Loading -> {
                        renderState(isLoading = true, isFail = false, isSuccess = false)
                    }

                    is ArticleResult.Failure -> {
                        renderState(isLoading = false, isFail = true, isSuccess = false)
                        LogUtil.e(logTag(), articleResult.error)
                    }

                    is ArticleResult.Success -> {
                        renderState(isLoading = false, isFail = false, isSuccess = true)
                        articleAdapter.updateArticles(articleResult.news.articles)
                    }
                }
            }
        }
    }

    private fun renderState(isLoading: Boolean, isFail: Boolean, isSuccess: Boolean) {
        binding.progressLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.btnRetry.visibility = if (isFail) View.VISIBLE else View.GONE
        binding.rvArticles.visibility = if (isSuccess) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}